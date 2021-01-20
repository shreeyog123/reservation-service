package com.myhotel.reservationservice.service;

import com.myhotel.reservationservice.model.BookHotelRequest;
import com.myhotel.reservationservice.model.BookingResponse;

public interface HotelBookingService {

    String bookAHotelForGuest(final BookHotelRequest guestDetailsRequest);

    BookingResponse getBookingDetailsByGuestId(final long guestId);
}
