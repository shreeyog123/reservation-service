package com.myhotel.reservationservice.contract;

import com.myhotel.reservationservice.model.BookHotelRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

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


}
