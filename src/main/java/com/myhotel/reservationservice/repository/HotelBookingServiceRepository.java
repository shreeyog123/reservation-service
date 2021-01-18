package com.myhotel.reservationservice.repository;

import com.myhotel.reservationservice.model.entity.BookingEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelBookingServiceRepository extends CrudRepository<BookingEntity, Long> {
}
