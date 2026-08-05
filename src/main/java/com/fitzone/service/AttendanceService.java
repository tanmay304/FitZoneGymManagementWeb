package com.fitzone.service;

import com.fitzone.model.Attendance;
import java.sql.Date;
import java.util.List;

public interface AttendanceService {
    List<Attendance> getDailyAttendance(Date date);
    boolean checkInMember(int userId, String method);
    boolean checkOutMember(int attendanceId);
}
