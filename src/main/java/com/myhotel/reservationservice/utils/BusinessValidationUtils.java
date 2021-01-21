package com.myhotel.reservationservice.utils;

import com.myhotel.reservationservice.model.request.BookHotelRequest;
import com.myhotel.reservationservice.model.response.Hotel;
import com.myhotel.reservationservice.model.response.RoomAvailable;
import com.myhotel.reservationservice.model.entity.BookingEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class BusinessValidationUtils {

    public List<RoomAvailable> isHotelRoomAvailable(final BookHotelRequest bookHotelRequest, final Hotel hotelDetails) {
        return hotelDetails.getRoomAvailable().stream()
                 .filter(q -> q.getRoomType().equals(bookHotelRequest.getRoomType()))
                 .filter(r -> r.getAvailableRooms() >= 0)
                 .collect(Collectors.toList());
    }


    public List<BookingEntity> isBookingAlreadyAvailable(List<BookingEntity> bookings, LocalDate bookingStartDate, LocalDate bookingEndDate) {

        return bookings.stream().filter(booking -> validateDates(booking,bookingStartDate,bookingEndDate)).collect(Collectors.toList());
    }

    private boolean validateDates(BookingEntity booking, LocalDate startDate, LocalDate endDate) {


        final boolean startDateFlag = booking.getBookingStartDate().isEqual(startDate) ||  booking.getBookingStartDate().isAfter(startDate) ;
        final boolean endDateFlag = booking.getBookingEndDate().isEqual(endDate) ||  booking.getBookingEndDate().isBefore(endDate);

        return startDateFlag && endDateFlag;
    }


}
