package com.fitzone.service.impl;

import com.fitzone.dao.PaymentDAO;
import com.fitzone.dao.impl.PaymentDAOImpl;
import com.fitzone.model.Payment;
import com.fitzone.service.PaymentService;
import java.util.List;

public class PaymentServiceImpl implements PaymentService {
    private final PaymentDAO paymentDAO;

    public PaymentServiceImpl() {
        this.paymentDAO = new PaymentDAOImpl();
    }

    public PaymentServiceImpl(PaymentDAO paymentDAO) {
        this.paymentDAO = paymentDAO;
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentDAO.getAllPayments();
    }

    @Override
    public boolean addPayment(Payment payment) {
        return paymentDAO.addPayment(payment);
    }

    @Override
    public boolean processPayment(Payment payment) {
        return paymentDAO.addPayment(payment);
    }

    @Override
    public boolean updatePaymentStatus(int paymentId, String status) {
        return paymentDAO.updatePaymentStatus(paymentId, status);
    }

    @Override
    public Payment getPaymentById(int paymentId) {
        List<Payment> all = paymentDAO.getAllPayments();
        for (Payment p : all) {
            if (p.getId() == paymentId) return p;
        }
        return null;
    }

    @Override
    public double getTotalRevenue() {
        return paymentDAO.getTotalRevenue();
    }

    @Override
    public double getTodayRevenue() {
        return paymentDAO.getTodayRevenue();
    }

    @Override
    public double getMonthlyRevenue() {
        return paymentDAO.getMonthlyRevenue();
    }
}
