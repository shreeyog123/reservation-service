package com.myhotel.reservationservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StayHistory {

    private long hotelId;
    private String hotelName;
    private String bookedRoomCode;
    private String bookingStatus;
    private LocalDate bookingStartDate;
    private LocalDate bookingEndDate;
}
