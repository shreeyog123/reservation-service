package com.myhotel.reservationservice.service;

import com.myhotel.reservationservice.model.BookHotelRequest;

public interface HotelBookingService {

    String bookAHotelForGuest(BookHotelRequest guestDetailsRequest);
}
