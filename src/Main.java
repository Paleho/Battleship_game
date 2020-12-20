import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main{
    public static void main(String[] args) {
        Map ships_map = new Map();
        Map enemy_map = new Map();

        System.out.println("Random map:");
        enemy_map.print_map();

        RandomPlayer cpu_player = new RandomPlayer("CPU", ships_map);

        while(enemy_map.getHitpoints() > 0)
            cpu_player.random_play(enemy_map);

//        System.out.println("map_hitpoints = " + my_map.getHitpoints());
//        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
//        while(true){
//            System.out.println("Pick a shot (x, y): ");
//            try {
//                int shot_x = Integer.parseInt(stdin.readLine());
//                int shot_y = Integer.parseInt(stdin.readLine());
//                if(shot_x >= 1 && shot_x <= 10 && shot_y >= 1 && shot_y <= 10){
//                    int score = my_map.shoot(shot_x, shot_y);
//                    System.out.println("map_hitpoints = " + my_map.getHitpoints());
//                    if(score == -2) {
//                        System.out.println("Game over!");
//                        break;
//                    }
//                    else
//                        System.out.println("Score += " + score);
//                }
//                else{
//                    System.out.println("Invalid Point! : 1 <= x <= 10, 1 <= y <= 10");
//                    System.out.println("Try again..");
//                }
//            } catch (IOException ioe) {
//                System.out.println(ioe);
//            }
//        }
    }
}
