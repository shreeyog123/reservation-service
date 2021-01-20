package com.myhotel.reservationservice.servicesProxy;

import com.myhotel.reservationservice.model.GuestDetails;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "guest-service")
public interface GuestServiceProxy {

    @RequestMapping(value = "/guest/details/{guestId}", method = RequestMethod.GET)
    public GuestDetails getGuestDetails(@PathVariable("guestId") final Long guestId);


}
