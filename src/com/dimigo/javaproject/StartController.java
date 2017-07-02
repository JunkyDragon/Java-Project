/**
 * 
 */
package com.dimigo.javaproject;

import java.io.IOException;
import java.net.URL;

import javafx.application.Platform;
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
public class StartController implements Initializable {
	// FXML params
	@FXML
	private TextField tfID;
	@FXML
	private TextField tfPassword;
	@FXML
	private Label message;
	@FXML
	private Button loginBtn; 
	@FXML
	private Button registerBtn;
	@FXML
	private Button findBtn;
	
	// get login info params
	public static String id;
	private String passwd;
	private String name;
	private String getID;
	private boolean getPer;
	private String getPasswd;

	// get Stage params
	public static Stage oldstage;

	// mySQL Connection params
	private Statement stmt;
	private Connection conn;
	private ResultSet rs;

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
			e.printStackTrace();
		}
	}

	// Login Method
	public void Actionhandler(ActionEvent event) throws IOException {
		// Get id & password from textfield
		id = tfID.getText();
		passwd = tfPassword.getText();

		try {
			// Get id & password from database
			StringBuilder sb = new StringBuilder();
			String sql = sb.append("select * from register_user where id = \"" + id + "\";").toString();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				getID = rs.getString("ID");
				getPasswd = rs.getString("PASSWORD");
				getPer = rs.getBoolean("PERMISSION");
				name = rs.getString("NAME");
			}
			if (getPasswd == null || getID == null) {
				message.setText("잘못된 아이디 혹은 비밀번호입니다.");
				return;
			}
			// Compare database info and textfield info
			if (passwd.equals(getPasswd)) {
				if (getPer) {
					message.setText("일반인");
					Stage stage = (Stage) loginBtn.getScene().getWindow();
					Parent root = FXMLLoader.load(getClass().getResource("ClientGUI.fxml"));
					Scene scene = new Scene(root);
					stage.setTitle(name + "님 환영합니다.");
					stage.setScene(scene);
					stage.show();
				} else {
					Stage stage = (Stage) loginBtn.getScene().getWindow();
					message.setText("어드민");
					Parent root = FXMLLoader.load(getClass().getResource("AdminGUI.fxml"));
					Scene scene = new Scene(root);
					stage.setTitle("관리자 페이지");
					stage.setScene(scene);
					stage.show();
				}
			} else {
				message.setText("아이디나 비밀번호가 틀립니다.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// Move to find Action
	public void FindActionhandler(ActionEvent event) {
		try {
			Stage stage = (Stage) findBtn.getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("FindGUI.fxml"));
			stage.setScene(new Scene(root));
			stage.setTitle("아이디 비밀번호 찾기");
			stage.show();
			oldstage = stage;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Move to register action
	public void RegisterActionhandler(ActionEvent event) {
		try {
			Stage stage = (Stage) registerBtn.getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("RegisterGUI.fxml"));
			stage.setScene(new Scene(root));
			stage.setTitle("가입하기");
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Exit
	public void CloseAction(ActionEvent event) {
		Platform.exit();
	}
}