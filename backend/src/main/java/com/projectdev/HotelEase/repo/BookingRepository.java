package com.projectdev.HotelEase.repo;

import com.projectdev.HotelEase.entity.User;


import com.projectdev.HotelEase.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findByBookingConfirmationCode(String confirmationCode);
}
