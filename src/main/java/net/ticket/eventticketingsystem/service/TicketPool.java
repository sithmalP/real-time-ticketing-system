package net.ticket.eventticketingsystem.service;

import net.ticket.eventticketingsystem.service.impl.ConfigurationServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class TicketPool {
    private final List<Integer> ticketsPool = Collections.synchronizedList(new Vector<>());

    private static final Logger logger = LoggerFactory.getLogger(TicketPool.class);


    public synchronized void addTicket(Long maxTicketCapacity) {
        if (ticketsPool.size() < maxTicketCapacity) {
            ticketsPool.add(1);
            logger.info("Ticket added. Current ticket pool size: {}", ticketsPool.size());
        } else {
            logger.warn("Cannot add ticket. Maximum ticket capacity reached: {}", maxTicketCapacity);
        }
    }

    public synchronized boolean removeTicket() {
        if (!ticketsPool.isEmpty()) {
            ticketsPool.remove(0);
            logger.info("Ticket removed. Current ticket pool size: {}", ticketsPool.size());
            return true;
        }
        return false;
    }

    public synchronized int getCurrentTicketCount() {
        return ticketsPool.size();
    }

    // Clears all tickets from the pool
    public synchronized void clearTickets() {
        ticketsPool.clear();
        logger.info("Tickets cleared. Current ticket pool size: {}", ticketsPool.size());
    }
}
