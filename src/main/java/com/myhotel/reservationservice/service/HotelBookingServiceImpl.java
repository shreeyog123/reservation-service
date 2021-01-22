package com.myhotel.reservationservice.service;

import com.myhotel.reservationservice.exception.*;
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

        final Hotel hotelDetails = getHotelDetails(bookHotelRequest.getHotelId());
        log.info("hotel details {}",hotelDetails );

        if (validationUtils.isHotelRoomAvailable(bookHotelRequest, hotelDetails).isEmpty()) {
            throw new HotelRoomException(REQUESTED_HOTEL_ROOM_IS_NOT_AVAILABLE);
        }

        final GuestDetails guestDetails = getGuestDetails(bookHotelRequest);
        log.info("guest details {} ", guestDetails);

        saveRequestedBooking(bookHotelRequest);

        return BOOKING_CONFIRMED_MESSAGE;
    }


    @Override
    public BookingResponse getBookingDetailsByGuestId(final Long guestId) {

        final List<BookingEntity> bookingEntity = getBookingEntitiesByGuestId(guestId);
        log.info("booking entity {} ", bookingEntity);

        List<Booking> bookings = new ArrayList<>();
        bookingEntity.forEach(b -> {
            Hotel hotelDetails = getHotelDetails(b.getHotelId());

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

    private List<BookingEntity> getBookingEntitiesByGuestId(final Long guestId) {
        if(guestId != null) {
            final List<BookingEntity> bookingEntity = bookingServiceRepository.getBookingInformationByGuestId(guestId);
            if(bookingEntity != null && !bookingEntity.isEmpty()){
                return bookingEntity;
            }
            throw new BookingNotFoundException(BOOKING_NOT_FOUND);
        }
        throw new BookingNotFoundException(BOOKING_NOT_FOUND);
    }

    @Override
    public String cancelBooking(final Long bookingId) {

        final BookingEntity booking = getBookingByBookingId(bookingId);
        log.info("get booking by booking id{} bookingEntity {}", bookingId, booking);

        booking.setBookingStatus(CANCELED);
        log.info("set status to CANCELED, bookingEntity {}", booking);

        final BookingEntity canceledBooking = saveBookingEntity(booking);

        final String updateMessage = updateNumberOfRoom(canceledBooking.getHotelId(), canceledBooking.getRoomType(), canceledBooking.getBookingStatus());
        log.info("update message {} after update Number of room", updateMessage);

        return BOOKING_HAS_BEEN_CANCELED_SUCCESSFULLY;
    }

    private BookingEntity getBookingByBookingId(Long bookingId) {
        if (bookingId != null) {
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
        final BookingEntity booking = saveBookingEntity(buildBookingEntityForSave(bookHotelRequest, CONFIRMED));
        log.info("booking is not saved in database",booking);

        final String updateMessage = updateNumberOfRoom(booking.getHotelId(), booking.getRoomType(), booking.getBookingStatus());
        log.info("update message {} after update Number of room", updateMessage);
    }

    private BookingEntity saveBookingEntity(BookingEntity bookingEntity) {
        final BookingEntity booking = bookingServiceRepository.save(bookingEntity);
        if(booking !=null){
            return booking;
        }
       else {
           throw new BookingIsNotConfirmedException("booking is not confirm yet !");
        }

    }

    private String updateNumberOfRoom(long hotelId, final String roomType, final String bookingStatus) {

        final HotelResponse hotelResponse = hotelServiceProxy.updateHotel(
                HotelUpdateRequest.builder().hotelId(hotelId)
                        .roomType(roomType)
                        .status(bookingStatus)
                        .build());
        log.info("hotel information update response {} ", hotelResponse);
        if(hotelResponse !=null){
            return hotelResponse.getMessage();
        }
        else {
            throw new HotelRoomUpdateException("room has not been updated");
        }


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

    private GuestDetails getGuestDetails(BookHotelRequest bookHotelRequest) {
        final GuestDetails guestDetails = guestServiceProxy.getGuestDetails(bookHotelRequest.getGuestId());
        log.info("guest details {} ", guestDetails);
        if(guestDetails != null){
            return guestDetails;
        }
        else {
            throw new GuestNotFoundException("Guest details has not been found in record. please sign up");
        }
    }

    private Hotel getHotelDetails(long hotelId) {
        final Hotel hotelDetails = hotelServiceProxy.getHotelDetailsByHotelId(hotelId);
        if(hotelDetails != null){
            return hotelDetails;
        }
        else {
            throw new HotelNotFoundException("Requested Hotel details is not found in record ");
        }

    }



}
