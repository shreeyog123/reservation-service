package com.myhotel.reservationservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BOOKING_TABLE")
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "BOOKING_ID")
    private Long bookingId;

    @Column(name = "GUEST_ID")
    private String guestId;

    @Column(name = "HOTEL_ID")
    private String hotelId;
}
