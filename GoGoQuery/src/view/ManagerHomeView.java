package view;

import java.util.ArrayList;

import controller.ItemController;
import controller.LoginController;
import controller.TransactionController;
import entity.Transaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import jfxtras.labs.scene.control.window.Window;
import util.StageManager;
import util.UserSession;

public class ManagerHomeView {
	private final UserSession session = UserSession.getInstance();
	private final StageManager sm = StageManager.getInstance();
	private BorderPane bp;
	private VBox vb, vbQueue;
	private MenuBar menuBar;
	private Menu menuAction;
	private MenuItem miAddItem, miQueueManagement, miLogout;
	private Label lblWelcome, lblAddItem, lblItemName, lblItemDescription, lblItemCategory, lblItemPrice, lblItemQuantity, lblQueueManager;
	private TextField tfItemName, tfItemCategory, tfItemPrice;
	private TextArea tfItemDescription;
	private Spinner<Integer> spQuantity = new Spinner<Integer>();
	private Window winAdd, winQueue;
	private GridPane gpItem;
	private Button btnAddItem, btnSendPackage;
	private TableView<Transaction> tv;
	private ArrayList<Transaction> transactions;
	private TransactionController tc = new TransactionController();
//	buat transaction model
		
	public ManagerHomeView(ArrayList<Transaction> transactions) {
		this.transactions = transactions;
		initialize();
		addComponent();
		arrangeComponent();
		setMouseEvent();
	}
	
	private void setMouseEvent() {
		miAddItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				vb.getChildren().clear();
				vb.getChildren().add(winAdd);
				bp.setCenter(vb);
				winAdd.setVisible(true);
			}
		});
		
		miQueueManagement.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				vb.getChildren().clear();
				vb.getChildren().add(winQueue);
				bp.setCenter(vb);
				winQueue.setVisible(true);
			}
		});
		
		miLogout.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				session.cleanUserSession();
				
				LoginController lc = new LoginController();
				lc.index();				
			}
		});
		
		btnAddItem.setOnMouseClicked(event -> {
			String name = tfItemName.getText();
			String description = tfItemDescription.getText();
			String category = tfItemCategory.getText();
			String iPrice = tfItemPrice.getText();
			int quantity = spQuantity.getValue();
			double price = 0d;
			
			String errorMsg = "";
			if (name.isEmpty() || description.isEmpty() || category.isEmpty() || iPrice.isEmpty()) {
				errorMsg = "All fields must be filled out.";
			} else if (name.length() < 5 || name.length() > 70) {
				errorMsg = "Item name must be between 5 and 70 characters.";
			} else if (description.length() < 10 || name.length() > 255) {
				errorMsg = "Item description must be between 10 and 255 characters.";
			} else {
				try {
					price = Double.valueOf(iPrice);
				} catch (Exception e) {
				}
				
				if (price == 0d) {
					errorMsg = "Item price must be a valid number.";
				} else if (price < 0.50d || price > 900000d) {
					errorMsg = "Item price must be between $0.50 and $900,00";
				}
			}
			
			if (!errorMsg.isEmpty()) {
				Alert errAlert = new Alert(AlertType.ERROR);
				errAlert.setTitle("Error");
				errAlert.setHeaderText("Insert error!");
				errAlert.setContentText(errorMsg);
				errAlert.showAndWait();
				return;
			}

			ItemController ic = new ItemController();
			ic.insertItem(name, description, category, price, quantity);
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Insert Success");
			alert.setHeaderText("Insert Success!");
			alert.setContentText("Item added to product catalog.");
			alert.showAndWait();
		});
		
	    btnSendPackage.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	            // Get the selected transaction from the table
	            Transaction selectedTransaction = tv.getSelectionModel().getSelectedItem();

	            // Check if a row is selected
	            if (selectedTransaction == null) {
	                Alert alert = new Alert(AlertType.ERROR);
	                alert.setTitle("Reference Error");
	                alert.setHeaderText("Reference error due to no transaction selected");
	                alert.setContentText("Please select a transaction.");
	                alert.showAndWait();
	            } else {
	                // Send package and update status
	                tc.sendPackage(selectedTransaction.getTransactionId());

	                // Refresh table data after updating
	                refreshTable();
	            }
	        }
	    });
	}
	
	private void refreshTable() {
	    // Fetch the updated list of transactions from the model or database
	    ArrayList<Transaction> updatedTransactions = tc.getAllTransactions();

	    // Clear the old items and update with new data
	    ObservableList<Transaction> obsTransactions = FXCollections.observableArrayList(updatedTransactions);
	    tv.setItems(obsTransactions);
	    
	    // Optionally refresh the table view to force re-rendering
	    tv.refresh();
	}


	private void arrangeComponent() {
		lblWelcome.getStyleClass().addAll("text-3xl");
		winAdd.setPrefSize(350, 250);
		winAdd.setVisible(false);
		lblAddItem.getStyleClass().add("text-3xl");
		lblQueueManager.getStyleClass().add("text-3xl");
		gpItem.setHgap(32);
		gpItem.setVgap(32);
		vb.setPadding(new Insets(32));
		btnAddItem.getStyleClass().addAll("text-white", "bg-green", "cursor-pointer");
		btnSendPackage.getStyleClass().addAll("text-white", "bg-green", "cursor-pointer");
		winAdd.setPadding(new Insets(32));
	}

	@SuppressWarnings("unchecked")
	private void addComponent() {
		bp.setTop(menuBar);
		bp.setCenter(lblWelcome);
		menuBar.getMenus().add(menuAction);
		menuAction.getItems().addAll(miAddItem, miQueueManagement, miLogout);
		gpItem.add(lblAddItem, 0, 0);
		
		gpItem.add(lblItemName, 0, 1);
		gpItem.add(tfItemName, 1, 1);
		
		gpItem.add(lblItemDescription, 0, 2);
		gpItem.add(tfItemDescription, 1, 2);
		
		gpItem.add(lblItemCategory, 0, 3);
		gpItem.add(tfItemCategory, 1, 3);
		
		gpItem.add(lblItemPrice, 0, 4);
		gpItem.add(tfItemPrice, 1, 4);
		
		gpItem.add(lblItemQuantity, 0, 5);
		gpItem.add(spQuantity, 1, 5);
		
		gpItem.add(btnAddItem, 1, 6);
		winAdd.getContentPane().getChildren().add(gpItem);
		
		vbQueue.getChildren().addAll(lblQueueManager, tv, btnSendPackage);
		vbQueue.setPadding(new Insets(32));
		vbQueue.setSpacing(32);
		
		winQueue.getContentPane().getChildren().addAll(vbQueue);
		
        // Define columns
        TableColumn<Transaction, Integer> colTransactionId = new TableColumn<>("Transaction ID");
        colTransactionId.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        
        TableColumn<Transaction, Integer> colCustomerId = new TableColumn<>("Customer ID");
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        
        TableColumn<Transaction, String> colCustomerEmail = new TableColumn<>("Customer Email");
        colCustomerEmail.setCellValueFactory(new PropertyValueFactory<>("customerEmail"));
        
        TableColumn<Transaction, String> colDate = new TableColumn<>("Date");
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        
        TableColumn<Transaction, Double> colTotal = new TableColumn<>("Total");
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        
        TableColumn<Transaction, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        // Add columns to the table
        tv.getColumns().addAll(colTransactionId, colCustomerId, colCustomerEmail, colDate, colTotal, colStatus);
        
        // Fetch data from the model
        ObservableList<Transaction> obsTransactions = FXCollections.observableArrayList(transactions);
        
        // Set data to the TableView
        tv.setItems(obsTransactions);
        tv.setMinHeight(400);
	}

	private void initialize() {
		sm.getStage().setTitle("GoGoQuery - Manager Home");
		
		bp = new BorderPane();
		vb = new VBox();
		vbQueue = new VBox();
		menuBar = new MenuBar();
		menuAction = new Menu("Menu");
		miAddItem = new MenuItem("Add Item");
		miQueueManagement = new MenuItem("Queue Management");
		miLogout = new MenuItem("Logout");
		lblWelcome = new Label("Welcome to GoGoQuery Manager 2.0");
		winAdd= new Window("Add Item");
		winQueue = new Window("Queue Management");
		lblAddItem = new Label("Add Item");
		lblItemName = new Label("Item Name");
		lblItemDescription = new Label("Item Description");
		lblItemCategory = new Label("Item Category");
		lblItemPrice = new Label("Item Price");
		lblItemQuantity = new Label("Item Quantity");
		lblQueueManager = new Label("Queue Manager");
		tfItemName = new TextField();
		tfItemDescription = new TextArea();
		tfItemCategory = new TextField();
		tfItemPrice = new TextField();
		spQuantity = new Spinner<Integer>(1, 30, 1);
		gpItem = new GridPane();
		btnAddItem = new Button("Add Item");
		btnSendPackage = new Button("Send Package");
		tv = new TableView<Transaction>();
	}

	public Pane getPane() {
		return bp;
	}
}
