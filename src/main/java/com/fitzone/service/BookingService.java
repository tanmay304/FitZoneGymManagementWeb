package com.fitzone.service;

import com.fitzone.model.Booking;
import java.util.List;

public interface BookingService {
    List<Booking> getAllBookings();
    boolean addBooking(Booking booking);
    boolean updateBookingStatus(int bookingId, String status);
    boolean renewBooking(int bookingId, java.sql.Date newExpiryDate);
    boolean deleteBooking(int id);
}
