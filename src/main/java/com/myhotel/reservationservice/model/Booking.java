package com.myhotel.reservationservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Booking {

    private String hotelName;

    private String roomType;

    private LocalDate bookingStartDate;

    private LocalDate bookingEndDate;

    private String bookingStatus;

    private GuestDetails guests;
}
