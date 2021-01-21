package com.myhotel.reservationservice.service;

import com.myhotel.reservationservice.model.request.BookHotelRequest;
import com.myhotel.reservationservice.model.response.BookingResponse;

public interface HotelBookingService {

    String REQUESTED_HOTEL_ROOM_IS_NOT_AVAILABLE = "Requested Hotel room is not available";
    String BOOKING_CONFIRMED_MESSAGE = "booking has been confirmed";
    String BOOKING_NOT_FOUND = "requested booking has not been found";
    String BOOKING_HAS_BEEN_CANCELED_SUCCESSFULLY = "booking has been canceled successfully";
    String CONFIRMED = "CONFIRMED";
    String CANCELED ="CANCELED";

    String bookAHotelForGuest(final BookHotelRequest guestDetailsRequest);

    BookingResponse getBookingDetailsByGuestId(final long guestId);

    String cancelBooking(final long bookingId);
}
