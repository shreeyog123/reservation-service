package com.myhotel.reservationservice.utils;

import com.myhotel.reservationservice.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BusinessValidationUtilsTest {


    BusinessValidationUtils validationUtils = new BusinessValidationUtils();

    public void test(){

        //validationUtils.validateGuestAndHotelDetails(getGuestDetails(), getHotelDetails(), getBookingRequest());

    }

    private Hotel getHotelDetails() {
        return null;
    }

    private BookHotelRequest getBookingRequest() {
        return null;
    }


    private GuestDetails getGuestDetails(){



       List<StayHistory> stayHistories = new ArrayList<>();

       StayHistory stay1 = StayHistory.builder()
               .hotelId(1)
               .bookingStartDate(LocalDate.now().minusDays(5))
               .bookingEndDate(LocalDate.now().minusDays(3))
               .bookedRoomCode("Q101")
               .bookingStatus("FEEDBACK")
               .build();

       StayHistory stay2 = StayHistory.builder()
               .hotelId(1)
               .bookingStartDate(LocalDate.now())
               .bookingEndDate(LocalDate.now().minusDays(3))
               .bookedRoomCode("Q101")
               .bookingStatus("BOOKED")
               .build();

       StayHistory stay3 = StayHistory.builder()
               .hotelId(2)
               .bookingStartDate(LocalDate.now())
               .bookingEndDate(LocalDate.now().minusDays(3))
               .bookedRoomCode("K101")
               .bookingStatus("BOOKED")
               .build();

       stayHistories.add(stay1);
       stayHistories.add(stay2);
       stayHistories.add(stay3);

        GuestDetails guestDetails = GuestDetails.builder()
                .stayHistory(stayHistories)
                .build();

       return guestDetails;

    }



}
