package com.myhotel.reservationservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelUpdateRequest {

    private long hotelId;

    private String roomType;

    private String status;



}
