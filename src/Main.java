import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Main{
    public static void main(String[] args) {
        Map cpu_ships = new Map();

        System.out.println("Random cpu map:");
        cpu_ships.print_map();

        System.out.println("Place your ships:");
        System.out.println("<enter x, y --> point and 1 --> vertical or 0 --> horizontal>");

        Point[] points = new Point[5];
        Direction[] dirs = new Direction[5];

        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        for (int i = 0; i < 5; i++) {
            try {
                int x = Integer.parseInt(stdin.readLine());
                int y = Integer.parseInt(stdin.readLine());

                if (!(x >= 1 && x <= 10 && y >= 1 && y <= 10)) {
                    System.out.println("try a valid point!");
                    i--;    //repeat the iteration
                    continue;
                }
                int d = Integer.parseInt(stdin.readLine());
                int distance = switch (i) {
                    case 0 -> 4;
                    case 1 -> 3;
                    case 2 -> 2;
                    case 3 -> 2;
                    case 4 -> 1;
                    default -> 0;
                };

                if((d > 0 && x + distance > 10) || (d <= 0 && y + distance > 10)){
                    System.out.println("ship doesn't fit!");
                    i--;    //repeat the iteration
                    continue;
                }

                points[i] = new Point(x, y);
                dirs[i] = (d > 0)? Direction.Down : Direction.Right;
            }catch (IOException ioe) {
                System.out.println(ioe);
            }
        }

        Map player_ships = new Map(points[0], dirs[0],
                                   points[1], dirs[1],
                                   points[2], dirs[2],
                                   points[3], dirs[3],
                                   points[4], dirs[4]);

        RandomPlayer cpu_player = new RandomPlayer("CPU", cpu_ships);
        Player player = new Player("USER", player_ships);

        Random rand = new Random();
        boolean cpu_first = rand.nextBoolean();
        if(cpu_first)
            System.out.println("CPU plays first");
        else
            System.out.println("User plays first");
        while(player_ships.getHitpoints() > 0 && cpu_ships.getHitpoints() > 0){
            if(cpu_first)
                cpu_player.random_play(player_ships);

            System.out.println("Pick a shot (x, y): ");
            try {
                int shot_x = Integer.parseInt(stdin.readLine());
                int shot_y = Integer.parseInt(stdin.readLine());
                if(shot_x >= 1 && shot_x <= 10 && shot_y >= 1 && shot_y <= 10){
                    int score = player.play(shot_x, shot_y, cpu_ships);

                    if(score == -2) {
                        System.out.println("Game over!");
                        break;
                    }
                    else
                        System.out.println("Score += " + score);
                }
                else{
                    System.out.println("Invalid Point! : 1 <= x <= 10, 1 <= y <= 10");
                    System.out.println("Try again..");
                }
            } catch (IOException ioe) {
                System.out.println(ioe);
            }

            if(!cpu_first)
                cpu_player.random_play(player_ships);
        }

    }
}
