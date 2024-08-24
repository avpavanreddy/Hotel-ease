package com.projectdev.HotelEase.service.interfac;

import com.projectdev.HotelEase.dto.Response;
import com.projectdev.HotelEase.entity.Booking;

public interface IBookingService {

    Response saveBooking(Long rooId, Long userId, Booking bookingRequest);
    Response findBookingByConfirmationCode(String confirmationCode);
    Response getAllBookings();
    Response cancelBooking(Long bookingId);
}
