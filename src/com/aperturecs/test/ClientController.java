/**
 * 
 */
package com.aperturecs.test;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

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
	@FXML
	ListView<String> lvList = new ListView<>();
	private ObservableList<String> olist;
	Statement stmt = null;
	Connection conn = null;
	ResultSet rs = null;
	List<String> list = new ArrayList<>();

	public void network() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/main", "root", "qwertyymca00");
			stmt = conn.createStatement();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException se) {
			se.printStackTrace();
		}

	}

	private void getItems() {
		try {
			network();
			StringBuilder sb = new StringBuilder();
			String sql = sb.append("select * from tickets;").toString();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				StringBuilder sb2 = new StringBuilder();
				sb2.append(rs.getString("name") + " ");
				sb2.append(rs.getString("sponsor") + " ");
				sb2.append(rs.getString("mainsingername") + " ");
				sb2.append(rs.getString("place") + " ");
				sb2.append(rs.getInt("price") + " ");
				sb2.append(rs.getString("date") + "\n");
				list.add(sb2.toString());
			}
			System.out.println(list);
			olist = FXCollections.observableArrayList(list);
			lvList.setItems(olist);
			lvList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void applicationAction(ActionEvent event) {
		System.out.println(lvList.getSelectionModel().getSelectedItem());
	}

	public void inquireAction(ActionEvent event) {
		lvList.getItems().clear();
		list.clear();
		olist.clear();
		getItems();
	}

	public void cancelAction(ActionEvent event) {

	}

	public void myticketAction(ActionEvent event) {

	}

	public void settingAction(ActionEvent event) {

	}

	public void logoutAction(ActionEvent event) {
		Platform.exit();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		getItems();
	}
}