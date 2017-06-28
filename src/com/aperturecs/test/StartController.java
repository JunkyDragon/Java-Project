/**
 * 
 */
package com.aperturecs.test;

import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
public class StartController {
	@FXML
	private TextField tfID;
	@FXML
	private TextField tfPassword;
	@FXML
	private Label message;
	private String id;
	private String passwd;
	private String getID;
	private boolean getPer;
	private String getPasswd;
	public static Stage oldstage;

	public void Actionhandler(ActionEvent event) throws IOException {
		id = tfID.getText();
		passwd = tfPassword.getText();
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/main", "root", "qwertyymca00");
			StringBuilder sb = new StringBuilder();
			String sql = sb.append("select * from register_user where id = \"" + id + "\";").toString();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				getID = rs.getString("ID");
				getPasswd = rs.getString("PASSWORD");
				getPer = rs.getBoolean("PERMISSION");
			}
			if (getPasswd == null || getID == null) {
				message.setText("잘못된 아이디 혹은 비밀번호입니다.");
				return;
			}
			if (passwd.equals(getPasswd)) {
				if (getPer) {
					message.setText("일반인");
					Stage stage = new Stage();
					Parent root = FXMLLoader.load(getClass().getResource("ClientGUI.fxml"));
					Scene scene = new Scene(root);
					stage.setScene(scene);
					stage.show();
					StartMain.getStage().close();
				} else {
					Stage stage = new Stage();
					message.setText("어드민");
					Parent root = FXMLLoader.load(getClass().getResource("AdminGUI.fxml"));
					Scene scene = new Scene(root);
					stage.setScene(scene);
					stage.show();
					StartMain.getStage().close();
				}
			} else {
				message.setText("아이디나 비밀번호가 틀립니다.");
			}
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void FindActionhandler(ActionEvent event) {
		try {
			Stage stage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("FindGUI.fxml"));
			stage.setScene(new Scene(root));
			stage.show();
			oldstage = stage;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void RegisterActionhandler(ActionEvent event) {
		try {
			Stage stage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("RegisterGUI.fxml"));
			stage.setScene(new Scene(root));
			oldstage = stage;
			stage.show();
			StartMain.getStage().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	public void CloseAction(ActionEvent event) {
		Platform.exit();
	}
}