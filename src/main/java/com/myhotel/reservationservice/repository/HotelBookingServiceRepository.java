package com.myhotel.reservationservice.repository;

import com.myhotel.reservationservice.model.entity.BookingEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelBookingServiceRepository extends CrudRepository<BookingEntity, Long> {

    @Query(value = "from BookingEntity where guestId = :guestId")
    List<BookingEntity> getBookingInformationByGuestId(@Param("guestId") long guestId);
}
