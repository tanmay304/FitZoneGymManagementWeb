package com.fitzone.service;

import com.fitzone.dao.PaymentDAO;
import com.fitzone.model.Payment;
import com.fitzone.service.impl.PaymentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PaymentServiceTest {

    @Mock
    private PaymentDAO paymentDAO;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetTotalRevenue() {
        when(paymentDAO.getTotalRevenue()).thenReturn(150000.0);
        assertEquals(150000.0, paymentService.getTotalRevenue());
    }

    @Test
    public void testGetTodayRevenue() {
        when(paymentDAO.getTodayRevenue()).thenReturn(4500.0);
        assertEquals(4500.0, paymentService.getTodayRevenue());
    }

    @Test
    public void testGetPaymentById() {
        Payment p = new Payment();
        p.setId(99);
        p.setReceiptNo("REC-99");

        when(paymentDAO.getAllPayments()).thenReturn(Collections.singletonList(p));

        Payment result = paymentService.getPaymentById(99);
        assertNotNull(result);
        assertEquals("REC-99", result.getReceiptNo());
    }
}
