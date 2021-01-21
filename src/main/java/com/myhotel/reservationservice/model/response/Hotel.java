package com.myhotel.reservationservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {

    private Long hotelId;

    private String hotelName;

    private List<RoomAvailable> roomAvailable;
}
