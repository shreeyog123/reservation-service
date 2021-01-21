package com.myhotel.reservationservice.contract;

import com.myhotel.reservationservice.model.request.BookHotelRequest;
import com.myhotel.reservationservice.model.response.BookingResponse;
import com.myhotel.reservationservice.model.response.ReservationResponse;
import com.myhotel.reservationservice.service.HotelBookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class HotelBookingController implements HotelBookingContract {

    private final HotelBookingService bookingService;

    public HotelBookingController(HotelBookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Override
    public ResponseEntity<ReservationResponse> bookAHotel(final BookHotelRequest guestDetailsRequest) {

        log.info("request for book a hotel request {}", guestDetailsRequest);

        final String bookedMessage = bookingService.bookAHotelForGuest(guestDetailsRequest);
        log.info("booked message {} ", bookedMessage);

        return ResponseEntity.status(HttpStatus.CREATED).body(ReservationResponse.builder().message(bookedMessage).build());
    }

    @Override
    public ResponseEntity<BookingResponse> getBookingDetailsByGuestId(final long guestId){

        log.info("get booking details by guest id {}", guestId);

        final BookingResponse bookingDetails = bookingService.getBookingDetailsByGuestId(guestId);
        log.info("booking details successfully get bookingDetails{} ", bookingDetails);

        return ResponseEntity.ok(bookingDetails);

    }

    @Override
    public ResponseEntity<ReservationResponse> cancelBooking(final long bookingId) {

        log.info("cancel booking for bookingId {} ", bookingId);

        final String cancelMessage = bookingService.cancelBooking(bookingId);
        log.info("cancel message {}",cancelMessage);

        return ResponseEntity.accepted().body(ReservationResponse.builder().message(cancelMessage).build());
    }

}
