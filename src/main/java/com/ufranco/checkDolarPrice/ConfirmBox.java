package com.ufranco.checkDolarPrice;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox {

  public static boolean response;

  public static boolean display(String title, String message) {
    Stage window = new Stage();

    window.initModality(Modality.APPLICATION_MODAL);
    window.setTitle(title);
    window.setMinWidth(300);
    window.setMinHeight(300);

    Label label = new Label(message);
    Button yesBtn = new Button("Yes");
    yesBtn.setOnAction(e -> {
      response = true;
      window.close();
    });
    Button noBtn = new Button("No");
    noBtn.setOnAction(e -> {
      response = false;
      window.close();
    });

    VBox layout = new VBox(20);
    layout.getChildren().addAll(label, yesBtn, noBtn);

    Scene scene = new Scene(layout);

    window.setScene(scene);

    window.showAndWait();

    return response;
  }
}
