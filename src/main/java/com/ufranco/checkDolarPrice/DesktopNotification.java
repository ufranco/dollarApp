package com.ufranco.checkDolarPrice;

import javafx.application.Platform;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.desktop.AppForegroundEvent;
import java.awt.desktop.AppForegroundListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DesktopNotification {

  private final Label target;
  private ThreadLocal<TrayIcon> trayIcon;

  DesktopNotification(Label label) {
    target = label;
    Desktop.getDesktop().addAppEventListener(new AppFocusListener());
  }

  public void NotificationClicked(ActionEvent actionEvent) {
    System.out.println(actionEvent.getActionCommand());
    System.out.println("Notification clicked");
    Platform.runLater(() -> target.setText("Notification clicked!"));
    SystemTray tray = SystemTray.getSystemTray();
    tray.remove(trayIcon.get());
  }

  public void ShowSystemNotification(String title, String message) {
    javax.swing.SwingUtilities.invokeLater(() ->
    {
      try {
        if (trayIcon == null || trayIcon.get() == null) {
          init();
        }
        SystemTray tray = SystemTray.getSystemTray();
        if(tray.getTrayIcons().length == 0)
          tray.add(trayIcon.get());
      } catch (Exception e) {
        e.printStackTrace();
      }

      trayIcon.get().displayMessage(title, message, TrayIcon.MessageType.NONE);

    });
  }

  private void init() throws IOException {
    if (!SystemTray.isSupported())
      return;

    // By default, we'll just use the standard app icon.
    //Image image = Taskbar.getTaskbar().getIconImage();

    //Alternative (if the icon is on the classpath):
    BufferedImage image = ImageIO.read(new File("./img/tray.png"));
    int trayIconWidth = new TrayIcon(image).getSize().width;

    trayIcon = new ThreadLocal<>();
    trayIcon.set(
      new TrayIcon(
        image.getScaledInstance(trayIconWidth, -1, Image.SCALE_SMOOTH),
        "God Emperor Menem"
      )
    );



    //Let the system resize the image if needed
    //trayIcon.get().setImageAutoSize(true);
    //Set tooltip text for the tray icon
    trayIcon.get().setToolTip("God Emperor Menem");

    trayIcon.get().setActionCommand("notification-clicked");
    trayIcon.get().addActionListener(this::NotificationClicked);

    PopupMenu popupMenu = new PopupMenu();
    MenuItem showNotification = new MenuItem("Show notification");
    popupMenu.add(showNotification);
    showNotification.addActionListener(this::NotificationClicked);

    trayIcon.get().setPopupMenu(popupMenu);
  }


  public void close() {
    System.out.println("closing");
    javax.swing.SwingUtilities.invokeLater(() ->
    {
      if (trayIcon.get() != null) {
        SystemTray tray = SystemTray.getSystemTray();
        tray.remove(trayIcon.get());
        System.out.println(("Removed tray!"));
      }
    });
  }

  private class AppFocusListener implements AppForegroundListener {

    @Override
    public void appRaisedToForeground(AppForegroundEvent e) {
      javax.swing.SwingUtilities.invokeLater(() ->
      {
        if (SystemTray.getSystemTray().getTrayIcons().length > 0)
          SystemTray.getSystemTray().remove(trayIcon.get());
      });
    }

    @Override
    public void appMovedToBackground(AppForegroundEvent e) {
      System.out.println("App moved to background");
    }
  }
}