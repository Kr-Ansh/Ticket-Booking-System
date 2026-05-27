package com.irons.ticketbookingsystem.controller;

import com.irons.ticketbookingsystem.dto.BookingRequestDTO;
import com.irons.ticketbookingsystem.model.Ticket;
import com.irons.ticketbookingsystem.service.TicketBookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketBookingService ticketBookingService;

    @GetMapping("/available")
    public ResponseEntity<List<Ticket>> getAvailableTickets() {
        log.info("REST Request received to list all open seat configurations.");
        List<Ticket> availableTickets = ticketBookingService.getAvailableTickets();
        return ResponseEntity.ok(availableTickets);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Ticket>> getAllTickets() {
        log.info("REST Request received to list all seats configurations.");
        List<Ticket> allTickets = ticketBookingService.getAllTickets();
        return ResponseEntity.ok(allTickets);
    }

    @PostMapping("/book/{id}")
    public ResponseEntity<?> bookTickets(
            @PathVariable Long id,
            @Valid @RequestBody BookingRequestDTO requestDTO
    ) {
        log.info("REST Request received to book ticket assignment ID: {} for user: {}", id, requestDTO.getCustomerName());

        Ticket bookedTicket = ticketBookingService.bookTicket(id, requestDTO.getCustomerName());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(bookedTicket);
    }
}
