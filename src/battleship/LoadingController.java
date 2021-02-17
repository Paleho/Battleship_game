package battleship;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class LoadingController {
    public TextField scenario_id;
    public Label scenario_not_found;
    public Label invalid_input;
    public Stage window;

    public void setWin(Stage window){
        this.window = window;
        scenario_not_found.setVisible(false);
        invalid_input.setVisible(false);
    }

    public void handleLoadBtn(){
        try{
            scenario_not_found.setVisible(false);
            invalid_input.setVisible(false);

            int id = Integer.parseInt(scenario_id.getText());
            String path = "medialab/player_" + id + ".txt";
            File file = new File(path);
            BufferedReader br = new BufferedReader(new FileReader(file));

            String st;
            int car_x = 0;
            int car_y = 0;
            Direction car_dir = Direction.Down;
            int bat_x = 0;
            int bat_y = 0;
            Direction bat_dir = Direction.Down;
            int cru_x = 0;
            int cru_y = 0;
            Direction cru_dir = Direction.Down;
            int sub_x = 0;
            int sub_y = 0;
            Direction sub_dir = Direction.Down;
            int des_x = 0;
            int des_y = 0;
            Direction des_dir = Direction.Down;

            System.out.println("Player:");
            for(int i = 0; i < 5; i++){
                st = br.readLine();

                int x = Character.getNumericValue(st.charAt(2)) + 1;
                int y = Character.getNumericValue(st.charAt(4)) + 1;
                Point.isPoint(x, y);
                Direction direction = (Character.getNumericValue(st.charAt(6)) == 1) ? Direction.Right : Direction.Down;
                switch (Character.getNumericValue(st.charAt(0))) {
                    case 1 -> {
                        car_x = x;
                        car_y = y;
                        car_dir = direction;
                    }
                    case 2 -> {
                        bat_x = x;
                        bat_y = y;
                        bat_dir = direction;
                    }
                    case 3 -> {
                        cru_x = x;
                        cru_y = y;
                        cru_dir = direction;
                    }
                    case 4 -> {
                        sub_x = x;
                        sub_y = y;
                        sub_dir = direction;
                    }
                    case 5 -> {
                        des_x = x;
                        des_y = y;
                        des_dir = direction;
                    }
                }

//                System.out.printf("Ship %d: type = %d, x = %d, y = %d, orientation = %d\n", i+1,
//                        Character.getNumericValue(st.charAt(0)),
//                        Character.getNumericValue(st.charAt(2)),
//                        Character.getNumericValue(st.charAt(4)),
//                        Character.getNumericValue(st.charAt(6)));
            }

            Map player_map = new Map(new Point(car_x, car_y), car_dir,
                    new Point(bat_x, bat_y), bat_dir,
                    new Point(cru_x, cru_y), cru_dir,
                    new Point(sub_x, sub_y), sub_dir,
                    new Point(des_x, des_y), des_dir);

            path = "medialab/enemy_" + id + ".txt";
            file = new File(path);
            br = new BufferedReader(new FileReader(file));

            System.out.println("Enemy:");
            for(int i = 0; i < 5; i++){
                st = br.readLine();

                int x = Character.getNumericValue(st.charAt(2)) + 1;
                int y = Character.getNumericValue(st.charAt(4)) + 1;
                Point.isPoint(x, y);
                Direction direction = (Character.getNumericValue(st.charAt(6)) == 1) ? Direction.Right : Direction.Down;
                switch (Character.getNumericValue(st.charAt(0))) {
                    case 1 -> {
                        car_x = x;
                        car_y = y;
                        car_dir = direction;
                    }
                    case 2 -> {
                        bat_x = x;
                        bat_y = y;
                        bat_dir = direction;
                    }
                    case 3 -> {
                        cru_x = x;
                        cru_y = y;
                        cru_dir = direction;
                    }
                    case 4 -> {
                        sub_x = x;
                        sub_y = y;
                        sub_dir = direction;
                    }
                    case 5 -> {
                        des_x = x;
                        des_y = y;
                        des_dir = direction;
                    }
                }

//                System.out.printf("Ship %d: type = %d, x = %d, y = %d, orientation = %d\n", i+1,
//                        Character.getNumericValue(st.charAt(0)),
//                        Character.getNumericValue(st.charAt(2)),
//                        Character.getNumericValue(st.charAt(4)),
//                        Character.getNumericValue(st.charAt(6)));
            }

            Map cpu_map = new Map(new Point(car_x, car_y), car_dir,
                    new Point(bat_x, bat_y), bat_dir,
                    new Point(cru_x, cru_y), cru_dir,
                    new Point(sub_x, sub_y), sub_dir,
                    new Point(des_x, des_y), des_dir);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("scene2_new.fxml"));
            Parent root2 = (Parent) loader.load();

            Controller2 contr2 = loader.getController();    //get controller2
            contr2.setMap(player_map, cpu_map);   //give input map to controller 2

            Scene scene2 = new Scene(root2,605, 580);

            window.setTitle("MediaLab Battleship");
            window.setScene(scene2);
            window.show();
        }catch (FileNotFoundException file_e){
            scenario_not_found.setVisible(true);
            System.out.println(file_e);
        }
        catch(ShipsOverlap | OversizeException so){
            invalid_input.setVisible(true);
            System.out.println(so);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
