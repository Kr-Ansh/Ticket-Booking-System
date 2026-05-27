package com.irons.ticketbookingsystem.service;

import com.irons.ticketbookingsystem.model.Ticket;
import com.irons.ticketbookingsystem.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketBookingService {

    private final TicketRepository ticketRepository;

    public List<Ticket> getAvailableTickets() {
        log.info("Fetching all unbooked allocations from database.");
        return ticketRepository.findByIsBooked(false);
    }

    @Transactional
    public Ticket bookTicket(Long ticketId, String customerName) {

        log.info("Attempting to lock ticket Id: {} for customer: {}", ticketId, customerName);

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Transaction Failed: Ticket allocation target not found."));

        if(ticket.getIsBooked()) {
            throw new RuntimeException("Transaction Failed: Seat assignment " + ticket.getSeatNumber() + " is already secured.");
        }

        ticket.setCustomerName(customerName);
        ticket.setIsBooked(true);

        return ticketRepository.save(ticket);
    }

    public List<Ticket> getAllTickets() {
        log.info("Fetching all the tickets from database.");
        return ticketRepository.findAll();
    }

    public List<Ticket> getTicketsBookedByCustomerName(String customerName) {
        log.info("Fetching all the tickets booked by customer name: {}, from database", customerName);

        List<Ticket> ticketList = ticketRepository.findByCustomerName(customerName);
        if(ticketList.isEmpty()) {
            throw new RuntimeException("Transaction Failed: Ticket allocation target not found.");
        }
        return ticketList;
    }
}
