/**
 * 
 */
package com.aperturecs.test;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * <pre>
 * com.aperturecs.test
 *   |_ StartMain
 * 1. 개요 : 
 * 2. 작성일 : 2017. 6. 25.
 * </pre>
 *
 * @author 		: Administrator
 * @version		: 1.0
 */
public class ClientMain extends Application{
	
	public static void main(String[] args) {
		launch();
	}
	
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("ClientGUI.fxml"));
		stage.setScene(new Scene(root));
		
		stage.setTitle("ClientMain");
		stage.show();
	}
}

