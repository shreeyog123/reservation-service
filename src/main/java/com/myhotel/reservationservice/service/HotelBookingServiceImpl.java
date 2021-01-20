package com.myhotel.reservationservice.service;

import com.myhotel.reservationservice.exception.HotelRoomException;
import com.myhotel.reservationservice.model.*;
import com.myhotel.reservationservice.model.entity.BookingEntity;
import com.myhotel.reservationservice.repository.HotelBookingServiceRepository;
import com.myhotel.reservationservice.servicesProxy.GuestServiceProxy;
import com.myhotel.reservationservice.servicesProxy.HotelServiceProxy;
import com.myhotel.reservationservice.utils.BusinessValidationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class HotelBookingServiceImpl implements HotelBookingService {

    public static final String REQUESTED_HOTEL_ROOM_IS_NOT_AVAILABLE = "Requested Hotel room is not available";

   private final HotelBookingServiceRepository bookingServiceRepository;

   private final HotelServiceProxy hotelServiceProxy;

   private final GuestServiceProxy guestServiceProxy;

   private final BusinessValidationUtils validationUtils;

    public HotelBookingServiceImpl(
            HotelBookingServiceRepository bookingServiceRepository,
            HotelServiceProxy hotelServiceProxy,
            GuestServiceProxy guestServiceProxy, BusinessValidationUtils validationUtils) {
        this.bookingServiceRepository = bookingServiceRepository;
        this.hotelServiceProxy = hotelServiceProxy;
        this.guestServiceProxy = guestServiceProxy;
        this.validationUtils = validationUtils;
    }

    @Override
    public String bookAHotelForGuest(final BookHotelRequest bookHotelRequest) {

        Hotel hotelDetails = hotelServiceProxy.getHotelDetailsByHotelId(bookHotelRequest.getHotelId());
        log.info("hotel details {} ", hotelDetails);

        if(validationUtils.isHotelRoomAvailable(bookHotelRequest, hotelDetails).isEmpty()){
            throw new HotelRoomException(REQUESTED_HOTEL_ROOM_IS_NOT_AVAILABLE);
        }

        GuestDetails guestDetails = guestServiceProxy.getGuestDetails(bookHotelRequest.getGuestId());
        log.info("guest details {} ", guestDetails);

        BookingEntity booking = BookingEntity.builder()
                .hotelId(bookHotelRequest.getHotelId())
                .guestId(bookHotelRequest.getGuestId())
                .bookingStartDate(bookHotelRequest.getBookingStartDate())
                .bookingEndDate(bookHotelRequest.getBookingEndDate())
                .roomType(bookHotelRequest.getRoomType())
                .bookingStatus("CONFIRMED")
                .guests(bookHotelRequest.getNumberOfGuest())
                .build();
        bookingServiceRepository.save(booking);

        hotelServiceProxy.updateHotel(booking.getHotelId(), booking.getRoomType(), booking.getBookingStatus());

        return "booking confirmed";
    }

    @Override
    public BookingResponse getBookingDetailsByGuestId(final long guestId) {

        List<BookingEntity> bookingEntity= bookingServiceRepository.getBookingInformationByGuestId(guestId);
        log.info("booking entity {} ", bookingEntity);

        List<Booking> bookings = new ArrayList<>();

        bookingEntity.forEach(b->{
            Hotel hotelDetails = hotelServiceProxy.getHotelDetailsByHotelId(b.getHotelId());
            log.info("hotel details {} ", hotelDetails);

           bookings.add( Booking.builder()
                    .hotelName(hotelDetails.getHotelName())
                    .roomType(b.getRoomType())
                    .bookingStatus(b.getBookingStatus())
                    .bookingStartDate(b.getBookingStartDate())
                    .bookingEndDate(b.getBookingEndDate())
                    .build());
        });

        return BookingResponse.builder()
                .bookings(bookings)
                .build();
    }


}
