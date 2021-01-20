package com.myhotel.reservationservice.contract;

import com.myhotel.reservationservice.model.BookHotelRequest;
import com.myhotel.reservationservice.model.BookingResponse;
import com.myhotel.reservationservice.service.HotelBookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("booking")
public class HotelBookingController implements HotelBookingContract {

    private final HotelBookingService bookingService;

    public HotelBookingController(HotelBookingService bookingService) {
        this.bookingService = bookingService;
    }


    @Override
    @PostMapping("/")
    public ResponseEntity<String> bookAHotel(@RequestBody final BookHotelRequest guestDetailsRequest) {

        bookingService.bookAHotelForGuest(guestDetailsRequest);

        return ResponseEntity.ok().body("success");
    }

    @Override
    @GetMapping("/details/{guestId}")
    public ResponseEntity<BookingResponse> bookingDetails(@PathVariable("guestId") long guestId){

        BookingResponse bookingDetails = bookingService.getBookingDetailsByGuestId(guestId);
        log.info("booking details {} ", bookingDetails);

        return ResponseEntity.ok(bookingDetails);

    }

}
