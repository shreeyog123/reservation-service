package com.myhotel.reservationservice.service;

import com.myhotel.reservationservice.model.BookHotelRequest;
import com.myhotel.reservationservice.model.HotelDetails;
import com.myhotel.reservationservice.repository.HotelBookingServiceRepository;
import com.myhotel.reservationservice.servicesProxy.HotelServiceProxy;
import org.springframework.stereotype.Service;

@Service
public class HotelBookingServiceImpl implements HotelBookingService {

   private final HotelBookingServiceRepository bookingServiceRepository;

   private final HotelServiceProxy hotelServiceProxy;

    public HotelBookingServiceImpl(HotelBookingServiceRepository bookingServiceRepository, HotelServiceProxy hotelServiceProxy) {
        this.bookingServiceRepository = bookingServiceRepository;
        this.hotelServiceProxy = hotelServiceProxy;
    }

    @Override
    public String bookAHotelForGuest(final BookHotelRequest guestDetailsRequest) {

        HotelDetails hotelDetails = hotelServiceProxy.getHotelDetailsByHotelId(guestDetailsRequest.getHotelId(),
                guestDetailsRequest.getBookingStartDate(),
                guestDetailsRequest.getBookingEndDate());



        // check available details by room id and start date end end date
        // check capacity of guest
        // save booking
        // save booking by guest id
        return null;
    }
}
