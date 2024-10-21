package entity;

public class Transaction {
	public int transactionId;
	public int customerId;
	public String customerEmail;
	public String date;
	public double total;
	public String status;

	public Transaction(int transactionId, int customerId, String customerEmail, String date, double total,
			String status) {
		this.transactionId = transactionId;
		this.customerId = customerId;
		this.customerEmail = customerEmail;
		this.date = date;
		this.total = total;
		this.status = status;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
