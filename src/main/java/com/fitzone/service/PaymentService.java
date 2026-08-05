package com.fitzone.service;

import com.fitzone.model.Payment;
import java.util.List;

public interface PaymentService {
    List<Payment> getAllPayments();
    boolean addPayment(Payment payment);
    boolean processPayment(Payment payment);
    boolean updatePaymentStatus(int paymentId, String status);
    Payment getPaymentById(int paymentId);
    double getTotalRevenue();
    double getTodayRevenue();
    double getMonthlyRevenue();
}
