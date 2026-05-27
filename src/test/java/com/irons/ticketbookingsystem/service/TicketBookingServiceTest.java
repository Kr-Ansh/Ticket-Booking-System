package com.irons.ticketbookingsystem.service;

import com.irons.ticketbookingsystem.model.Ticket;
import com.irons.ticketbookingsystem.repository.TicketRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TicketBookingServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketBookingService ticketBookingService;

    @Test
    @DisplayName("Should successfully book ticket when seat is unbooked")
    void bookTicket_Success() {

        //Arrange
        Long ticketId = 1L;
        Ticket mockTicket = new Ticket(ticketId, "Matrix", "A1", 150.00, null, false);

        Mockito.when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(mockTicket));
        Mockito.when(ticketRepository.save(mockTicket)).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Ticket result = ticketBookingService.bookTicket(ticketId, "Kr Ansh");

        // Assert
        assertNotNull(result);
        assertTrue(result.getIsBooked());
        assertEquals("Kr Ansh", result.getCustomerName());

        Mockito.verify(ticketRepository, Mockito.times(1)).findById(ticketId);
        Mockito.verify(ticketRepository, Mockito.times(1)).save(mockTicket);
    }

    @Test
    @DisplayName("Should throw exception when trying to book an already booked ticket")
    void bookTicket_AlreadyBooked_ThrowsException() {

        // Arrange
        Long ticketId = 1L;
        Ticket mockAlreadyBookedTicket = new Ticket(ticketId, "Matrix", "A2", 150.00, "Kamlesh", true);

        Mockito.when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(mockAlreadyBookedTicket));

        // Act
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ticketBookingService.bookTicket(ticketId, "Kr Ansh");
        });

        // Assert
        assertTrue(exception.getMessage().contains("already secured"));

        Mockito.verify(ticketRepository, Mockito.never()).save(Mockito.any(Ticket.class));
    }
}