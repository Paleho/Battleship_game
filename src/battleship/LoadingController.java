package battleship;

import javafx.scene.control.TextField;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class LoadingController {
    public TextField scenario_id;

    public void handleLoadBtn(){
        try{
            int id = Integer.parseInt(scenario_id.getText());
            String path = "medialab/player_" + id + ".txt";
            File file = new File(path);
            BufferedReader br = new BufferedReader(new FileReader(file));

            String st;
            System.out.println("Player:");
            for(int i = 0; i < 5; i++){
                st = br.readLine();
                System.out.printf("Ship %d: type = %d, x = %d, y = %d, orientation = %d\n", i+1,
                        Character.getNumericValue(st.charAt(0)),
                        Character.getNumericValue(st.charAt(2)),
                        Character.getNumericValue(st.charAt(4)),
                        Character.getNumericValue(st.charAt(6)));
            }

            path = "medialab/enemy_" + id + ".txt";
            file = new File(path);
            br = new BufferedReader(new FileReader(file));

            System.out.println("Enemy:");
            for(int i = 0; i < 5; i++){
                st = br.readLine();
                System.out.printf("Ship %d: type = %d, x = %d, y = %d, orientation = %d\n", i+1,
                        Character.getNumericValue(st.charAt(0)),
                        Character.getNumericValue(st.charAt(2)),
                        Character.getNumericValue(st.charAt(4)),
                        Character.getNumericValue(st.charAt(6)));
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
