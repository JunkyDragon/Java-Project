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
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

/**
 * <pre>
 * com.aperturecs.test
 *   |_ SettingMain
 * 1. 개요 : 
 * 2. 작성일 : 2017. 6. 28.
 * </pre>
 *
 * @author : Administrator
 * @version : 1.0
 */
public class SettingController implements Initializable{
	@FXML
	private PasswordField psCurrent = new PasswordField();
	@FXML
	private PasswordField psChange = new PasswordField();
	@FXML
	private PasswordField psAgain = new PasswordField();
	@FXML
	private PasswordField psQuit = new PasswordField();
	@FXML
	private Label lbmessage;
	private Connection conn;
	private Statement stmt;
	private StringBuilder sb;
	private String sql;
	private ResultSet rs;
	private String passwd;
	private String getPasswd;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://javaproject.c4rqyk8nyct0.ap-northeast-2.rds.amazonaws.com:3306/main", "root", "qwertyymca00");
			stmt = conn.createStatement();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	@FXML
	public void SubmitAction(ActionEvent event) {
		try {
			passwd = psCurrent.getText();
			if (passwd == null || "".equals(passwd)) {
				lbmessage.setText("비밀번호를 입력해주세요");
				return;
			}
			sb = new StringBuilder();
			sb.append("select * from register_user where id = \"" + StartController.id +"\";");
			sql = sb.toString();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				getPasswd = rs.getString("PASSWORD");
			}
			if(getPasswd == null || "".equals(getPasswd) || !getPasswd.equals(passwd)) {
				lbmessage.setText("비밀번호가 다릅니다.");
				return;
			}
			if (psAgain.getText() == null || "".equals(psAgain.getText()) || psChange.getText() == null || "".equals(psChange.getText())) {
				lbmessage.setText("빈칸이 있습니다.");
				return;
			}
			if (!psChange.getText().equals(psAgain.getText())) {
				lbmessage.setText("입력된 비밀번호가 다릅니다");
			}
			sb = new StringBuilder();
			sb.append("update register_user set password = \"" + psChange.getText() + "\" where id = \"" + StartController.id +"\";");
			sql = sb.toString();
			stmt.executeUpdate(sql);
			sb = new StringBuilder();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Platform.exit();
	}

	@FXML
	public void QuitAction(ActionEvent event) {
		try {
			passwd = psQuit.getText();
			if (passwd == null || "".equals(passwd)) {
				lbmessage.setText("비밀번호를 입력해주세요");
				return;
			}
			sb = new StringBuilder();
			sb.append("select * from register_user where id = \"" + StartController.id +"\";");
			sql = sb.toString();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				getPasswd = rs.getString("PASSWORD");
			}
			if(getPasswd == null || "".equals(getPasswd)) {
				lbmessage.setText("비밀번호가 다릅니다.");
				return;
			}
			if(!getPasswd.equals(passwd)) {
				lbmessage.setText("비밀번호가 다릅니다.");
				return;
			}
			sb = new StringBuilder();
			sb.append("delete from register_user where id = \"" + StartController.id +"\";");
			sql = sb.toString();
			stmt.executeUpdate(sql);
			sb = new StringBuilder();
			sb.append("delete from list where id = \"" + StartController.id +"\";");
			sql = sb.toString();
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Platform.exit();
	}

	@FXML
	public void CloseAction(ActionEvent event) {
		Platform.exit();
	}
}
