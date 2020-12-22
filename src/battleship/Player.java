package battleship;

class Player {
    String name;
    Map ships_map;
    protected int[][] shooting_map = new int[11][11];
//    -1  --> no info for that tile
//    0   --> shot that tile but nothing was there
//    1   --> successful shot

    public Player(String name, Map ships){
        this.name = name;
        ships_map = ships;
        for (int i = 0; i <= 10; i++) {
            shooting_map[i][0] = 0;
            shooting_map[0][i] = 0;
        }
        for (int i = 1; i <= 10; i++)
            for(int j = 1; j <= 10; j++)
                shooting_map[i][j] = -1;
    }

    //on successful hit returns score
    //on unsuccessful attempt returns 0
    //on invalid attempt returns -1
    //on wasteful attempt returns -2
    //on player won returns -3
    public int play(int x, int y, Map enemy_map){
        if(!(x >= 1 && x <= 10 && y >= 1 && y <= 10)){
            System.out.println("Invalid player move!");
            return -1;
        }

        if(shooting_map[x][y] != -1){
            System.out.println("Don't waste shots on the same tiles");
            return -2;
        }

        int result = enemy_map.shoot(x, y);
        if(result == 0){    // nothing hit
            shooting_map[x][y] = 0;
            return result;
        }
        else if(result > 0 ){
            shooting_map[x][y] = 1;
            return result;
        }
        else if(result == -2){
            System.out.println("player won!");
            return -3;
        }
        else{   //error
            System.out.println("play error");
            return -1;
        }
    }

    public int get_shooting_map(int x, int y){
        if(!(x >= 1 && x <= 10 && y >= 1 && y <= 10))
            return -2;

        return shooting_map[x][y];
    }


}

class RandomPlayer extends Player{
    Point[] memory; //will hold up to 5 points that represent a ship that the player found
    boolean found_target;
    int point_index;    // index in the memory array
    int dir;
    boolean changed_dir;
//    for finding ship:
//    0 --> go up
//    1 --> go down
//    2 --> go right
//    3 --> go left
    Boolean vertical;   //need to store 3 states
//  null --> no idea if the ship found is vertical or horizontal
//  false --> the ship found is horizontal
//  true --> the ship found is vertical

    public RandomPlayer(String name, Map ships){
        super(name, ships);
        for (int i = 0; i <= 10; i++) {
            shooting_map[i][0] = 0;
            shooting_map[0][i] = 0;
        }
        for (int i = 1; i <= 10; i++)
            for(int j = 1; j <= 10; j++)
                shooting_map[i][j] = -1;
        found_target = false;
        memory = new Point[5];
        point_index = 0;
        dir = 0;
        changed_dir = false;
        vertical = null;
    }

    public int random_play(Map enemy_map){
        int x,y;
        //define x, y
        if(!found_target){
            while (true) {  // this may loop a lot after many shots (table almost filled)
                Point random_point = new Point();
                if (get_shooting_map(random_point.getX(), random_point.getY()) == -1) {
                    x = random_point.getX();
                    y = random_point.getY();
                    break;
                }
            }
        }
        else{
            //what happens if found a ship with a previous shot?
            while(true){
                if(dir == 0){   //UP
                    x = memory[point_index - 1].getX() - 1;   //point_index - 1 --> recent point added
                    y = memory[point_index - 1].getY();
                    if (get_shooting_map(x, y) != -1) {//you plan to shoot somewhere that is not needed
                        dir++;  //move to next direction
                        changed_dir = true;
                    }
                    else {
                        vertical = true;
                        break;
                    }
                }
                else if(dir == 1){   //DOWN
                    int pivot = changed_dir? 0 : point_index - 1;
                    changed_dir = false;
                    x = memory[pivot].getX() + 1;   //point_index - 1 --> recent point added
                    y = memory[pivot].getY();
                    if(get_shooting_map(x, y) != -1 && vertical){
                        dir += 3; //go for reset
                    }
                    else if (get_shooting_map(x, y) != -1) {//you plan to shoot somewhere that is not needed
                        dir++;  //move to next direction
                        changed_dir = true;
                    }
                    else{
                        vertical = true;
                        break;
                    }
                }
                else if(dir == 2){  //RIGHT
                    int pivot = changed_dir? 0 : point_index - 1;
                    changed_dir = false;
                    x = memory[pivot].getX();   //point_index - 1 --> recent point added
                    y = memory[pivot].getY() + 1;
                    if (get_shooting_map(x, y) != -1) {//you plan to shoot somewhere that is not needed
                        dir++;  //move to next direction
                        changed_dir = true;
                    }
                    else {
                        break;
                    }
                }
                else if(dir == 3) {  //LEFT
                    int pivot = changed_dir? 0 : point_index - 1;
                    changed_dir = false;
                    x = memory[pivot].getX();   //point_index - 1 --> recent point added
                    y = memory[pivot].getY() - 1;
                    if (get_shooting_map(x, y) != -1) {//you plan to shoot somewhere that is not needed
                        dir++;  //move to next direction    [CHECK]
                        changed_dir = true;
                    }
                    else
                        break;
                }
                else{   // if dir > 3
                    //reset
                    found_target = false;
                    memory = new Point[5];
                    point_index = 0;
                    dir = 0;
                    changed_dir = false;
                    return random_play(enemy_map);
                }
            }
        }

        if(get_shooting_map(x, y) != -1)
            return random_play(enemy_map);

        System.out.printf("playing: (%d,%d)\n", x, y);
        int score = play(x, y, enemy_map);
        if(score == 0){
            shooting_map[x][y] = 0;
            if(found_target) {
                dir++;
                changed_dir = true;
                if(dir > 3){
                    //reset
                    found_target = false;
                    memory = new Point[5];
                    point_index = 0;
                    dir = 0;
                    changed_dir = false;
                }
            }
        }
        else if(score > 0){
            shooting_map[x][y] = 1;
            found_target = true;
            memory[point_index++] = new Point(x, y);    //add point to memory array and increase index
        }
        else if(score == -3){
            shooting_map[x][y] = 1;
            System.out.println("Random player won!");
        }
        else{
            System.out.println("Random play shouldn't reach that point");
        }
        return score;
    }
}
