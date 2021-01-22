package com.myhotel.reservationservice.exception;

public class BookingIsNotConfirmedException extends RuntimeException {
    public BookingIsNotConfirmedException(String message) {
        super(message);
    }

}
