package com.fitzone.dao.impl;

import com.fitzone.dao.PaymentDAO;
import com.fitzone.database.DatabaseConnection;
import com.fitzone.model.Payment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaymentDAOImpl implements PaymentDAO {

    private static final Logger logger =
            LoggerFactory.getLogger(PaymentDAOImpl.class);


    @Override
    public List<Payment> getAllPayments() {

        List<Payment> payments = new ArrayList<>();

        String sql =
                "SELECT p.*, " +
                "CONCAT(u.fname,' ',u.lname) AS member_name, " +
                "pkg.titlename AS package_name " +
                "FROM tblpayment p " +
                "LEFT JOIN tblbooking b ON p.bookingID = b.id " +
                "LEFT JOIN tbluser u ON b.userid = u.id " +
                "LEFT JOIN tbladdpackage pkg ON b.package_id = pkg.id " +
                "ORDER BY p.id DESC";


        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {


            while(rs.next()) {

                Payment payment = new Payment(
                        rs.getInt("id"),
                        rs.getString("bookingID"),
                        rs.getString("paymentType"),
                        rs.getString("payment"),
                        rs.getTimestamp("payment_date")
                );


                payment.setStatus(
                        rs.getString("status")
                );

                payment.setTransactionId(
                        rs.getString("transaction_id")
                );

                payment.setReceiptNo(
                        rs.getString("receipt_no")
                );

                payment.setPaymentReference(
                        rs.getString("payment_reference")
                );


                payment.setMemberName(
                        rs.getString("member_name")
                );

                payment.setPackageName(
                        rs.getString("package_name")
                );


                payments.add(payment);
            }


        } catch(SQLException e) {

            logger.error(
                    "Error loading payments from database",
                    e
            );
        }


        return payments;
    }



    @Override
    public boolean addPayment(Payment payment) {


        String sql =
                "INSERT INTO tblpayment " +
                "(bookingID,paymentType,payment,status," +
                "transaction_id,receipt_no,payment_reference) " +
                "VALUES(?,?,?,?,?,?,?)";


        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt =
                    conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {


            stmt.setString(1,payment.getBookingID());
            stmt.setString(2,payment.getPaymentType());
            stmt.setString(3,payment.getPayment());

            stmt.setString(
                    4,
                    payment.getStatus()!=null
                            ? payment.getStatus()
                            : "Paid"
            );

            stmt.setString(
                    5,
                    payment.getTransactionId()
            );

            stmt.setString(
                    6,
                    payment.getReceiptNo()
            );

            stmt.setString(
                    7,
                    payment.getPaymentReference()
            );


            int result = stmt.executeUpdate();


            if(result > 0) {

                try(ResultSet rs =
                            stmt.getGeneratedKeys()) {

                    if(rs.next()) {

                        payment.setId(
                                rs.getInt(1)
                        );
                    }
                }

                return true;
            }


        } catch(SQLException e) {

            logger.error(
                    "Error adding payment",
                    e
            );
        }


        return false;
    }




    @Override
    public boolean updatePaymentStatus(
            int paymentId,
            String status) {


        String sql =
                "UPDATE tblpayment SET status=? WHERE id=?";


        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt =
                    conn.prepareStatement(sql)) {


            stmt.setString(1,status);
            stmt.setInt(2,paymentId);


            return stmt.executeUpdate()>0;


        } catch(SQLException e) {

            logger.error(
                    "Error updating payment status",
                    e
            );
        }


        return false;
    }





    @Override
    public double getTotalRevenue() {


        String sql =
                "SELECT COALESCE(" +
                "SUM(CAST(payment AS DECIMAL(10,2))),0) " +
                "FROM tblpayment " +
                "WHERE payment IS NOT NULL " +
                "AND payment<>'' " +
                "AND (status IS NULL OR status='Paid')";


        return executeRevenueQuery(sql,"total revenue");
    }





    @Override
    public double getTodayRevenue() {


        String sql =
                "SELECT COALESCE(" +
                "SUM(CAST(payment AS DECIMAL(10,2))),0) " +
                "FROM tblpayment " +
                "WHERE DATE(payment_date)=CURDATE() " +
                "AND (status IS NULL OR status='Paid')";


        return executeRevenueQuery(sql,"today revenue");
    }





    @Override
    public double getMonthlyRevenue() {


        String sql =
                "SELECT COALESCE(" +
                "SUM(CAST(payment AS DECIMAL(10,2))),0) " +
                "FROM tblpayment " +
                "WHERE MONTH(payment_date)=MONTH(CURRENT_DATE()) " +
                "AND YEAR(payment_date)=YEAR(CURRENT_DATE()) " +
                "AND (status IS NULL OR status='Paid')";


        return executeRevenueQuery(sql,"monthly revenue");
    }





    private double executeRevenueQuery(
            String sql,
            String description) {


        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt =
                    conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {


            if(rs.next()) {

                return rs.getDouble(1);
            }


        } catch(SQLException e) {

            logger.error(
                    "Failed calculating " + description,
                    e
            );
        }


        return 0.0;
    }
}