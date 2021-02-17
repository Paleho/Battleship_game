package battleship;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("scene1.fxml"));
        Parent root = (Parent) loader.load();

        Controller1 contr1 = loader.getController();    //get controller1
        contr1.clearErrors();   //clear error messages

        primaryStage.setTitle("MediaLab Battleship");
        primaryStage.setScene(new Scene(root, 640, 450));
        primaryStage.show();

        System.out.println("after stage.show");
    }

    public static void main(String[] args) {
        launch(args);
    }

}
