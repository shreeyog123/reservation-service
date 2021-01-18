package com.myhotel.reservationservice.contract;

import com.myhotel.reservationservice.model.BookHotelRequest;
import com.myhotel.reservationservice.service.HotelBookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("booking")
public class HotelBookingController implements HotelBookingContract {

    private final HotelBookingService bookingService;

    public HotelBookingController(HotelBookingService bookingService) {
        this.bookingService = bookingService;
    }


    @Override
    @PostMapping("add")
    public ResponseEntity<String> bookAHotel(@RequestBody final BookHotelRequest guestDetailsRequest) {

        bookingService.bookAHotelForGuest(guestDetailsRequest);

        return ResponseEntity.ok().body("success");
    }
}
