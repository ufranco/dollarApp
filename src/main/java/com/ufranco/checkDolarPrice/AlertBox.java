package com.ufranco.checkDolarPrice;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {


  public static void display(String title, String message) {
    Stage window = new Stage();

    window.initModality(Modality.APPLICATION_MODAL);
    window.setTitle(title);
    window.setMinWidth(300);
    window.setMinHeight(300);

    Label label = new Label(message);
    Button closeBtn = new Button("Ok");
    closeBtn.setOnAction(e -> window.close());

    VBox layout = new VBox(20);
    layout.getChildren().addAll(label, closeBtn);

    Scene scene = new Scene(layout);

    window.setScene(scene);

    window.showAndWait();

  }
}
