package com.myhotel.reservationservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BOOKING_TABLE")
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "BOOKING_ID")
    private long bookingId;

    @Column(name = "GUEST_ID")
    private long guestId;

    @Column(name = "NUMBER_GUEST")
    private long guests;

    @Column(name = "HOTEL_ID")
    private long hotelId;

    @Column(name = "ROOM_TYPE")
    private String roomType;

    @Column(name ="TIME_STAMP")
    private LocalDateTime updateTimeStamp;

    @Column(name = "BOOKING_START_DATE")
    private LocalDate bookingStartDate;

    @Column(name = "BOOKING_END_DATE")
    private LocalDate bookingEndDate;

    @Column(name = "BOOKING_STATUS")
    private String bookingStatus;

}
