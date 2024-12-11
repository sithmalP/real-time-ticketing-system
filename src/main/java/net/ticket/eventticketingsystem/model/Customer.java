package net.ticket.eventticketingsystem.model;

import net.ticket.eventticketingsystem.service.TicketPool;

public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final Long customerRetrievalRate; // How often the customer retrieves tickets

    public Customer(TicketPool ticketPool, Long customerRetrievalRate) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(customerRetrievalRate * 1000); // Control the rate at which customers retrieve tickets
                boolean success = ticketPool.removeTicket();
                if (!success) {
                    System.out.println("No tickets available for customer to retrieve.");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
