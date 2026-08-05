package com.fitzone;

import com.fitzone.model.Member;
import com.fitzone.model.Payment;
import com.fitzone.model.Trainer;
import com.fitzone.util.PasswordUtil;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WebEntityTest {

    @Test
    public void testMemberModel() {
        Member m = new Member();
        m.setId(1);
        m.setFname("John");
        m.setLname("Doe");
        m.setEmail("john@fitzone.com");
        m.setMobile("9876543210");

        assertEquals(1, m.getId());
        assertEquals("John Doe", m.getFullName());
        assertEquals("john@fitzone.com", m.getEmail());
    }

    @Test
    public void testTrainerModel() {
        Trainer t = new Trainer(10, "Alex Smith", "alex@fitzone.com", "9998887776", "CrossFit", 30000.0, null, "Active");
        assertEquals(10, t.getId());
        assertEquals("Alex Smith", t.getName());
        assertEquals("CrossFit", t.getSpecialty());
    }

    @Test
    public void testPaymentModel() {
        Payment p = new Payment();
        p.setId(500);
        p.setPayment("1500");
        p.setPaymentMethod("UPI");
        p.setStatus("Paid");

        assertEquals(500, p.getId());
        assertEquals("1500", p.getPayment());
        assertEquals("UPI", p.getPaymentMethod());
    }

    @Test
    public void testPasswordUtil() {
        String hash = PasswordUtil.hashPasswordBCrypt("admin123");
        assertTrue(PasswordUtil.checkPasswordBCrypt("admin123", hash));
    }
}
