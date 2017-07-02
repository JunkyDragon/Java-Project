/**
 * 
 */
package com.dimigo.javaproject;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;

/**
 * <pre>
 * com.aperturecs.test
 *   |_ StartController
 * 1. 개요 : 
 * 2. 작성일 : 2017. 6. 25.
 * </pre>
 *
 * @author : Administrator
 * @version : 1.0
 */
public class ClientController implements Initializable {

	// FXML params
	@FXML
	private ListView<String> lvItem = new ListView<String>();
	@FXML
	private ListView<String> lvList = new ListView<String>();
	@FXML
	private Button logoutBtn;
	
	// Listview params
	private ObservableList<String> olItem;
	private ObservableList<String> olList;
	private List<String> lItem = new ArrayList<>();
	private List<String> lList = new ArrayList<>();

	// mysql params
	private StringBuilder sb = new StringBuilder();
	private String lists;
	private String sql;
	private Statement stmt;
	private Connection conn;
	private ResultSet rs;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		try {
			// database connection
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://javaproject.c4rqyk8nyct0.ap-northeast-2.rds.amazonaws.com:3306/main", "root",
					"qwertyymca00");
			stmt = conn.createStatement();

			// get listview
			resetItem();
			getItem();
			setItem();
			resetList();
			getList();
			setList();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	// get tickets from database
	public void getItem() {
		try {
			StringBuilder sb = new StringBuilder();
			String sql = sb.append("select * from tickets;").toString();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				StringBuilder sb2 = new StringBuilder();
				sb2.append(rs.getString("name"));
				lItem.add(sb2.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// get user's tickets from database
	public void getList() {
		try {
			StringBuilder sb = new StringBuilder();
			String sql = sb.append("select * from list where id = \"" + StartController.id + "\";").toString();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				lists = rs.getString("list");
			}
			if (lists != null && !"".equals(lists)) {
				lList = new ArrayList<>(Arrays.asList(lists.split("-")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// show tickets listview
	public void setItem() {
		olItem = FXCollections.observableArrayList(lItem);
		lvItem.setItems(olItem);
		lvItem.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}

	// show user's tickets listview
	public void setList() {
		olList = FXCollections.observableArrayList(lList);
		lvList.setItems(olList);
		lvList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}

	// reset tickets listview
	public void resetItem() {
		if (!lvItem.getItems().isEmpty()) {
			lvItem.getItems().clear();
		}
		if (!lItem.isEmpty()) {
			lItem.clear();
		}
		if (olItem != null) {
			olItem.clear();
		}
	}

	// reset user's tickets listview
	public void resetList() {
		if (!lvList.getItems().isEmpty()) {
			lvList.getItems().clear();
		}
		if (!lList.isEmpty()) {
			lList.clear();
		}
		if (olList != null) {
			olList.clear();
		}
	}

	// Method for application tickets
	@FXML
	public void applicationAction(ActionEvent event) {
		try {
			getList();
			if (lvItem.getSelectionModel().getSelectedItem() == null
					|| "".equals(lvItem.getSelectionModel().getSelectedItem())) {
				return;
			}
			if (lList.contains(lvItem.getSelectionModel().getSelectedItem())) {
				return;
			}
			sb = new StringBuilder();
			lList.add(lvItem.getSelectionModel().getSelectedItem());
			for (String string : lList) {
				sb.append(string);
				sb.append("-");
			}
			lists = sb.toString();
			sb = new StringBuilder();
			sql = sb.append("update list set list = \"" + lists + "\" where id = \"" + StartController.id + "\";")
					.toString();
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resetList();
		getList();
		setList();
	}

	// Method for cancel tickets
	@FXML
	public void cancelAction(ActionEvent event) {
		try {
			getList();
			sb = new StringBuilder();
			if (lvList.getSelectionModel().getSelectedItem() == null
					|| "".equals(lvList.getSelectionModel().getSelectedItem())) {
				return;
			}
			int where = lList.indexOf(lvList.getSelectionModel().getSelectedItem());
			lList.remove(where);
			for (String string : lList) {
				sb.append(string);
				sb.append("-");
			}
			lists = sb.toString();
			sb = new StringBuilder();
			sql = sb.append("update list set list = \"" + lists + "\" where id = \"" + StartController.id + "\";")
					.toString();
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		resetList();
		getList();
		setList();
	}

	// Refresh tickets list
	@FXML
	public void refreshAction(ActionEvent event) {
		resetItem();
		getItem();
		setItem();
	}

	// Refresh user's tickets list
	@FXML
	public void myticketAction(ActionEvent event) {
		resetList();
		getList();
		setList();
	}

	// method for move client's setting
	@FXML
	public void settingAction(ActionEvent event) {
		try {
			Stage stage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("SettingGUI.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("설정");
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Method for logout
	@FXML
	public void logoutAction(ActionEvent event) throws Exception {
		Stage stage = (Stage) logoutBtn.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("StartGUI.fxml"));
		Scene scene = new Scene(root);
		stage.setTitle("Main");
		stage.setScene(scene);
		stage.show();
	}
}