package com.myhotel.reservationservice.utils;

import com.myhotel.reservationservice.model.request.BookHotelRequest;
import com.myhotel.reservationservice.model.response.Hotel;
import com.myhotel.reservationservice.model.response.RoomAvailable;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BusinessValidationUtilsTest {


    BusinessValidationUtils validationUtils = new BusinessValidationUtils();

    @Test
    public void testByHotelTypeAndNumberOfRooms() {

        List<RoomAvailable> availableRooms = validationUtils.isHotelRoomAvailable(getBookingRequest(), getHotelDetails());

        assertEquals(1,availableRooms.size());

    }

    @Test
    public void testByHotelTypeAndOnlyOneRoomsAvailable() {

        List<RoomAvailable> availableRooms = validationUtils.isHotelRoomAvailable(getBookingRequestKing(), getHotelDetails());

        assertEquals(1,availableRooms.size());

    }

    @Test
    public void testByHotelTypeAndNumberOfRoomsNotAvailable() {

        List<RoomAvailable> availableRooms = validationUtils.isHotelRoomAvailable(getBookingRequestLarge(), getHotelDetails());

        assertEquals(0,availableRooms.size());

    }

    private Hotel getHotelDetails() {

        Hotel hotel = Hotel.builder()
                .hotelId(1L)
                .hotelName("Space")
                .roomAvailable(getAvailableRooms())
                .build();
        return hotel;
    }

    private List<RoomAvailable> getAvailableRooms() {
        List<RoomAvailable> availableRooms = new ArrayList<>();
        RoomAvailable roomAvailable1 = RoomAvailable.builder()
                .availableRooms(2)
                .roomType("Queen")
                .price(8000L)
                .build();
        RoomAvailable roomAvailable2 = RoomAvailable.builder()
                .availableRooms(1)
                .roomType("King")
                .price(10000L)
                .build();
        availableRooms.add(roomAvailable1);
        availableRooms.add(roomAvailable2);

        return availableRooms;

    }

    private BookHotelRequest getBookingRequest() {

        BookHotelRequest bookingRequest = BookHotelRequest.builder()
                .bookingStartDate(LocalDate.now())
                .bookingEndDate(LocalDate.now().plusDays(1))
                .guestId(1)
                .hotelId(1)
                .numberOfGuest(2)
                .roomType("Queen")
                .build();
        return bookingRequest;
    }

    private BookHotelRequest getBookingRequestKing() {
        BookHotelRequest bookingRequest = BookHotelRequest.builder()
                .bookingStartDate(LocalDate.now())
                .bookingEndDate(LocalDate.now().plusDays(1))
                .guestId(1)
                .hotelId(1)
                .numberOfGuest(2)
                .roomType("King")
                .build();
        return bookingRequest;
    }

    private BookHotelRequest getBookingRequestLarge() {
        BookHotelRequest bookingRequest = BookHotelRequest.builder()
                .bookingStartDate(LocalDate.now())
                .bookingEndDate(LocalDate.now().plusDays(1))
                .guestId(1)
                .hotelId(1)
                .numberOfGuest(2)
                .roomType("Large")
                .build();
        return bookingRequest;
    }



}
