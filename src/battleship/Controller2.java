package battleship;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Controller2 {

    Map player_map;

    public GridPane pane;

    public TextField target_x;
    public TextField target_y;

    public void setMap(Map input_map){
        player_map = input_map;
    }

    public void handleShot(javafx.event.ActionEvent event){
        try{
            int x = Integer.parseInt(target_x.getText());
            int y = Integer.parseInt(target_y.getText());

            Node n = fetchNode(x, y, pane);
            if(n == null) System.out.println("null node");
            Rectangle target = (Rectangle) n;

            target.setFill(Color.BLACK);
        }catch (Exception e){
            System.out.println(e);
        }

//        for(Node node : pane.getChildren()) {
//            System.out.println(node);
//            System.out.println(GridPane.getRowIndex(node));
//        }
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
