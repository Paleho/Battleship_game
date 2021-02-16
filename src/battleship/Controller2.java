package battleship;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Random;


public class Controller2 {

    Map player_map;
    Map cpu_map;
    Player player;
    RandomPlayer cpu_player;
    private Color[][] player_state;
    private Color[][] cpu_state;
    private boolean cpu_first;
    private int round = 1;
    private int cpu_active = 5;
    private int cpu_score = 0;
    private int cpu_acc = 0;
    private int cpu_shots = 0;
    private int cpu_hit_shots = 0;
    private int player_active = 5;
    private int player_score = 0;
    private int player_acc = 0;
    private int player_shots = 0;
    private int player_hit_shots = 0;


    public GridPane player_grid;
    public GridPane cpu_grid;
    public TextField target_x;
    public TextField target_y;
    public Label plays_first;
    public Label who_won;
    public Label wasting;
    public Label cpu_active_label;
    public Label cpu_score_label;
    public Label cpu_acc_label;
    public Label player_active_label;
    public Label player_score_label;
    public Label player_acc_label;
    public Label round_cnt_label;
    public Label max_rounds;



    public void setMap(Map input_map) {
        player_map = input_map;
        player_state = new Color[11][11];

        for(int i = 1; i <= 10; i++)
            for (int j = 0; j <= 10; j++) {
                player_state[i][j] = Color.GRAY;
            }

        for(Point p : player_map.carrier.hitbox){
            player_state[p.getX()][p.getY()] = Color.YELLOW;
        }
        for(Point p : player_map.battleship.hitbox){
            player_state[p.getX()][p.getY()] = Color.GREEN;
        }
        for(Point p : player_map.cruiser.hitbox){
            player_state[p.getX()][p.getY()] = Color.BLUE;
        }
        for(Point p : player_map.submarine.hitbox){
            player_state[p.getX()][p.getY()] = Color.CYAN;
        }
        for(Point p : player_map.destroyer.hitbox){
            player_state[p.getX()][p.getY()] = Color.VIOLET;
        }

        cpu_map = new Map();    //random cpu map
        cpu_player = new RandomPlayer("CPU", cpu_map);
        player = new Player("USER", player_map);
        Random rand = new Random();
        cpu_first = rand.nextBoolean();

        plays_first.setText( (cpu_first) ? "Cpu plays first" : "You play first");
        who_won.setVisible(false);
        wasting.setVisible(false);
        max_rounds.setVisible(false);
        cpu_active_label.setText(Integer.toString(cpu_active));
        cpu_score_label.setText(Integer.toString(cpu_score));
        cpu_acc_label.setText(cpu_acc + "%");
        player_active_label.setText(Integer.toString(player_active));
        player_score_label.setText(Integer.toString(player_score));
        player_acc_label.setText(player_acc + "%");

        round_cnt_label.setText("Round " + round);

        cpu_state = new Color[11][11];

        for(int i = 1; i <= 10; i++)
            for (int j = 0; j <= 10; j++) {
                cpu_state[i][j] = Color.GRAY;
            }

        updateColors();
    }

    void updateColors(){
        for(Node node : player_grid.getChildren()){
            if(node != null){
                int row_index = (GridPane.getRowIndex(node) != null) ? GridPane.getRowIndex(node) : 0;
                int col_index = (GridPane.getColumnIndex(node) != null) ? GridPane.getColumnIndex(node) : 0;

                if(row_index >= 1 && row_index <= 10 && col_index >= 1 && col_index <= 10)
                    ((Rectangle) node).setFill(player_state[row_index][col_index]);
            }
        }
        for(Node node : cpu_grid.getChildren()){
            if(node != null){
                int row_index = (GridPane.getRowIndex(node) != null) ? GridPane.getRowIndex(node) : 0;
                int col_index = (GridPane.getColumnIndex(node) != null) ? GridPane.getColumnIndex(node) : 0;

                if(row_index >= 1 && row_index <= 10 && col_index >= 1 && col_index <= 10)
                    ((Rectangle) node).setFill(cpu_state[row_index][col_index]);
            }
        }
    }

    private void nextRound(int x, int y){
        wasting.setVisible(false);
        round++;
        int cpu_score_inc = 0;
        if(cpu_first) {
            cpu_score_inc = cpu_player.random_play(player_map);
        }

        int score = player.play(x, y, cpu_map);
        player_shots++;
        if(score > 0) {
            cpu_state[x][y] = Color.RED;
            player_score += score;
            player_hit_shots++;
        }
        else if(score == -3){
            cpu_state[x][y] = Color.RED;
            who_won.setText("You Won!!!");
            who_won.setVisible(true);
        }
        else if (score == -2){
            wasting.setVisible(true);
        }
        else
            cpu_state[x][y] = Color.BLACK;

        if(!cpu_first)
            cpu_score_inc = cpu_player.random_play(player_map);

        cpu_shots++;

        if(cpu_score_inc == -3){
            who_won.setText("You Lost...");
            who_won.setVisible(true);
        }
        else if(cpu_score_inc > 0) {
            cpu_score += cpu_score_inc;
            cpu_hit_shots++;
        }

        player_acc = 100 * player_hit_shots / player_shots;
        player_acc_label.setText(player_acc + "%");

        cpu_acc = 100 * cpu_hit_shots / cpu_shots;
        cpu_acc_label.setText(cpu_acc + "%");

        cpu_score_label.setText(Integer.toString(cpu_score));
        player_score_label.setText(Integer.toString(player_score));

        cpu_active_label.setText(Integer.toString(cpu_map.getActiveShips()));
        player_active_label.setText(Integer.toString(player_map.getActiveShips()));

        if(round  >= 40){
            max_rounds.setVisible(true);
            if(player_score > cpu_score){
                who_won.setText("You Won!!!");
                who_won.setVisible(true);
            }
            else if(player_score < cpu_score){
                who_won.setText("You Lost...");
                who_won.setVisible(true);
            }
            else{
                who_won.setText("Draw");
                who_won.setVisible(true);
            }
        }

        round_cnt_label.setText("Round " + round);

        for (int i = 1; i <= 10 ; i++)
            for (int j = 0; j <= 10 ; j++) {
                if(cpu_player.shooting_map[i][j] == 0)
                    player_state[i][j] = Color.BLACK;
                else if(cpu_player.shooting_map[i][j] == 1)
                    player_state[i][j] = Color.RED;
            }
        updateColors();
    }

    public void handleShot(javafx.event.ActionEvent event){
        try{
            int x = Integer.parseInt(target_x.getText());
            int y = Integer.parseInt(target_y.getText());
            nextRound(x, y);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    private Node fetchNode (int x, int y, GridPane grid){
        Node res = null;

        for(Node node : grid.getChildren()){
            if(node != null){
                int row_index = (GridPane.getRowIndex(node) != null) ? GridPane.getRowIndex(node) : 0;
                int col_index = (GridPane.getColumnIndex(node) != null) ? GridPane.getColumnIndex(node) : 0;
                if(x == row_index && y == col_index){
                    res = node;
                    break;
                }
            }
        }
        return res;
    }

    public void handleStart(){
        System.out.println("Starting");

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scene1.fxml"));
            Parent root1 = (Parent) loader.load();

            Controller1 contr1 = loader.getController();    //get controller1

            Scene scene1 = new Scene(root1, 640, 450);
            Stage window = (Stage) player_grid.getScene().getWindow();    //get the stage from the event

            window.setTitle("MediaLab Battleship");
            window.setScene(scene1);
            window.show();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void handleLoad(){
        System.out.println("Loading...");
    }

    public void handleExit(){
        System.out.println("Exiting...");
        System.exit(0);
    }

    public void handleEnemyShips(){
        System.out.println("in Enemy Ships");
        Stage popup_window = new Stage();
        VBox popup_Vbox = new VBox(20);

        popup_Vbox.getChildren().add(new Text("Enemy Carrier: " + ShipStateToString(cpu_map.carrier.state)));
        popup_Vbox.getChildren().add(new Text("Enemy Battleship: " + ShipStateToString(cpu_map.battleship.state)));
        popup_Vbox.getChildren().add(new Text("Enemy Cruiser: " + ShipStateToString(cpu_map.cruiser.state)));
        popup_Vbox.getChildren().add(new Text("Enemy Submarine: " + ShipStateToString(cpu_map.submarine.state)));
        popup_Vbox.getChildren().add(new Text("Enemy Destroyer: " + ShipStateToString(cpu_map.destroyer.state)));


        Scene popup_Scene = new Scene(popup_Vbox, 300, 200);
        popup_window.setTitle("Enemy Ships");
        popup_window.setScene(popup_Scene);
        popup_window.show();
    }

    private String ShipStateToString(Ship.ShipState state){
        if(state == Ship.ShipState.Intact) return "Intact";
        else if(state == Ship.ShipState.Damaged) return "Damaged";
        else return "Destroyed";
    }
}
