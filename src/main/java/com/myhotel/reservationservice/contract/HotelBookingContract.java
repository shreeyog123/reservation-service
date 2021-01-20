package com.myhotel.reservationservice.contract;

import com.myhotel.reservationservice.model.BookHotelRequest;
import com.myhotel.reservationservice.model.BookingResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Api(value = "booking")
public interface HotelBookingContract {

    @ApiOperation(
            value = "add",
            response =String.class,
            produces = MediaType.APPLICATION_JSON_VALUE,
            httpMethod = "POST"
    )
    ResponseEntity<String> bookAHotel(
            @ApiParam(value = "request for book hotel for Guest") final BookHotelRequest guestDetailsRequest);


    @ApiOperation(
            value = "details/{guestId}",
            response =BookingResponse.class,
            produces = MediaType.APPLICATION_JSON_VALUE,
            httpMethod = "GET"
    )
     ResponseEntity<BookingResponse> bookingDetails(
             @ApiParam(value = "get booking details by guest id ") final long guestId);


}
