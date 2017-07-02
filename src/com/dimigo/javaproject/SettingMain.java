/**
 * 
 */
package com.dimigo.javaproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * <pre>
 * com.aperturecs.test
 *   |_ SettingMain
 * 1. 개요 : 
 * 2. 작성일 : 2017. 6. 28.
 * </pre>
 *
 * @author 		: Administrator
 * @version		: 1.0
 */
//To test FXML
public class SettingMain extends Application{
	public static void main(String[] args) {
		launch();
	}

	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("SettingGUI.fxml"));
		stage.setScene(new Scene(root));
		stage.setTitle("Setting");
		stage.show();
	}
}
