package info.knigoed.dao;

import info.knigoed.pojo.InvoicePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

@Component
public class InvoiceDao implements Invoice {

    @Autowired
    private Sql2o sql2o;

    @Override
    public int createInvoice(int targetId, double amount) {
        String insertSql
                = "INSERT INTO payment (paymentId, targetId, amount, created) "
                + "VALUES (NULL, :targetId, :amount, NOW())";

        try (Connection con = sql2o.open()) {
            con.createQuery(insertSql)
                    .addParameter("targetId", targetId)
                    .addParameter("amount", amount)
                    .executeUpdate();
        }
        return 1; 
    }

    @Override
    public InvoicePojo readInvoice(int paymentId, int userId) {
        String sql = "SELECT p.invoiceId, p.targetId, p.created, p.shopName, , s.title, s.shopId, s.domain FROM shop s, payment p "
                + "WHERE p.targetId = s.shopId AND p.paymentId = :paymentId AND s.userId = :userId";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).addParameter("paymentId", paymentId).addParameter("userId", userId)
                    .executeAndFetchFirst(InvoicePojo.class);
        }
    }

}
