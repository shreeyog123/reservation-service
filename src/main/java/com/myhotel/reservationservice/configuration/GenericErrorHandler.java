package com.myhotel.reservationservice.configuration;


import com.myhotel.reservationservice.exception.BookingNotFoundException;
import com.myhotel.reservationservice.exception.ClientException;
import com.myhotel.reservationservice.exception.HotelRoomException;
import com.myhotel.reservationservice.model.response.GenericErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice(basePackages = "com.myhotel.reservationservice.contract")
public class GenericErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ClientException.class)
    public ResponseEntity<GenericErrorResponse> clientException(final ClientException exception) {

        GenericErrorResponse errorResponse = GenericErrorResponse.builder()
                .errorCode(exception.getErrorCode())
                .errorMessage(exception.getMessage())
                .build();

        log.error("event=createWorkFlow, errorResponse = {}", errorResponse);

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(HotelRoomException.class)
    public ResponseEntity<GenericErrorResponse> hotelRoomIsNotAvailable(final HotelRoomException exception) {

        GenericErrorResponse errorResponse = GenericErrorResponse.builder()
                .errorCode(101)
                .errorMessage(exception.getMessage())
                .build();

        log.error("event=createWorkFlow, errorResponse = {}", errorResponse);

        return ResponseEntity.badRequest().body(errorResponse);
    }


    @ExceptionHandler(BookingNotFoundException.class)
    public ResponseEntity<GenericErrorResponse> bookingNotFoundException(final BookingNotFoundException exception) {

        GenericErrorResponse errorResponse = GenericErrorResponse.builder()
                .errorCode(101)
                .errorMessage(exception.getMessage())
                .build();

        log.error("event=createWorkFlow, errorResponse = {}", errorResponse);

        return ResponseEntity.badRequest().body(errorResponse);
    }
}
