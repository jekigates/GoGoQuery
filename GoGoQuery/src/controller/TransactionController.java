package controller;

import java.util.ArrayList;

import entity.Transaction;
import model.TransactionModel;
import util.StageManager;
import view.ManagerHomeView;

public class TransactionController {
	private final StageManager sm = StageManager.getInstance();
	
	public void index() {
		ArrayList<Transaction> transactions = getAllTransactions();
		ManagerHomeView mhv = new ManagerHomeView(transactions);
		sm.getStage().getScene().setRoot(mhv.getPane());
	}
	
	public void sendPackage(int transactionId) {
		TransactionModel.sendPackage(transactionId);
	}

	public ArrayList<Transaction> getAllTransactions() {
		ArrayList<Transaction> transactions = TransactionModel.getAllTransactions();
		return transactions;
	}

}
