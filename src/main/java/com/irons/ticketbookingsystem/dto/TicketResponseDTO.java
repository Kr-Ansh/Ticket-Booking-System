package com.irons.ticketbookingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponseDTO {

    private Long id;
    private String movieTitle;
    private String seatNumber;
    private Double price;
    private String customerName;
    private boolean isBooked;
}
