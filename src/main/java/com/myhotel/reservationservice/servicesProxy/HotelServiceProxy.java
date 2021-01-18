package com.myhotel.reservationservice.servicesProxy;

import com.myhotel.reservationservice.model.HotelDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class HotelServiceProxy {

    public static final String HOTEL_SERVICE_HOTEL_DETAILS_PATH = "http://hotel-service/hotel/details/{hotelId}";
    private final  RestTemplate restTemplate;

    public HotelServiceProxy(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public HotelDetails getHotelDetailsByHotelId(final String hotelId, final LocalDate startDate, final LocalDate endDate) {

       String buildUri = getHotelDetailsByHotelIdPath(hotelId,startDate, endDate);
       log.info("build uri {}", buildUri);

       ResponseEntity<HotelDetails> response = restTemplate.getForEntity(buildUri, HotelDetails.class);
       log.info("hotel details response {} ",response );

       return response.getBody();
    }

    private String getHotelDetailsByHotelIdPath(final String hotelId, final LocalDate startDate, final LocalDate endDate){

        final Map<String, Object> uriVariable = new HashMap<>();
        uriVariable.put("hotelId", hotelId);

        final UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(HOTEL_SERVICE_HOTEL_DETAILS_PATH);
        uriComponentsBuilder.queryParam("startDate", startDate);
        uriComponentsBuilder.queryParam("endDate", endDate);

        return uriComponentsBuilder.buildAndExpand(uriVariable).toUriString();
    }
}
