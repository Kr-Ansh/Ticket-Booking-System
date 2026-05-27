package com.irons.ticketbookingsystem.controller;

import com.irons.ticketbookingsystem.dto.TicketRequestDTO;
import com.irons.ticketbookingsystem.dto.TicketResponseDTO;
import com.irons.ticketbookingsystem.model.Ticket;
import com.irons.ticketbookingsystem.service.TicketBookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketBookingService ticketBookingService;

    @GetMapping("/available")
    public ResponseEntity<List<TicketResponseDTO>> getAvailableTickets() {
        log.info("REST Request received to list all open seat configurations.");
        List<TicketResponseDTO> availableDTOs = ticketBookingService.getAvailableTickets()
                .stream()
                .map(ticket -> new TicketResponseDTO(
                        ticket.getId(), ticket.getMovieTitle(), ticket.getSeatNumber(), ticket.getPrice(), ticket.getCustomerName(), ticket.getIsBooked()
                ))
                .toList();

        return ResponseEntity.ok(availableDTOs);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TicketResponseDTO>> getAllTickets() {
        log.info("REST Request received to list all seats configurations.");
        List<TicketResponseDTO> allDTOs = ticketBookingService.getAllTickets()
                .stream()
                .map(ticket -> new TicketResponseDTO(
                        ticket.getId(), ticket.getMovieTitle(), ticket.getSeatNumber(), ticket.getPrice(), ticket.getCustomerName(), ticket.getIsBooked()
                ))
                .toList();

        return ResponseEntity.ok(allDTOs);
    }

    @GetMapping("/{customerName}")
    public ResponseEntity<List<TicketResponseDTO>> getAllTicketsByCustomerName(
            @PathVariable String customerName
    ) {
        log.info("REST Request to get all tickets booked by customer name.");

        List<TicketResponseDTO> customerDTOs = ticketBookingService.getTicketsBookedByCustomerName(customerName)
                .stream()
                .map(ticket -> new TicketResponseDTO(
                        ticket.getId(), ticket.getMovieTitle(), ticket.getSeatNumber(), ticket.getPrice(), ticket.getCustomerName(), ticket.getIsBooked()
                ))
                .toList();

        return ResponseEntity.ok(customerDTOs);
    }

    @PostMapping("/book/{id}")
    public ResponseEntity<TicketResponseDTO> bookTickets(
            @PathVariable Long id,
            @Valid @RequestBody TicketRequestDTO requestDTO
    ) {
        log.info("REST Request received to book ticket assignment ID: {} for user: {}", id, requestDTO.getCustomerName());

        Ticket bookedTicket = ticketBookingService.bookTicket(id, requestDTO.getCustomerName());

        TicketResponseDTO responseDTO = new TicketResponseDTO(
                bookedTicket.getId(),
                bookedTicket.getMovieTitle(),
                bookedTicket.getSeatNumber(),
                bookedTicket.getPrice(),
                bookedTicket.getCustomerName(),
                bookedTicket.getIsBooked()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDTO);
    }
}
