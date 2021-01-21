package com.myhotel.reservationservice.exception;

public class BookingNotFoundException extends RuntimeException {
    public BookingNotFoundException(final String bookingNotFound) {
        super(bookingNotFound);
    }
}
