/**
 * 
 */
package com.aperturecs.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
public class RegisterController {
	@FXML
	private TextField tfName;
	@FXML
	private TextField tfID;
	@FXML
	private TextField tfPassword;
	@FXML
	private TextField tfEmail;
	@FXML
	private Label lbmessage;
	private String id;
	private String passwd;
	private String name;
	private String email;
	private static Stage oldstage;

	@FXML
	public void Actionhandler(ActionEvent event) {
		id = tfID.getText();
		passwd = tfPassword.getText();
		name = tfName.getText();
		email = tfEmail.getText();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/main", "root", "qwertyymca00");
			Statement stmt = conn.createStatement();
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
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			Platform.exit();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void CloseAction(ActionEvent event) {
		Platform.exit();
	}

	public static Stage getStage() {
		return oldstage;
	}

}