package com.myhotel.reservationservice.contract;

import com.myhotel.reservationservice.model.request.BookHotelRequest;
import com.myhotel.reservationservice.model.response.BookingResponse;
import com.myhotel.reservationservice.model.response.GenericErrorResponse;
import com.myhotel.reservationservice.model.response.ReservationResponse;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "booking")
@RequestMapping("booking")
public interface HotelBookingContract {

    @ApiOperation(
            value = "Request for book a hotel.",
            response = ReservationResponse.class,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            httpMethod = "POST"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Hotel has been booked successfully.", response = ReservationResponse.class),
            @ApiResponse(code = 400, message = "Business validation fail", response = GenericErrorResponse.class),
            @ApiResponse(code = 401, message = "Authentication failed."),
            @ApiResponse(code = 403, message = "You are not authorized to do this operation."),
            @ApiResponse(code = 404, message = "Resource not found.")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ReservationResponse> bookAHotel(
            @ApiParam(value = "request for book hotel") @RequestBody final BookHotelRequest guestDetailsRequest);

    @ApiOperation(
            value = "Fetch Booking Details by guest id ",
            response = BookingResponse.class,
            produces = MediaType.APPLICATION_JSON_VALUE,
            httpMethod = "GET"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Booking information has been successfully get.", response = BookingResponse.class),
            @ApiResponse(code = 400, message = "Business validation fail", response = GenericErrorResponse.class),
            @ApiResponse(code = 401, message = "Authentication failed."),
            @ApiResponse(code = 403, message = "You are not authorized to do this operation."),
            @ApiResponse(code = 404, message = "Resource not found.")
    })
    @GetMapping(value = "/details/{guestId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BookingResponse> getBookingDetailsByGuestId(
            @ApiParam(value = "Get booking details by guest id ") @PathVariable("guestId") final long guestId);

    @ApiOperation(
            value = "Cancel booking",
            response = ReservationResponse.class,
            produces = MediaType.APPLICATION_JSON_VALUE,
            httpMethod = "DELETE"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Booking has been canceled successfully.", response = ReservationResponse.class),
            @ApiResponse(code = 400, message = "Business validation fail", response = GenericErrorResponse.class),
            @ApiResponse(code = 401, message = "Authentication failed."),
            @ApiResponse(code = 403, message = "You are not authorized to do this operation."),
            @ApiResponse(code = 404, message = "Resource not found.")
    })
    @DeleteMapping(value = "/{bookingId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ReservationResponse> cancelBooking(
            @ApiParam(value = "booking id for cancel booking") @PathVariable("bookingId") final long bookingId);


}
