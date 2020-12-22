package battleship;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.Random;


public class Controller2 {

    Map player_map;
    Map cpu_map;
    Player player;
    RandomPlayer cpu_player;
    private Color[][] player_state;
    private Color[][] cpu_state;
    private boolean cpu_first;
    private int round = 0;

    public GridPane player_grid;
    public GridPane cpu_grid;
    public TextField target_x;
    public TextField target_y;
    public Label plays_first;
    public Label who_won;
    public Label wasting;


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
        int cpu_score = 0;
        if(cpu_first)
           cpu_score = cpu_player.random_play(player_map);

        int score = player.play(x, y, cpu_map);
        if(score > 0)
            cpu_state[x][y] = Color.RED;
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
            cpu_score = cpu_player.random_play(player_map);

        if(cpu_score == -3){
            who_won.setText("You Lost...");
            who_won.setVisible(true);
        }

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
}
