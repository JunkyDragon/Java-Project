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
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;

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
	private TextField tfName2;
	@FXML
	private ListView<String> lvList;
	private String name;
	private String singer;
	private String place;
	private int price;
	private String sponsor;
	private String datetime;
	private ObservableList<String> olist;
	Statement stmt = null;
	Connection conn = null;
	ResultSet rs = null;
	List<String> list = new ArrayList<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		getItems();
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

	private void datetime() {
		// TODO Auto-generated method stubad
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

	@FXML
	public void MakeAction(ActionEvent event) {
		try {
			name = tfName1.getText();
			singer = tfSinger.getText();
			place = tfPlace.getText();
			price = Integer.parseInt(tfPrice.getText());
			sponsor = tfSponsor.getText();
			datetime = dpDatetime.getEditor().getText();
			network();
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

	@FXML
	public void DeleteAction(ActionEvent event) {

		try {
			name = tfName2.getText();
			network();
			StringBuilder sb = new StringBuilder();
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

	@FXML
	public void inqueryAction(ActionEvent event) {
		lvList.getItems().clear();
		list.clear();
		olist.clear();
		getItems();
	}

	@FXML
	public void CloseAction(ActionEvent event) {
		Platform.exit();
	}

}