package com.fitzone.service.impl;

import com.fitzone.dao.BookingDAO;
import com.fitzone.dao.impl.BookingDAOImpl;
import com.fitzone.model.Booking;
import com.fitzone.service.BookingService;
import java.util.List;

public class BookingServiceImpl implements BookingService {
    private final BookingDAO bookingDAO;

    public BookingServiceImpl() {
        this.bookingDAO = new BookingDAOImpl();
    }

    public BookingServiceImpl(BookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO;
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingDAO.getAllBookings();
    }

    @Override
    public boolean addBooking(Booking booking) {
        return bookingDAO.addBooking(booking);
    }

    @Override
    public boolean updateBookingStatus(int bookingId, String status) {
        return bookingDAO.updateBookingStatus(bookingId, status);
    }

    @Override
    public boolean renewBooking(int bookingId, java.sql.Date newExpiryDate) {
        return bookingDAO.renewBooking(bookingId, newExpiryDate);
    }

    @Override
    public boolean deleteBooking(int id) {
        return bookingDAO.deleteBooking(id);
    }
}
