package com.myhotel.reservationservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuestDetails {

    private String guestFirstName;

    private String guestLastName;

    private long phoneNumber;

    private String email;

    private Address address;

    private List<StayHistory> stayHistory;

}
