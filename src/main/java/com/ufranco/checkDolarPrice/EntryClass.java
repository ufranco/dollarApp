package com.ufranco.checkDolarPrice;

import com.ufranco.checkDolarPrice.models.Currency;
import javafx.application.Application;
import javafx.stage.Stage;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.String.format;

public class EntryClass extends Application {

    /*
    Stage window;
    Scene scene1, scene2, scene3;
    GridPane grid;
    Button button1, button2, button3;
    */
    Label labelMabel = new Label();
    DesktopNotification notification = new DesktopNotification(labelMabel);

    Double lastBuy;
    Double lastSell;

    @Override
    public void start(Stage primaryStage) {

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    String title;
                    String message;

                    StringBuffer content = getNewData();
                    Currency blue = formatResponse(content);

                    if(lastBuy == null || lastSell == null) {
                        title = "App que te avisa cuando el dolar se va a la mierda";
                        message =
                          "Compra: " + "$" + format("%.2f", blue.getBuyValue()) + "\n" +
                          "Venta: " + "$" + format("%.2f", blue.getSellValue());

                        notification.ShowSystemNotification(title, message);

                    } else if(blue.getBuyValue().intValue() > lastBuy.intValue()) {
                        title = "App que te avisa cuando el dolar se va a la mierda";
                        message = "El dólar se fue a la pija de vuelta.\n" +
                          "Compra: $" + format("%.2f", lastBuy) + " -> " + "$" + format("%.2f", blue.getBuyValue()) + "\n" +
                          "Venta: $" + format("%.2f", lastSell) + " -> " + "$" + format("%.2f", blue.getSellValue());

                        notification.ShowSystemNotification(title, message);
                    }

                    lastSell = blue.getSellValue();
                    lastBuy = blue.getSellValue();

                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(-1);
                }
            }
        },0,300000);



    /*
        window = primaryStage;
        setWindowProperties();
        setGrid();

        Scene scene = new Scene(grid);
        window.setScene(scene);

        window.show();
    */

    }

    private StringBuffer getNewData() throws IOException {
        BufferedReader in;
        URL url = new URL("https://www.dolarsi.com/api/api.php?type=valoresprincipales");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        in = new BufferedReader(
          new InputStreamReader(con.getInputStream())
        );

        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        con.disconnect();

        return content;
    }

    private Currency formatResponse(StringBuffer content) {

        Currency blue = new Currency();

        String[] contentArray = Arrays.stream(content.toString()
          .split("},"))
          .skip(1)
          .findFirst()
          .orElse("")
          .replace("{\"casa\":{", "")
          .replace("{","")
          .replace("}","")
          .replace("\"","")
          .replace(":", ",")
          .split(",");

        blue.setName(contentArray[9]);
        blue.setBuyValue(
          Double.valueOf(
            contentArray[1] + "." + contentArray[2]
          )
        );

        blue.setSellValue(
          Double.valueOf(
            contentArray[4] + "." + contentArray[5]
          )
        );

        return blue;
    }



   /* private void setWindowProperties()  {
        window.setTitle("Saluden que se va");
        window.getIcons().add(
          new Image("https://upload.wikimedia.org/wikipedia/commons/1/19/Foto_de_campa%C3%B1a_Menem_1989.png")
        );
        window.setMinHeight(400);
        window.setMinWidth(400);
        window.setOnCloseRequest(e  -> {
            e.consume();
            closeProgram();
        });

    }

    private void setGrid() {
        grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(8);
        grid.setHgap(10);

        HBox topMenu = createTopMenu();
        GridPane.setConstraints(topMenu, 1,0);

        VBox leftMenu = createLeftMenu();
        GridPane.setConstraints(leftMenu, 0, 1);

        grid.getChildren().addAll(topMenu,leftMenu);
    }

    private HBox createTopMenu() {
        HBox topMenu = new HBox();
        Button fileButton = new Button("File");
        Button editButton = new Button("Edit");
        Button viewButton = new Button("View");
        Button helpButton = new Button("Help");
        topMenu.getChildren().addAll(
          fileButton,
          editButton,
          viewButton,
          helpButton
        );

        return topMenu;
    }

    private VBox createLeftMenu() {

        VBox leftMenu = new VBox();
        Button btnA = new Button("1");
        Button btnB = new Button("2");
        Button btnC = new Button("3");
        Button btnD = new Button("4");
        Button btnE = new Button("5");

        leftMenu.getChildren().addAll(
          btnA,
          btnB,
          btnC,
          btnD,
          btnE
        );

        return leftMenu;
    }

    private void closeProgram() {
        if(ConfirmBox.display("Cerrar programa", "De verdad queres cerrar el programa?")){
            window.close();
        }
    }*/

    public static void main(String[] args) {
        launch();
    }
}