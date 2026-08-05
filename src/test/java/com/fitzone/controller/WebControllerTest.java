package com.fitzone.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
               .andExpect(status().isOk());
    }

    @Test
    public void testDashboardPage() throws Exception {
        mockMvc.perform(get("/dashboard"))
               .andExpect(status().isOk());
    }

    @Test
    public void testMembersPage() throws Exception {
        mockMvc.perform(get("/members"))
               .andExpect(status().isOk());
    }

    @Test
    public void testTrainersPage() throws Exception {
        mockMvc.perform(get("/trainers"))
               .andExpect(status().isOk());
    }

    @Test
    public void testPackagesPage() throws Exception {
        mockMvc.perform(get("/packages"))
               .andExpect(status().isOk());
    }

    @Test
    public void testBookingsPage() throws Exception {
        mockMvc.perform(get("/bookings"))
               .andExpect(status().isOk());
    }

    @Test
    public void testPaymentsPage() throws Exception {
        mockMvc.perform(get("/payments"))
               .andExpect(status().isOk());
    }

    @Test
    public void testAttendancePage() throws Exception {
        mockMvc.perform(get("/attendance"))
               .andExpect(status().isOk());
    }

    @Test
    public void testReportsPage() throws Exception {
        mockMvc.perform(get("/reports"))
               .andExpect(status().isOk());
    }

    @Test
    public void testSettingsPage() throws Exception {
        mockMvc.perform(get("/settings"))
               .andExpect(status().isOk());
    }
}
