package tn.esprit;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn.esprit.views/user.fxml"));
        Scene scene = new Scene(loader.load(), 600, 400);
        primaryStage.setTitle("Gestion Utilisateurs");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) throws Exception {
        String headless = System.getenv("HEADLESS");
        if ("1".equals(headless)) {
            int port = HeadlessServer.envInt("PORT", 8080);
            new HeadlessServer().start(port);
        } else {
            Application.launch(args);
        }
    }
}

