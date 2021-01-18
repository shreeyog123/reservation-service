package com.myhotel.reservationservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomAvailable {

    private String roomCode;

    private String roomType;

    private Integer price;

    private LocalDate availableStartDate;

    private LocalDate availableEndDate;
}
