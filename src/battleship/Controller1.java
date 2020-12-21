package battleship;


import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;

public class Controller1 {

    public Button readyButton;
    public TextField txt_car_x;
    public TextField txt_car_y;
    public CheckBox chk_car_vert;
    public TextField txt_bat_x;
    public TextField txt_bat_y;
    public CheckBox chk_bat_vert;
    public TextField txt_cru_x;
    public TextField txt_cru_y;
    public CheckBox chk_cru_vert;
    public TextField txt_sub_x;
    public TextField txt_sub_y;
    public CheckBox chk_sub_vert;
    public TextField txt_des_x;
    public TextField txt_des_y;
    public CheckBox chk_des_vert;

    public void handleReadyButton(javafx.event.ActionEvent event){
        try{
            int car_x = Integer.parseInt(txt_car_x.getText());
            int car_y = Integer.parseInt(txt_car_y.getText());
            Point.isPoint(car_x, car_y);
            int bat_x = Integer.parseInt(txt_bat_x.getText());
            int bat_y = Integer.parseInt(txt_bat_y.getText());
            Point.isPoint(bat_x, bat_y);
            int cru_x = Integer.parseInt(txt_cru_x.getText());
            int cru_y = Integer.parseInt(txt_cru_y.getText());
            Point.isPoint(cru_x, cru_y);
            int sub_x = Integer.parseInt(txt_sub_x.getText());
            int sub_y = Integer.parseInt(txt_sub_y.getText());
            Point.isPoint(sub_x, sub_y);
            int des_x = Integer.parseInt(txt_des_x.getText());
            int des_y = Integer.parseInt(txt_des_y.getText());
            Point.isPoint(des_x, des_y);
            System.out.println("Ok!");

            Parent root2 = FXMLLoader.load(getClass().getResource("scene2.fxml"));
            Scene scene2 = new Scene(root2,640, 450);
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();    //get the stage from the event
            window.setTitle("Battleship");
            window.setScene(scene2);
            window.show();

        }catch (Exception e){
            System.out.println(e);
        }
    }

}
