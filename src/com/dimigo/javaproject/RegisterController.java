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
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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
public class RegisterController implements Initializable {
	// FXML params
	@FXML
	private TextField tfName;
	@FXML
	private TextField tfID;
	@FXML
	private PasswordField tfPassword = new PasswordField();
	@FXML
	private TextField tfEmail;
	@FXML
	private Label lbmessage;
	@FXML
	private Button cancelBtn;
	@FXML
	private Button submitBtn;
	

	// Method for get info
	private String id;
	private String passwd;
	private String name;
	private String email;

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
		}
	}

	// Method for apply
	@FXML
	public void Actionhandler(ActionEvent event) throws Exception {
		// get info and check
		id = tfID.getText();
		passwd = tfPassword.getText();
		name = tfName.getText();
		email = tfEmail.getText();
		if (id == null || passwd == null || name == null || email == null || "".equals(id) || "".equals(passwd)
				|| "".equals(name) || "".equals(email)) {
			lbmessage.setText("빈칸이 존재합니다.");
			return;
		}
		try {
			StringBuilder sb = new StringBuilder();
			String sql = sb.append("select * from register_user where id = \"" + id + "\";").toString();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				if (id.equals(rs.getString("ID"))) {
					lbmessage.setText("중복되는 ID가 존재합니다");
					return;
				}
			}
			sb = new StringBuilder();
			sql = sb.append("select * from register_user where email = \"" + email + "\";").toString();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				if (email.equals(rs.getString("EMAIL"))) {
					lbmessage.setText("중복되는 이메일이 존재합니다.");
					return;
				}
			}
			sb = new StringBuilder();
			sql = sb.append("insert into register_user values(\"" + id + "\", \"" + passwd + "\", \"" + name
					+ "\", true, \"" + email + "\" );").toString();
			stmt.executeUpdate(sql);
			sb = new StringBuilder();
			sql = sb.append("insert into list values(\"" + id + "\", " + null + ");").toString();
			stmt.executeUpdate(sql);
			Stage stage = (Stage) submitBtn.getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("StartGUI.fxml"));
			Scene scene = new Scene(root);
			stage.setTitle("Main");
			stage.setScene(scene);
			stage.show();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Method for exit
	public void CloseAction(ActionEvent event) throws Exception {
		Stage stage = (Stage) cancelBtn.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("StartGUI.fxml"));
		Scene scene = new Scene(root);
		stage.setTitle("Main");
		stage.setScene(scene);
		stage.show();
	}


}