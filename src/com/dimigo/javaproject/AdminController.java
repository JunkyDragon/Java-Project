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
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
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
public class AdminController implements Initializable {
	// FXML params
	@FXML
	private TextField tfName1;
	@FXML
	private TextField tfSinger;
	@FXML
	private TextField tfPlace;
	@FXML
	private TextField tfPrice;
	@FXML
	private TextField tfSponsor;
	@FXML
	private DatePicker dpDatetime;
	@FXML
	private ListView<String> lvList;
	@FXML
	private Button logoutBtn;
	
	// basic info params
	private String name;
	private String singer;
	private String place;
	private int price;
	private String sponsor;
	private String datetime;

	// listview params
	private ObservableList<String> olist;
	private List<String> list = new ArrayList<>();

	// mysql params
	private Statement stmt = null;
	private Connection conn = null;
	private ResultSet rs = null;

	// prevent error param
	private String regex = "\\d+";

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Database connection
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://javaproject.c4rqyk8nyct0.ap-northeast-2.rds.amazonaws.com:3306/main", "root",
					"qwertyymca00");
			stmt = conn.createStatement();
		} catch (Exception e) {
			// TODO: handle exception
		}
		getItems();
	}

	// Get Item list from database
	private void getItems() {
		// Listview Clean
		if (!lvList.getItems().isEmpty()) {
			lvList.getItems().clear();
		}
		if (!list.isEmpty()) {
			list.clear();
		}
		if (olist != null) {
			olist.clear();
		}
		// Get List
		try {
			StringBuilder sb = new StringBuilder();
			String sql = sb.append("select * from tickets;").toString();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				StringBuilder sb2 = new StringBuilder();
				sb2.append(rs.getString("name") + "-");
				sb2.append(rs.getString("sponsor") + "-");
				sb2.append(rs.getString("mainsingername") + "-");
				sb2.append(rs.getString("place") + "-");
				sb2.append(rs.getInt("price") + "-");
				sb2.append(rs.getString("date") + "\n");
				list.add(sb2.toString());
			}
			olist = FXCollections.observableArrayList(list);
			lvList.setItems(olist);
			lvList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Change datetime form
	private void datetime() {
		StringBuilder sb = new StringBuilder();
		List<String> myList = new ArrayList<String>(Arrays.asList(datetime.split("")));
		for (int i = 0; i < myList.size(); i++) {
			if (myList.get(i).equals(" ")) {
				myList.remove(i);
			}
			if (myList.get(i).equals(".")) {
				myList.set(i, "-");
			}
			sb.append(myList.get(i));
		}
		datetime = sb.toString();
	}

	// Make new ticket Method
	@FXML
	public void MakeAction(ActionEvent event) {
		try {
			// Checking params
			name = tfName1.getText();
			singer = tfSinger.getText();
			place = tfPlace.getText();
			if (!tfPrice.getText().matches(regex)) {
				System.out.println("생성에 실패하였습니다.");
				return;
			}
			price = Integer.parseInt(tfPrice.getText());
			sponsor = tfSponsor.getText();
			datetime = dpDatetime.getEditor().getText();
			if (name == null || singer == null || place == null || sponsor == null || datetime == null
					|| "".equals(name) || "".equals(place) || "".equals(sponsor) || "".equals(datetime)) {
				System.out.println("생성에 실패하였습니다.");
				return;
			}
			// insert params
			datetime();
			StringBuilder sb = new StringBuilder();
			String sql = sb.append("insert into tickets values(\"" + name + "\", \"" + sponsor + "\", \"" + singer
					+ "\",\"" + place + "\", " + price + ",\"" + datetime + "\" );").toString();
			stmt = conn.createStatement();
			int r = stmt.executeUpdate(sql);
			if (r == 1) {
				System.out.println("생성에 성공하였습니다.");
			} else {
				System.out.println("생성에 실패하였습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Delete ticket method
	@FXML
	public void DeleteAction(ActionEvent event) {
		// Checking selected one
		StringBuilder sb = new StringBuilder();
		if (lvList.getSelectionModel().getSelectedItem() == null
				|| "".equals(lvList.getSelectionModel().getSelectedItem())) {
			return;
		}
		List<String> myList = new ArrayList<String>(
				Arrays.asList(lvList.getSelectionModel().getSelectedItem().split("-")));

		name = myList.get(0);
		// delete selected one
		try {

			sb = new StringBuilder();
			String sql = sb.append("delete from tickets where name = \"" + name + "\";").toString();
			int r = stmt.executeUpdate(sql);
			if (r == 1) {
				System.out.println("제거에 성공하였습니다.");
			} else {
				System.out.println("제거에 실패하였습니다.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// refresh List method
	@FXML
	public void refreshAction(ActionEvent event) {
		lvList.getItems().clear();
		list.clear();
		olist.clear();
		getItems();
	}

	// Logout method
	@FXML
	public void CloseAction(ActionEvent event) throws Exception {
		Stage stage = (Stage) logoutBtn.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("StartGUI.fxml"));
		Scene scene = new Scene(root);
		stage.setTitle("Main");
		stage.setScene(scene);
		stage.show();
	}

}