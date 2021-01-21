package com.myhotel.reservationservice.service;

import com.myhotel.reservationservice.exception.BookingNotFoundException;
import com.myhotel.reservationservice.exception.HotelRoomException;
import com.myhotel.reservationservice.model.entity.BookingEntity;
import com.myhotel.reservationservice.model.request.BookHotelRequest;
import com.myhotel.reservationservice.model.request.HotelUpdateRequest;
import com.myhotel.reservationservice.model.response.*;
import com.myhotel.reservationservice.repository.HotelBookingServiceRepository;
import com.myhotel.reservationservice.servicesProxy.GuestServiceProxy;
import com.myhotel.reservationservice.servicesProxy.HotelServiceProxy;
import com.myhotel.reservationservice.utils.BusinessValidationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class HotelBookingServiceImpl implements HotelBookingService {

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

        final Hotel hotelDetails = hotelServiceProxy.getHotelDetailsByHotelId(bookHotelRequest.getHotelId());
        log.info("hotel details {} ", hotelDetails);

        if (validationUtils.isHotelRoomAvailable(bookHotelRequest, hotelDetails).isEmpty()) {
            throw new HotelRoomException(REQUESTED_HOTEL_ROOM_IS_NOT_AVAILABLE);
        }

        final GuestDetails guestDetails = guestServiceProxy.getGuestDetails(bookHotelRequest.getGuestId());
        log.info("guest details {} ", guestDetails);

        saveRequestedBooking(bookHotelRequest);

        return BOOKING_CONFIRMED_MESSAGE;
    }

    @Override
    public BookingResponse getBookingDetailsByGuestId(final long guestId) {

        List<BookingEntity> bookingEntity = bookingServiceRepository.getBookingInformationByGuestId(guestId);
        log.info("booking entity {} ", bookingEntity);

        List<Booking> bookings = new ArrayList<>();
        bookingEntity.forEach(b -> {
            Hotel hotelDetails = hotelServiceProxy.getHotelDetailsByHotelId(b.getHotelId());
            log.info("hotel details {} ", hotelDetails);

            bookings.add(Booking.builder()
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

    @Override
    public String cancelBooking(final long bookingId) {

        final BookingEntity booking = getBookingByBookingId(bookingId);
        log.info("get booking by booking id{} bookingEntity {}", bookingId, booking);

        booking.setBookingStatus(CANCELED);
        log.info("set status to CANCELED, bookingEntity {}",booking);

        final BookingEntity canceledBooking = bookingServiceRepository.save(booking);
        log.info("booking has been canceled cancelBookingEntity{}", canceledBooking);

        final String updateMessage = updateNumberOfRoom(canceledBooking.getHotelId(),canceledBooking.getRoomType(),canceledBooking.getBookingStatus());
        log.info("update message {} after update Number of room",updateMessage);

        return BOOKING_HAS_BEEN_CANCELED_SUCCESSFULLY;
    }

    private BookingEntity getBookingByBookingId(Long bookingId) {
        if (bookingId !=null) {
            Optional<BookingEntity> bookingEntity = bookingServiceRepository.findById(bookingId);
            log.info("booking entity {} ", bookingEntity);
            if (bookingEntity.isPresent()) {
                return bookingEntity.get();
            } else {
                throw new BookingNotFoundException(BOOKING_NOT_FOUND);
            }
        }
        throw new BookingNotFoundException(BOOKING_NOT_FOUND);
    }

    private void saveRequestedBooking(final BookHotelRequest bookHotelRequest) {
        final BookingEntity booking = bookingServiceRepository.save(buildBookingEntityForSave(bookHotelRequest, CONFIRMED));
        log.info("booking entity saved {} ", booking);

        final String updateMessage = updateNumberOfRoom(booking.getHotelId(),booking.getRoomType(),booking.getBookingStatus());
        log.info("update message {} after update Number of room",updateMessage);
    }

    private String updateNumberOfRoom(long hotelId, final String roomType, final String bookingStatus) {

        final HotelResponse hotelResponse = hotelServiceProxy.updateHotel(
                HotelUpdateRequest.builder().hotelId(hotelId)
                        .roomType(roomType)
                        .status(bookingStatus)
                        .build());
        log.info("hotel information update response {} ", hotelResponse);
        return hotelResponse.getMessage();

    }

    private BookingEntity buildBookingEntityForSave(final BookHotelRequest bookHotelRequest, final String bookingStatus) {
        return BookingEntity.builder()
                .hotelId(bookHotelRequest.getHotelId())
                .guestId(bookHotelRequest.getGuestId())
                .bookingStartDate(bookHotelRequest.getBookingStartDate())
                .bookingEndDate(bookHotelRequest.getBookingEndDate())
                .roomType(bookHotelRequest.getRoomType())
                .bookingStatus(bookingStatus)
                .guests(bookHotelRequest.getNumberOfGuest())
                .build();
    }


}
