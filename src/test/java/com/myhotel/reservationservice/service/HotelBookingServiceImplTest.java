package com.myhotel.reservationservice.service;


import com.myhotel.reservationservice.exception.*;
import com.myhotel.reservationservice.model.entity.BookingEntity;
import com.myhotel.reservationservice.model.request.BookHotelRequest;
import com.myhotel.reservationservice.model.response.*;
import com.myhotel.reservationservice.repository.HotelBookingServiceRepository;
import com.myhotel.reservationservice.servicesProxy.GuestServiceProxy;
import com.myhotel.reservationservice.servicesProxy.HotelServiceProxy;
import com.myhotel.reservationservice.utils.BusinessValidationUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class HotelBookingServiceImplTest {

    private HotelBookingServiceImpl hotelBookingService;

    @Mock
    private HotelBookingServiceRepository bookingServiceRepository;

    @Mock
    private HotelServiceProxy hotelServiceProxy;

    @Mock
    private GuestServiceProxy guestServiceProxy;

    private BusinessValidationUtils validationUtils = new BusinessValidationUtils();

    @Before
    public void setup() {
        initMocks(this);
        hotelBookingService = new HotelBookingServiceImpl(bookingServiceRepository, hotelServiceProxy, guestServiceProxy, validationUtils);
    }

    @Test(expected = HotelNotFoundException.class)
    public void testIfHotelInformationNotFound_bookAHotelForGuest() {
        hotelBookingService.bookAHotelForGuest(getBookingRequest());
    }

    @Test(expected = GuestNotFoundException.class)
    public void testIfGuestInformationNotFound_bookAHotelForGuest() {
        when(hotelServiceProxy.getHotelDetailsByHotelId(getBookingRequest().getHotelId())).thenReturn(getHotelDetails());
        hotelBookingService.bookAHotelForGuest(getBookingRequest());
    }

    @Test(expected = HotelRoomException.class)
    public void testIfHotelInformationFoundButRoomIsNotAvailable_bookAHotelForGuest() {
        when(hotelServiceProxy.getHotelDetailsByHotelId(getBookingRequest().getHotelId())).thenReturn(getHotelDetails());
        hotelBookingService.bookAHotelForGuest(getBookingRequestLarge());
    }

    @Test(expected = BookingIsNotConfirmedException.class)
    public void testIfBookingIsNotConfirmedInDataBase_bookAHotelForGuest() {
        when(hotelServiceProxy.getHotelDetailsByHotelId(getBookingRequest().getHotelId())).thenReturn(getHotelDetails());
        when(guestServiceProxy.getGuestDetails(getBookingRequest().getGuestId())).thenReturn(getGuestDetails());
        hotelBookingService.bookAHotelForGuest(getBookingRequest());
    }

    @Test(expected = HotelRoomUpdateException.class)
    public void testIfUpdateHotelRoomHasNotUpdated_bookAHotelForGuest() {
        when(hotelServiceProxy.getHotelDetailsByHotelId(getBookingRequest().getHotelId())).thenReturn(getHotelDetails());
        when(guestServiceProxy.getGuestDetails(getBookingRequest().getGuestId())).thenReturn(getGuestDetails());
        when(bookingServiceRepository.save(Matchers.any())).thenReturn(getBooking());
        hotelBookingService.bookAHotelForGuest(getBookingRequest());
    }

    @Test
    public void testIfAllRequiredInformationFound_bookAHotelForGuest() {
        when(hotelServiceProxy.getHotelDetailsByHotelId(getBookingRequest().getHotelId())).thenReturn(getHotelDetails());
        when(guestServiceProxy.getGuestDetails(getBookingRequest().getGuestId())).thenReturn(getGuestDetails());
        when(bookingServiceRepository.save(Matchers.any())).thenReturn(getBooking());
        when(hotelServiceProxy.updateHotel(Matchers.any())).thenReturn(getHotelUpdateResponse());
        String message = hotelBookingService.bookAHotelForGuest(getBookingRequest());

        Assert.assertEquals(HotelBookingService.BOOKING_CONFIRMED_MESSAGE, message);
    }

    @Test(expected = BookingNotFoundException.class)
    public void testIfGuestIdNull_getBookingDetailsByGuestId() {
        hotelBookingService.getBookingDetailsByGuestId(null);
    }

    @Test(expected = BookingNotFoundException.class)
    public void testIfGuestIdNotNullButBookingIsEmpty_getBookingDetailsByGuestId() {
        Long guestId = 1L;
        hotelBookingService.getBookingDetailsByGuestId(guestId);
    }

    @Test(expected = HotelNotFoundException.class)
    public void testIfBookingInformationFoundButHotelInformationNotFound_getBookingDetailsByGuestId() {
        Long guestId = 1L;
        when(bookingServiceRepository.getBookingInformationByGuestId(1L)).thenReturn(getBookingEntity());
        hotelBookingService.getBookingDetailsByGuestId(guestId);
    }

    @Test
    public void testIfBookingInformationFound_getBookingDetailsByGuestId() {
        Long guestId = 1L;
        when(bookingServiceRepository.getBookingInformationByGuestId(1L)).thenReturn(getBookingEntity());
        when(hotelServiceProxy.getHotelDetailsByHotelId(Matchers.any())).thenReturn(getHotelDetails());
        BookingResponse response = hotelBookingService.getBookingDetailsByGuestId(guestId);

        Assert.assertEquals(2, response.getBookings().size());
    }

    private List<BookingEntity> getBookingEntity() {

        List<BookingEntity> bookings = new ArrayList<>();
        BookingEntity bl1 = BookingEntity.builder()
                .bookingId(1)
                .bookingStatus("CONFIRMED")
                .roomType("Queen")
                .bookingStartDate(LocalDate.now().plusDays(1))
                .bookingEndDate(LocalDate.now().plusDays(3))
                .guests(2)
                .updateTimeStamp(LocalDateTime.now())
                .guestId(1L)
                .build();

        BookingEntity bl2 = BookingEntity.builder()
                .bookingId(1)
                .bookingStatus("CONFIRMED")
                .roomType("Queen")
                .bookingStartDate(LocalDate.now().plusDays(1))
                .bookingEndDate(LocalDate.now().plusDays(3))
                .guests(2)
                .updateTimeStamp(LocalDateTime.now())
                .guestId(1L)
                .build();

        bookings.add(bl1);
        bookings.add(bl2);

        return bookings;
    }


    private HotelResponse getHotelUpdateResponse() {
        return HotelResponse.builder()
                .message("room has been updated successfully")
                .build();
    }

    private BookingEntity getBooking() {
        return BookingEntity.builder()
                .bookingStatus(HotelBookingService.CONFIRMED)
                .hotelId(1)
                .roomType("Queen")
                .build();
    }

    private GuestDetails getGuestDetails() {
        return GuestDetails.builder()
                .guestFirstName("Mat")
                .guestLastName("Green")
                .email("mat@gmail.com")
                .phoneNumber(90239032023L)
                .address(getAddress())
                .stayHistory(getStayHistory())
                .build();
    }

    private List<StayHistory> getStayHistory() {

        List<StayHistory> stayHistories = new ArrayList<>();
        StayHistory sh1 = StayHistory.builder()
                .bookedRoomCode("1")
                .bookingStatus("CONFIRMED")
                .bookingStartDate(LocalDate.now())
                .bookingEndDate(LocalDate.now().plusDays(1))
                .hotelId(1)
                .hotelName("Space")
                .build();

        StayHistory sh2 = StayHistory.builder()
                .bookedRoomCode("1")
                .bookingStatus("FEEDBACK")
                .bookingStartDate(LocalDate.now().minusDays(10))
                .bookingEndDate(LocalDate.now().minusDays(20))
                .hotelId(1)
                .hotelName("Space")
                .build();

        stayHistories.add(sh1);
        stayHistories.add(sh2);
        return stayHistories;
    }

    private Address getAddress() {

        return Address.builder()
                .buildingName("XYZ")
                .city("Pune")
                .state("MH")
                .country("India")
                .pin(411284)
                .build();

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
