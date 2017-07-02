package info.knigoed.dao;

public interface Invoice {

    public int createInvoice(int targetId, double amount);

    public info.knigoed.pojo.InvoicePojo readInvoice(int paymentId, int userId);
}
