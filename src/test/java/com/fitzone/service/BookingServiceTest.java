package com.fitzone.service;

import com.fitzone.dao.BookingDAO;
import com.fitzone.model.Booking;
import com.fitzone.service.impl.BookingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.sql.Date;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookingServiceTest {

    @Mock
    private BookingDAO bookingDAO;

    @InjectMocks
    private BookingServiceImpl bookingService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllBookings() {
        Booking b = new Booking();
        b.setId(101);
        b.setStatus("Active");

        when(bookingDAO.getAllBookings()).thenReturn(Collections.singletonList(b));

        List<Booking> list = bookingService.getAllBookings();
        assertEquals(1, list.size());
        assertEquals("Active", list.get(0).getStatus());
    }

    @Test
    public void testRenewBooking() {
        Date newDate = new Date(System.currentTimeMillis() + 86400000L);
        when(bookingDAO.renewBooking(101, newDate)).thenReturn(true);

        assertTrue(bookingService.renewBooking(101, newDate));
        verify(bookingDAO, times(1)).renewBooking(101, newDate);
    }
}
