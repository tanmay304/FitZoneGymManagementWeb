package com.fitzone.dao;

import com.fitzone.model.Payment;
import java.util.List;

public interface PaymentDAO {
    List<Payment> getAllPayments();
    boolean addPayment(Payment payment);
    boolean updatePaymentStatus(int paymentId, String status);
    double getTotalRevenue();
    double getTodayRevenue();
    double getMonthlyRevenue();
}
