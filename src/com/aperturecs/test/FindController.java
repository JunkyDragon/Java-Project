/**
 * 
 */
package com.aperturecs.test;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.sql.*;

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
public class FindController {
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
	public String name;
	public String email;
	public String id;
	public void FindIDAction(ActionEvent event) {
		name = tfName1.getText();
		email = tfEmail1.getText();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/main", "root", "qwertyymca00");
			System.out.println("Connection OK");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM register_user where name = \"" + name + "\" && EMAIL =\""+ email+ "\";");
			System.out.println("SELECT * FROM register_user where name = \"" + name + "\" && EMAIL =\""+ email+ "\";");
			while (rs.next()) {
				id = rs.getString("ID").toString();
			}
			if (id == null) {
				lbID.setText("존재하지 않는 이름입니다.");
			} else {
				lbID.setText("ID : " + (String) id);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("driver error");
		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println("connection error");
		}
	}

	public void FindPasswordAction(ActionEvent event) {
		name = tfName2.getText();
		email = tfEmail2.getText();
		String id = tfID.getText();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/main", "root", "qwertyymca00");
			System.out.println("Connection OK");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM register_user where name = \"" + name + "\" && id = \"" + id + "\" && email = \"" + email + "\";");
			String password = null;
			while (rs.next()) {
				password = rs.getString("PASSWORD").toString();
			}
			if (password == null) {
				lbPasswd.setText("아이디 혹은 이름이 잘못되었습니다.");
			} else {
				lbPasswd.setText("password : " + (String) password);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("driver error");
		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println("connection error");
		}
	}

	public void closeAction(ActionEvent event) {
		StartController.oldstage.close();
	}
}
