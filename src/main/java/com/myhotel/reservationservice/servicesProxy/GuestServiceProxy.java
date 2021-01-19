package com.myhotel.reservationservice.servicesProxy;

import com.google.gson.Gson;
import com.myhotel.reservationservice.exception.ClientException;
import com.myhotel.reservationservice.model.GenericErrorResponse;
import com.myhotel.reservationservice.model.GuestDetails;
import com.myhotel.reservationservice.model.HotelDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class GuestServiceProxy {

    public static final String GUEST_SERVICE_GET_GUEST_DETAILS_PATH = "http://guest-service/guest/details/{guestId}";

    private final RestTemplate restTemplate;

    private Gson gson = new Gson();

    public GuestServiceProxy(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public GuestDetails getGuestDetails(final Long guestId) {

        String buildUri = getGuestDetailsByGuestIdPath(guestId);
        log.info("build uri {}", buildUri);

        try {
            ResponseEntity<GuestDetails> response = restTemplate.getForEntity(buildUri, GuestDetails.class);
            log.info("hotel details response {} ", response);

            return response.getBody();

        }catch (HttpClientErrorException e){

            GenericErrorResponse errorResponse = gson.fromJson(e.getResponseBodyAsString(), GenericErrorResponse.class);
            log.error("error response {} ", errorResponse);
            throw new ClientException(errorResponse.errorMessage, errorResponse.errorCode);
        }
    }

    private String getGuestDetailsByGuestIdPath(final Long guestId) {

        final Map<String, Object> uriVariable = new HashMap<>();
        uriVariable.put("guestId", guestId);

        final UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(GUEST_SERVICE_GET_GUEST_DETAILS_PATH);

        return uriComponentsBuilder.buildAndExpand(uriVariable).toUriString();
    }
}
