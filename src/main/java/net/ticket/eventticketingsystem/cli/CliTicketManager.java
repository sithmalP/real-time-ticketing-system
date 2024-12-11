package net.ticket.eventticketingsystem.cli;

import net.ticket.eventticketingsystem.dto.ConfigurationDto;
import net.ticket.eventticketingsystem.service.TicketOperation;
import net.ticket.eventticketingsystem.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class CliTicketManager implements CommandLineRunner {

    @Autowired
    private TicketOperation ticketOperation;
    private static final Logger logger = LoggerFactory.getLogger(CliTicketManager.class);


    @Override
    public void run(String... args) {

        // Create a scanner for user input
        Scanner scanner = new Scanner(System.in);
        Long ticketReleaseRate = 0L;
        Long customerRetrievalRate = 0L;
        Long maxTicketCapacity = 0L;
        Long totalTickets = 0L;

        // Manually configure the activeConfigurations
        List<ConfigurationDto> configurations = new ArrayList<>();
        System.out.println("############################");
        System.out.println("SETUP CONFIGURATIONS");
        System.out.println("############################");
        boolean isValid = true;
        while(isValid){
            System.out.println("Enter ticket release rate(per second): ");
            ticketReleaseRate = scanner.nextLong();
            isValid = Validator.validateValue(ticketReleaseRate);
        }
        isValid = true;
        while(isValid){
            System.out.println("Enter customer retrieval rate(per second): ");
            customerRetrievalRate = scanner.nextLong();
            isValid = Validator.validateValue(customerRetrievalRate);
        }
        isValid = true;
        while(isValid){
            System.out.println("Enter maximum ticket capacity: ");
            maxTicketCapacity = scanner.nextLong();
            isValid = Validator.validateValue(maxTicketCapacity);
        }
        isValid = true;
        while(isValid){
            System.out.println("Enter total tickets: ");
            totalTickets = scanner.nextLong();
            isValid = Validator.validateValue(totalTickets);
        }


        // Example of manually setting configurations
        configurations.add(new ConfigurationDto(1,"ticketReleaseRate", 5L, ticketReleaseRate)); // Parameter, Value, Default Value
        configurations.add(new ConfigurationDto(2,"customerRetrievalRate", 5L, customerRetrievalRate));
        configurations.add(new ConfigurationDto(3,"maxTicketCapacity", 100L, maxTicketCapacity));
        configurations.add(new ConfigurationDto(4,"totalTickets", 50L, totalTickets));

        // Initialize TicketOperation with configurations
        ticketOperation.updateConfiguration(configurations);

        // Start operation in the background
        Thread operationThread = new Thread(() -> {
            // Provide a menu for user interaction
            while (true) {
                System.out.println("1. Start Operation");
                System.out.println("2. Stop Operation");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        // Start the ticket operation
                        ticketOperation.startOperation();
                        break;
                    case 2:
                        // Stop the ticket operation
                        stopOperation();
                        break;
                    case 3:
                        // Exit the application
                        System.out.println("Exiting...");
                        stopOperation();  // Ensure the operation stops before exiting
                        scanner.close();   // Close the scanner
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        });
        operationThread.start();
    }

    private void stopOperation() {
        ticketOperation.stopOperation();
    }

}

