package net.ticket.eventticketingsystem.controller;

import jakarta.annotation.PostConstruct;
import net.ticket.eventticketingsystem.service.TicketOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ControlPanelController {

    @Autowired
    private TicketOperation ticketOperation;

    private static final Logger logger = LoggerFactory.getLogger(ControlPanelController.class);


    @PostConstruct
    public void initialize() {
        ticketOperation.initializeFromConfiguration();
    }

    @MessageMapping("/start")
    @SendTo("/topic/logs")
    public String startOperation() {
        ticketOperation.startOperation();
        logger.info("request received. operation started.");
        return "Operation started successfully!";
    }

    @MessageMapping("/stop")
    @SendTo("/topic/logs")
    public String stopOperation() {
        ticketOperation.stopOperation();
        logger.info("request received. operation stopped.");
        return "Operation stopped successfully!";
    }

    @MessageMapping("/details")
    @SendTo("/topic/details")
    public void getDetails() {
        ticketOperation.broadcastTicketDetails();
    }
}
