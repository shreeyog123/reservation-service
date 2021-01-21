package com.myhotel.reservationservice.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HotelResponse {

    private String message;
}
