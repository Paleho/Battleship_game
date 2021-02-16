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
            Direction car_dir = (chk_car_vert.isSelected()) ? Direction.Down : Direction.Right;
            int bat_x = Integer.parseInt(txt_bat_x.getText());
            int bat_y = Integer.parseInt(txt_bat_y.getText());
            Point.isPoint(bat_x, bat_y);
            Direction bat_dir = (chk_bat_vert.isSelected()) ? Direction.Down : Direction.Right;
            int cru_x = Integer.parseInt(txt_cru_x.getText());
            int cru_y = Integer.parseInt(txt_cru_y.getText());
            Point.isPoint(cru_x, cru_y);
            Direction cru_dir = (chk_cru_vert.isSelected()) ? Direction.Down : Direction.Right;
            int sub_x = Integer.parseInt(txt_sub_x.getText());
            int sub_y = Integer.parseInt(txt_sub_y.getText());
            Point.isPoint(sub_x, sub_y);
            Direction sub_dir = (chk_sub_vert.isSelected()) ? Direction.Down : Direction.Right;
            int des_x = Integer.parseInt(txt_des_x.getText());
            int des_y = Integer.parseInt(txt_des_y.getText());
            Point.isPoint(des_x, des_y);
            Direction des_dir = (chk_des_vert.isSelected()) ? Direction.Down : Direction.Right;

            Map input_map = new Map(new Point(car_x, car_y), car_dir,
                                    new Point(bat_x, bat_y), bat_dir,
                                    new Point(cru_x, cru_y), cru_dir,
                                    new Point(sub_x, sub_y), sub_dir,
                                    new Point(des_x, des_y), des_dir);
            System.out.println("Ok!");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("scene2_new.fxml"));
            Parent root2 = (Parent) loader.load();

            Controller2 contr2 = loader.getController();    //get controller2
            contr2.setMap(input_map);   //give input map to controller 2

            Scene scene2 = new Scene(root2,605, 580);
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();    //get the stage from the event
            window.setTitle("MediaLab Battleship");
            window.setScene(scene2);
            window.show();

        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void handleStart(){
        System.out.println("You are already in start");
    }

    public void handleLoad(){
        System.out.println("Loading...");
    }

    public void handleExit(){
        System.out.println("Exiting...");
        System.exit(0);
    }

}
