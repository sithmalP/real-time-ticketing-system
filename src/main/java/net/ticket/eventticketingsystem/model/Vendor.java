package net.ticket.eventticketingsystem.model;

import net.ticket.eventticketingsystem.service.TicketPool;

public class Vendor implements Runnable {
    private final TicketPool ticketPool;
    private final Long ticketReleaseRate;
    private final Long maxTicketCapacity;

    public Vendor(TicketPool ticketPool, Long ticketReleaseRate, Long maxTicketCapacity) {
        this.ticketPool = ticketPool;
        this.ticketReleaseRate = ticketReleaseRate;
        this.maxTicketCapacity = maxTicketCapacity;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(ticketReleaseRate * 1000);
                ticketPool.addTicket(maxTicketCapacity);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
