package com.fitzone.service.impl;

import com.fitzone.dao.AttendanceDAO;
import com.fitzone.dao.impl.AttendanceDAOImpl;
import com.fitzone.model.Attendance;
import com.fitzone.service.AttendanceService;
import java.sql.Date;
import java.util.List;

public class AttendanceServiceImpl implements AttendanceService {
    private final AttendanceDAO attendanceDAO;

    public AttendanceServiceImpl() {
        this.attendanceDAO = new AttendanceDAOImpl();
    }

    public AttendanceServiceImpl(AttendanceDAO attendanceDAO) {
        this.attendanceDAO = attendanceDAO;
    }

    @Override
    public List<Attendance> getDailyAttendance(Date date) {
        return attendanceDAO.getDailyAttendance(date);
    }

    @Override
    public boolean checkInMember(int userId, String method) {
        return attendanceDAO.recordCheckIn(userId, method);
    }

    @Override
    public boolean checkOutMember(int attendanceId) {
        return attendanceDAO.recordCheckOut(attendanceId);
    }
}
