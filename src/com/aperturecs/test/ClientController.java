/**
 * 
 */
package com.aperturecs.test;

import javafx.application.Platform;
import javafx.event.ActionEvent;

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
public class ClientController {
	public void logoutAction(ActionEvent event) {
		Platform.exit();
	}
}
