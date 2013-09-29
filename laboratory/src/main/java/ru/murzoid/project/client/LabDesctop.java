package ru.murzoid.project.client;

import ru.murzoid.project.client.login.LoginData;

import com.extjs.gxt.desktop.client.Desktop;
import com.extjs.gxt.desktop.client.Shortcut;
import com.extjs.gxt.desktop.client.StartMenu;
import com.extjs.gxt.desktop.client.TaskBar;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.util.IconHelper;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class LabDesctop extends Desktop {

	private LoginData loginData;
	private StartMenu menu;
	private SelectionListener<MenuEvent> menuListener;
	private SelectionListener<ComponentEvent> shortcutListener;

	private void itemSelected(ComponentEvent ce) {
		Window w;
		if (ce instanceof MenuEvent) {
			MenuEvent me = (MenuEvent) ce;
			w = me.getItem().getData("window");
		} else {
			w = ce.getComponent().getData("window");
		}
		if (!getWindows().contains(w)) {
			addWindow(w);
		}
		if (w != null && !w.isVisible()) {
			w.show();
		} else {
			w.toFront();
		}
	}

	public LabDesctop(LoginData loginDataI) {
		super();
		this.loginData = loginDataI;
		menuListener = new SelectionListener<MenuEvent>() {
			@Override
			public void componentSelected(MenuEvent me) {
				itemSelected(me);
			}
		};

		shortcutListener = new SelectionListener<ComponentEvent>() {
			@Override
			public void componentSelected(ComponentEvent ce) {
				itemSelected(ce);
			}
		};

		TaskBar taskBar = getTaskBar();

		menu = taskBar.getStartMenu();
		menu.setHeading(loginData.getUserName());
		menu.setIconStyle("user");

		MenuItem tool = new MenuItem("Logout");
		tool.setIcon(IconHelper.createStyle("logout"));
		tool.addSelectionListener(new SelectionListener<MenuEvent>() {
			@Override
			public void componentSelected(MenuEvent ce) {
				LogonManager.getInstance().logout(loginData.getUserName(),new AsyncCallback<String>() {
					
					@Override
					public void onSuccess(String result) {
						loginData.setSuccess(false);
						logoutAction();
					}
					
					@Override
					public void onFailure(Throwable caught) {
					}
				});
			}
		});
		menu.addTool(tool);
//		TODO Change Password
//		MenuItem changePass = new MenuItem("Смена пароля");
//		changePass.setIcon(IconHelper.createStyle("changePass"));
//		changePass.addSelectionListener(new SelectionListener<MenuEvent>() {
//			@Override
//			public void componentSelected(MenuEvent ce) {
//				LogonManager.getInstance().logout(loginData.getUserName(),new AsyncCallback<String>() {
//					
//					@Override
//					public void onSuccess(String result) {
//						loginData.setSuccess(false);
//						logoutAction();
//					}
//					
//					@Override
//					public void onFailure(Throwable caught) {
//					}
//				});
//			}
//		});
//		menu.addTool(changePass);
	}

	public void logoutAction() {
		loginData.setCookie();
		loginData.setSession("");
		loginData.deleteCookie();
		shortcuts.clear();
		windows.clear();
		menu.removeAll();
	}

	public void isVisible(boolean flag) {
		taskBar.setVisible(flag);
		viewport.setVisible(flag);
		desktop.setVisible(flag);
		for (Shortcut shortcut : shortcuts) {
			shortcut.setVisible(flag);
		}
		for (Window window : windows) {
			window.setVisible(flag);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addShortcut(String text, String urlIcon, Window window, SelectionListener listener) {
		Shortcut s1 = new Shortcut();
		s1.setText(text);
		s1.setIcon(IconHelper.createStyle(urlIcon));
		s1.setData("window", window);
		s1.addSelectionListener(listener);
		addShortcut(s1);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addMenuItem(String text, String urlIcon, Window window, SelectionListener listener) {
		 MenuItem menuItem = new MenuItem(text);
		 menuItem.setData("window", window);
		 menuItem.setIcon(IconHelper.createStyle(urlIcon));
		 menuItem.addSelectionListener(listener);
		 menu.add(menuItem);
	}

	public void addShortcut(String text, String urlIcon, Window window) {
		Shortcut s1 = new Shortcut();
		s1.setText(text);
		s1.setIcon(IconHelper.createStyle(urlIcon, 60, 60));
		s1.setData("window", window);
		s1.addSelectionListener(shortcutListener);
		addShortcut(s1);
	}

	public void addMenuItem(String text, String urlIcon, Window window) {
		 MenuItem menuItem = new MenuItem(text);
		 menuItem.setData("window", window);
		 menuItem.setIcon(IconHelper.createStyle(urlIcon));
		 menuItem.addSelectionListener(menuListener);
		 menu.add(menuItem);
	}
}
