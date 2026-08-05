package com.fitzone.service;

import com.fitzone.dao.AttendanceDAO;
import com.fitzone.model.Attendance;
import com.fitzone.service.impl.AttendanceServiceImpl;
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

public class AttendanceServiceTest {

    @Mock
    private AttendanceDAO attendanceDAO;

    @InjectMocks
    private AttendanceServiceImpl attendanceService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCheckInMember() {
        when(attendanceDAO.recordCheckIn(1, "QR Code")).thenReturn(true);
        assertTrue(attendanceService.checkInMember(1, "QR Code"));
        verify(attendanceDAO, times(1)).recordCheckIn(1, "QR Code");
    }

    @Test
    public void testCheckOutMember() {
        when(attendanceDAO.recordCheckOut(10)).thenReturn(true);
        assertTrue(attendanceService.checkOutMember(10));
        verify(attendanceDAO, times(1)).recordCheckOut(10);
    }

    @Test
    public void testGetDailyAttendance() {
        Date today = new Date(System.currentTimeMillis());
        Attendance a = new Attendance();
        a.setId(1);
        a.setUserId(5);

        when(attendanceDAO.getDailyAttendance(today)).thenReturn(Collections.singletonList(a));

        List<Attendance> result = attendanceService.getDailyAttendance(today);
        assertEquals(1, result.size());
        assertEquals(5, result.get(0).getUserId());
    }
}
