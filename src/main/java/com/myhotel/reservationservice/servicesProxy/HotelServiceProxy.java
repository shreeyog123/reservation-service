package com.myhotel.reservationservice.servicesProxy;

import com.myhotel.reservationservice.model.Hotel;
import com.myhotel.reservationservice.model.HotelUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "hotel-service")
public interface HotelServiceProxy {

     @RequestMapping(value = "/hotel/details/{hotelId}", method = RequestMethod.GET)
     Hotel getHotelDetailsByHotelId(@PathVariable("hotelId") final Long hotelId);

     @RequestMapping(value = "/hotel/", method = RequestMethod.PUT)
     String updateHotel(@RequestBody final HotelUpdateRequest hotelUpdateRequest);
}
