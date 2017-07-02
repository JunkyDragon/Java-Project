/**
 * 
 */
package com.dimigo.javaproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

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
public class FindController implements Initializable {
	// FXML params
	@FXML
	private TextField tfName1;
	@FXML
	private Label lbID;
	@FXML
	private TextField tfName2;
	@FXML
	private TextField tfID;
	@FXML
	private Label lbPasswd;
	@FXML
	private TextField tfPasswd;
	@FXML
	private TextField tfEmail1;
	@FXML
	private TextField tfEmail2;
	@FXML
	private Button closeBtn;

	// info params
	public String name;
	public String email;
	public String id;

	// mysql params
	private Statement stmt;
	private Connection conn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		try {
			// database connection
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://javaproject.c4rqyk8nyct0.ap-northeast-2.rds.amazonaws.com:3306/main", "root",
					"qwertyymca00");
			stmt = conn.createStatement();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	// Method for find ID
	public void FindIDAction(ActionEvent event) {
		// get info and check
		name = tfName1.getText();
		email = tfEmail1.getText();
		try {
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM register_user where name = \"" + name + "\" && EMAIL =\"" + email + "\";");
			while (rs.next()) {
				id = rs.getString("ID").toString();
			}
			if (id == null) {
				lbID.setText("존재하지 않는 이름입니다.");
			} else {
				lbID.setText("ID : " + (String) id);
			}
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	// Method for find password
	public void FindPasswordAction(ActionEvent event) {
		// get info and check
		name = tfName2.getText();
		email = tfEmail2.getText();
		String id = tfID.getText();
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM register_user where name = \"" + name + "\" && id = \"" + id
					+ "\" && email = \"" + email + "\";");
			String password = null;
			while (rs.next()) {
				password = rs.getString("PASSWORD").toString();
			}
			if (password == null) {
				lbPasswd.setText("아이디 혹은 이름이 잘못되었습니다.");
			} else {
				lbPasswd.setText("password : " + (String) password);
			}
		} catch (SQLException se) {
			se.printStackTrace();

		}
	}

	public void closeAction(ActionEvent event) {
		try {
			Stage stage = (Stage) closeBtn.getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("StartGUI.FXML"));
			Scene scene = new Scene(root);
			stage.setTitle("Main");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
