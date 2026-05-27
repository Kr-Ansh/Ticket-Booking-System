package com.irons.ticketbookingsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TicketRequestDTO {

    @NotBlank(message = "Customer name cannot be blank.")
    @Size(min = 2, max = 50, message = "Customer name must be between 2 and 50 characters.")
    private String customerName;
}
