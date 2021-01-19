package com.myhotel.reservationservice.servicesProxy;

import com.google.gson.Gson;
import com.myhotel.reservationservice.exception.ClientException;
import com.myhotel.reservationservice.model.GenericErrorResponse;
import com.myhotel.reservationservice.model.Hotel;
import com.myhotel.reservationservice.model.HotelUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class HotelServiceProxy {

    public static final String HOTEL_SERVICE_HOTEL_DETAILS_PATH = "http://hotel-service/hotel/details/{hotelId}";
    public static final String HOTEL_UPDATE_PATH = "http://hotel-service/hotel/hotel";
    private final  RestTemplate restTemplate;

    Gson gson = new Gson();

    public HotelServiceProxy(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Hotel getHotelDetailsByHotelId(final Long hotelId) {

      final String buildUri = getHotelDetailsByHotelIdPath(hotelId);
       log.info("build uri {}", buildUri);

       try {
           ResponseEntity<Hotel> response = restTemplate.getForEntity(buildUri, Hotel.class);
           log.info("hotel details response {} ", response);
           return response.getBody();
       }
       catch (HttpClientErrorException e){

           GenericErrorResponse errorResponse = gson.fromJson(e.getResponseBodyAsString(), GenericErrorResponse.class);

           throw new ClientException(errorResponse.errorMessage, errorResponse.errorCode);
       }

    }

    private String getHotelDetailsByHotelIdPath(final Long hotelId){

        final Map<String, Object> uriVariable = new HashMap<>();
        uriVariable.put("hotelId", hotelId);

        final UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(HOTEL_SERVICE_HOTEL_DETAILS_PATH);

        return uriComponentsBuilder.buildAndExpand(uriVariable).toUriString();
    }

    public String updateHotel(final long hotelId, final String roomType, final String bookingStatus) {

       HotelUpdateRequest request = HotelUpdateRequest.builder()
               .hotelId(hotelId)
               .roomType(roomType)
               .status(bookingStatus)
               .build();

        HttpEntity requestUpdate = new HttpEntity<>(request, null);

        try {
            ResponseEntity<String> response = restTemplate.exchange(HOTEL_UPDATE_PATH,HttpMethod.PUT, requestUpdate,String.class);
            log.info("update hotel response {} ", response);
            return response.getBody();
        }
        catch (HttpClientErrorException e){

            GenericErrorResponse errorResponse = gson.fromJson(e.getResponseBodyAsString(), GenericErrorResponse.class);

            throw new ClientException(errorResponse.errorMessage, errorResponse.errorCode);
        }
    }


}
