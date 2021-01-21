package com.myhotel.reservationservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private String buildingName;
    private String city;
    private String state;
    private String country;
    private Integer pin;



}
