package net.ticket.eventticketingsystem.service;

import net.ticket.eventticketingsystem.dto.ConfigurationDto;
import net.ticket.eventticketingsystem.dto.DetailsDto;
import net.ticket.eventticketingsystem.model.Customer;
import net.ticket.eventticketingsystem.model.Vendor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Component
public class TicketOperation {
    private final TicketPool ticketPool = new TicketPool();
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);
    private final AtomicBoolean isRunning = new AtomicBoolean(false);

    private static final Logger logger = LoggerFactory.getLogger(TicketOperation.class);

    private static final int customerCount = 5;
    private static final int vendorCount = 5;


    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ConfigurationService configurationService;

    private Map<String, Long> activeConfigurations;

    public void initializeFromConfiguration() {
        List<ConfigurationDto> configurations = configurationService.getAllConfigurations();
        if (configurations == null || configurations.isEmpty()) {
            sendLogMessage("No configurations found. Initialization failed.");
            logger.info("No configurations found. Initialization failed.");
            return;
        }

        activeConfigurations = configurations.stream()
                .collect(Collectors.toMap(ConfigurationDto::getParameter,
                        config -> config.getValue() != null ? config.getValue() : config.getDefaultValue()));
        logger.info("Configurations initialized successfully.");
        sendLogMessage("Configurations initialized successfully.");
    }

    public synchronized void startOperation() {
        if (isRunning.get()) {
            sendLogMessage("Operation already running.");
            logger.info("Operation already running.");
            return;
        }

        if (activeConfigurations == null || activeConfigurations.isEmpty()) {
            sendLogMessage("Configurations are not initialized. Unable to start operation.");
            logger.info("Configurations are not initialized. Unable to start operation.");
            return;
        }

        isRunning.set(true);
        sendLogMessage("Starting ticket operation...");
        logger.info("Starting ticket operation...");

        Long ticketReleaseRate = activeConfigurations.get("ticketReleaseRate");
        Long customerRetrievalRate = activeConfigurations.get("customerRetrievalRate");
        Long maxTicketCapacity = activeConfigurations.get("maxTicketCapacity");

        // Initialize the ticket pool
        Long totalTickets = activeConfigurations.get("totalTickets");
        for (int i = 0; i < totalTickets; i++) {
            ticketPool.addTicket(maxTicketCapacity);
        }

        // Start ticket-producing vendor threads
        for (int i = 0; i < customerCount; i++) {
            Vendor vendor = new Vendor(ticketPool, ticketReleaseRate, maxTicketCapacity);
            executorService.submit(vendor);
        }

        // Start ticket-consuming customer threads
        for (int i = 0; i < vendorCount; i++) {
            Customer customer = new Customer(ticketPool, customerRetrievalRate);
            executorService.submit(customer);
        }

        sendLogMessage("Ticket operation started successfully.");
        logger.info("Ticket operation started successfully.");
    }

    public synchronized void stopOperation() {
        if (!isRunning.get()) {
            sendLogMessage("Operation is not running.");
            logger.info("Operation is not running.");
            return;
        }

        isRunning.set(false);
        executorService.shutdownNow();
        ticketPool.clearTickets(); // Clear the ticket pool using TicketPool method
        sendLogMessage("Ticket operation stopped.");
        logger.info("Ticket operation stopped.");
    }

    public synchronized void updateConfiguration(List<ConfigurationDto> newConfigurations) {
        if (isRunning.get()) {
            logger.info("Cannot update configuration while operation is running. Please stop the operation first.");
            sendLogMessage("Cannot update configuration while operation is running. Please stop the operation first.");
            return;
        }

        activeConfigurations = newConfigurations.stream()
                .collect(Collectors.toMap(ConfigurationDto::getParameter,
                        config -> config.getValue() != null ? config.getValue() : config.getDefaultValue()));
        logger.info("Configuration updated successfully.");
        sendLogMessage("Configuration updated successfully.");
    }

    private void sendLogMessage(String message) {
        messagingTemplate.convertAndSend("/topic/logs", message);
    }

    public void broadcastTicketDetails() {
        DetailsDto details = new DetailsDto(ticketPool.getCurrentTicketCount(), Math.toIntExact(activeConfigurations.get("maxTicketCapacity")), vendorCount, customerCount);
        messagingTemplate.convertAndSend("/topic/details", details);
    }
}
