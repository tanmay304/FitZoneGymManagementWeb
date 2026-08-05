package com.fitzone.dao;

import com.fitzone.model.Attendance;
import java.sql.Date;
import java.util.List;

public interface AttendanceDAO {
    List<Attendance> getDailyAttendance(Date date);
    boolean recordCheckIn(int userId, String method);
    boolean recordCheckOut(int attendanceId);
}
