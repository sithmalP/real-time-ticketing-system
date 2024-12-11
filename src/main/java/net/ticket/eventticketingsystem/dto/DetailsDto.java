package net.ticket.eventticketingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class DetailsDto {
    private int totalTickets;
    private int ticketsLeft;
    private int customerCount;
    private int vendorCount;

    public DetailsDto(int totalTickets, int ticketsLeft, int customerCount, int vendorCount) {
        this.totalTickets = totalTickets;
        this.ticketsLeft = ticketsLeft;
        this.customerCount = customerCount;
        this.vendorCount = vendorCount;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getTicketsLeft() {
        return ticketsLeft;
    }

    public void setTicketsLeft(int ticketsLeft) {
        this.ticketsLeft = ticketsLeft;
    }

    public int getCustomerCount() {
        return customerCount;
    }

    public void setCustomerCount(int customerCount) {
        this.customerCount = customerCount;
    }

    public int getVendorCount() {
        return vendorCount;
    }

    public void setVendorCount(int vendorCount) {
        this.vendorCount = vendorCount;
    }
}
