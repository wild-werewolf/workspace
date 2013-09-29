package ru.murzoid.project.client.login;

import ru.murzoid.project.client.LogonManager;
import ru.murzoid.project.client.MD5.MD5Calculator;
import ru.murzoid.project.shared.LoginResponce;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LoginPanel extends DecoratedPopupPanel {
	
	private HorizontalPanel layout;
	private VerticalPanel vp;
	private VerticalPanel loginPanel;
	private LoginData loginData;

	public LoginPanel() {
		super();
		loginData=new LoginData();
		initial();
	}
	
	public LoginPanel(LoginData data) {
		super();
		if(data==null){
			loginData=new LoginData();
		}
		loginData=data;
		initial();
	}
	
	private void initial(){
		loginData.addLoginListener(new LoginListener() {
			
			@Override
			public void onChange() {
				toCenter();
				setVisible(!loginData.isSuccess());
			}
		});
		final Button sendButton = new Button("Login");
//		final Button createButton = new Button("Registered");
		final PasswordTextBox passField = new PasswordTextBox();
		final TextBox nameField = new TextBox();
		nameField.setText(loginData.getUserName());
		final Label errorLabel = new Label();

		layout = new HorizontalPanel();
		vp = new VerticalPanel();
		loginPanel = new VerticalPanel();
		vp.addStyleName("verticalPanel");
		// We can add style names to widgets
		sendButton.getElement().setId("sendButton");
//		createButton.getElement().setId("regButton");
		nameField.addStyleName("inputName");
		passField.addStyleName("inputPass");
		layout.addStyleName("horizontalPanel");
		// Add the nameField and sendButton to the RootPanel
		vp.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		vp.add(new HTML(
				"<div id=\"H2\" colspan=\"2\" align=\"center\" style=\"font-weight:bold;\">Please enter your name and password:</div>"));
		vp.add(nameField);
		vp.add(passField);
		layout.add(sendButton);
//		layout.add(createButton);
		vp.add(layout);
		vp.add(errorLabel);
		loginPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
//		loginPanel.add(new HTML(
//		"<div id=\"H1\" class=\"style_H1\" >Web Application Virtual Labaratory</div>"));
		loginPanel.add(new HTML(
		"<div id=\"H1\" class=\"style_H1\" >Веб приложение Виртуальная Лабораторная</div>"));
		loginPanel.add(vp);
		// Focus the cursor on the name field when the app loads
		nameField.setFocus(true);
		nameField.selectAll();

		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Login Procedure Call");
		dialogBox.setAnimationEnabled(true);
		// Create the popup dialog box 2
		final DialogBox dialogRBox = new DialogBox();
		dialogRBox.setText("Registration Procedure");
		dialogRBox.setAnimationEnabled(true);

		final Button closeVButton = new Button("Close");
		final Button closeRButton = new Button("Close");
//		final Button sendRegButton = new Button("Registered");
		// We can set the id of a widget by accessing its Element
		closeVButton.getElement().setId("closeVButton");
		closeRButton.getElement().setId("closeRButton");

//		sendRegButton.getElement().setId("sendRegButton");
		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();
		// create VPanel
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeVButton);
		dialogBox.setWidget(dialogVPanel);
		// create RPanel
		VerticalPanel dialogRPanel = new VerticalPanel();
		dialogRPanel.addStyleName("dialogVPanel");
		dialogRPanel.add(closeRButton);
//		dialogRPanel.add(sendRegButton);
		dialogRBox.setWidget(dialogRPanel);

		// Add a handler to close the DialogBox
		closeVButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
//				RootPanel.get("table").setVisible(true);
				dialogBox.hide();
				dialogRBox.hide();
				sendButton.setEnabled(true);
//				createButton.setEnabled(true);
				sendButton.setFocus(true);
			}
		});

		// Add a handler to close the RDialogBox
		closeRButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
//				RootPanel.get("table").setVisible(true);с
				dialogBox.hide();
				dialogRBox.hide();
				sendButton.setEnabled(true);
//				createButton.setEnabled(true);
				sendButton.setFocus(true);
			}
		});

//		createButton.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent event) {
////				RootPanel.get("table").setVisible(false);
//				dialogRBox.setText("Registered new user");
//				sendButton.setEnabled(false);
//				createButton.setEnabled(false);
//				serverResponseLabel.removeStyleName("serverResponseLabelError");
//				serverResponseLabel.setHTML("");
//				dialogRBox.center();
////				sendRegButton.setFocus(true);
//			}
//		});

		// Create a handler for the sendButton and nameField
		class LoginHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				sendNameToServer();
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendNameToServer();
				}
			}

			/**
			 * Send the name from the nameField to the server and wait for a
			 * response.
			 */
			private void sendNameToServer() {
				// First, we validate the input.
				errorLabel.setText("");
				final String textToServer = nameField.getText();
				MD5Calculator calculatorMD5 = new MD5Calculator();
				String passToServer = null;
				if (passField.getText() != null	&& !passField.getText().trim().equals("")) {
					passToServer = calculatorMD5.calculate(passField.getText());
					passField.setText("");
				}
				// String passToServer = passField.getText();
				// Then, we send the input to the server.
				// sendButton.setEnabled(false);
				// createButton.setEnabled(false);
				textToServerLabel.setText(textToServer);
				serverResponseLabel.setText("");
				AsyncCallback<LoginResponce> asyncCallback=new AsyncCallback<LoginResponce>() {
							public void onFailure(Throwable caught) {
								toCenter();
								// Show the RPC error message to the user
								dialogBox
										.setText("Ответ сервера - Ошибка");
								serverResponseLabel
										.addStyleName("serverResponseLabelError");

								serverResponseLabel.setHTML(caught.getMessage());
								dialogBox.center();
								closeVButton.setFocus(true);
							}

							public void onSuccess(LoginResponce result) {
								toCenter();
								dialogBox.setText("Ответ сервера");
								serverResponseLabel
										.removeStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(result.getResponse());
								dialogBox.center();
								closeVButton.setFocus(true);
								loginData.setUserName(textToServer);
								loginData.setAdmin(result.isAdmin());
								loginData.setTestPass(result.isTestPass());
								loginData.setSession(result.getSessionID());
								loginData.setSuccess(result.isSuccess());
							}
						};
				LogonManager.getInstance().login(passToServer, textToServer, asyncCallback);
			}
		}

//		class RegisterHandler implements ClickHandler, KeyUpHandler {
//			/**
//			 * Fired when the user clicks on the sendButton.
//			 */
//			public void onClick(ClickEvent event) {
//				createUserOnServer();
//			}
//
//			/**
//			 * Fired when the user types in the nameField.
//			 */
//			public void onKeyUp(KeyUpEvent event) {
//				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
//					createUserOnServer();
//				}
//			}
//
//			private void createUserOnServer() {
//
//			}
//		}

		// Add a handler to send the name to the server
		LoginHandler handler = new LoginHandler();
		sendButton.addClickHandler(handler);
		nameField.addKeyUpHandler(handler);
		passField.addKeyUpHandler(handler);

//		RegisterHandler rHandler = new RegisterHandler();
//		sendRegButton.addClickHandler(rHandler);
		this.add(loginPanel);
	}

	public LoginData getLoginData() {
		return loginData;
	}

	public void setLoginData(LoginData loginData) {
		this.loginData = loginData;
	}
	
	public void toCenter(){
		this.center();
	}

	public void logout(){
		
	}
}
