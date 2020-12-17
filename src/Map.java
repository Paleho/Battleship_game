import java.util.Random;

class Map {
    public Carrier carrier;
    public Battleship battleship;
    public Cruiser cruiser;
    public Submarine submarine;
    public Destroyer destroyer;
    private int map_hitpoints;

    private boolean[][] occupied = new boolean[12][12];

    public Map(Point car_point, Direction car_direction,
               Point bat_point, Direction bat_direction,
               Point cru_point, Direction cru_direction,
               Point sub_point, Direction sub_direction,
               Point des_point, Direction des_direction){
        //Initialize occupied table
        for (int i = 0; i <= 11; i++) {
            for(int j = 0; j <= 11; j++)
                occupied[i][j] = false;
        }
        for (int i = 0; i <= 11; i++) {
            occupied[0][i] = true;
            occupied[11][i] = true;
            occupied[i][0] = true;
            occupied[i][11] = true;
        }

        carrier = new Carrier(car_point, new Point(car_point, car_direction, 4));
        for (Point p : carrier.hitbox){
            occupied[p.getX()][p.getY()] = true;
            occupied[p.getX()+1][p.getY()] = true;
            occupied[p.getX()][p.getY()+1] = true;
            occupied[p.getX()-1][p.getY()] = true;
            occupied[p.getX()][p.getY()-1] = true;
        }

        battleship = new Battleship(bat_point, new Point(bat_point, bat_direction, 3));
        for (Point p : battleship.hitbox){
            if(occupied[p.getX()][p.getY()])
                System.out.println("Map error: Ships overlap (" + p.getX() + "," + p.getY() + ") (battleship)");
        }
        for (Point p : battleship.hitbox){
            occupied[p.getX()][p.getY()] = true;
            occupied[p.getX()+1][p.getY()] = true;
            occupied[p.getX()][p.getY()+1] = true;
            occupied[p.getX()-1][p.getY()] = true;
            occupied[p.getX()][p.getY()-1] = true;
        }
        cruiser = new Cruiser(cru_point, new Point(cru_point, cru_direction, 2));
        for (Point p : cruiser.hitbox){
            if(occupied[p.getX()][p.getY()])
                System.out.println("Map error: Ships overlap (" + p.getX() + "," + p.getY() + ") (cruiser)");
        }
        for (Point p : cruiser.hitbox){
            occupied[p.getX()][p.getY()] = true;
            occupied[p.getX()+1][p.getY()] = true;
            occupied[p.getX()][p.getY()+1] = true;
            occupied[p.getX()-1][p.getY()] = true;
            occupied[p.getX()][p.getY()-1] = true;
        }
        submarine = new Submarine(sub_point, new Point(sub_point, sub_direction, 2));
        for (Point p : submarine.hitbox){
            if(occupied[p.getX()][p.getY()])
                System.out.println("Map error: Ships overlap (" + p.getX() + "," + p.getY() + ") (submarine)");
        }
        for (Point p : submarine.hitbox){
            occupied[p.getX()][p.getY()] = true;
            occupied[p.getX()+1][p.getY()] = true;
            occupied[p.getX()][p.getY()+1] = true;
            occupied[p.getX()-1][p.getY()] = true;
            occupied[p.getX()][p.getY()-1] = true;
        }
        destroyer = new Destroyer(des_point, new Point(des_point, des_direction, 1));
        for (Point p : destroyer.hitbox){
            if(occupied[p.getX()][p.getY()])
                System.out.println("Map error: Ships overlap (" + p.getX() + "," + p.getY() + ") (destroyer)");
        }
        for (Point p : destroyer.hitbox){
            occupied[p.getX()][p.getY()] = true;
            occupied[p.getX()+1][p.getY()] = true;
            occupied[p.getX()][p.getY()+1] = true;
            occupied[p.getX()-1][p.getY()] = true;
            occupied[p.getX()][p.getY()-1] = true;
        }

        map_hitpoints = 17;
    }

    public void print_map(){
        carrier.print_Ship();
        battleship.print_Ship();
        cruiser.print_Ship();
        submarine.print_Ship();
        destroyer.print_Ship();
    }

    public int shoot(int x, int y){
        if(!(x >= 1 && x <= 10 && y >= 1 && y <= 10)){
            //out of bounds shot
            System.out.println("Map error: out of bounds shot");
            return -1;
        }

        int ret = 0;
        // one time loop
        outer:
        for (int k = 0; k < 1; k++) {
            for(Point i : carrier.hitbox){
                if(i != null && i.getX() == x && i.getY() == y){
                    ret = carrier.shot(x, y);
                    map_hitpoints--;
                    break outer;
                }
            }
            for(Point i : battleship.hitbox){
                if(i != null && i.getX() == x && i.getY() == y){
                    ret = battleship.shot(x, y);
                    map_hitpoints--;
                    break outer;
                }
            }
            for(Point i : cruiser.hitbox){
                if(i != null && i.getX() == x && i.getY() == y){
                    ret = cruiser.shot(x, y);
                    map_hitpoints--;
                    break outer;
                }
            }
            for(Point i : submarine.hitbox){
                if(i != null && i.getX() == x && i.getY() == y){
                    ret = submarine.shot(x, y);
                    map_hitpoints--;
                    break outer;
                }
            }
            for(Point i : destroyer.hitbox){
                if(i != null && i.getX() == x && i.getY() == y){
                    ret = destroyer.shot(x, y);
                    map_hitpoints--;
                    break outer;
                }
            }
        }

        if(map_hitpoints <= 0){
            System.out.println("All ships down");
            return -2;
        }

        return  ret;
    }

    public int getHitpoints(){ return map_hitpoints;}

    //random map constructor
    public Map(){
        //Initialize occupied table
        for (int i = 0; i <= 11; i++) {
            for(int j = 0; j <= 11; j++)
                occupied[i][j] = false;
        }
        for (int i = 0; i <= 11; i++) {
            occupied[0][i] = true;
            occupied[11][i] = true;
            occupied[i][0] = true;
            occupied[i][11] = true;
        }

        while(true){
            Point car_point = new Point();  //create a random point
            Random rand = new Random();

            boolean vert = rand.nextBoolean();
            Point other_point;
            if (vert && car_point.getX() + 4 <= 10 && car_point.getX() + 4 >= 1) {
                other_point = new Point(car_point.getX() + 4, car_point.getY());
                carrier = new Carrier(car_point, other_point);
                break;
            }
            else if (!vert && car_point.getY() + 4 <= 10 && car_point.getY() + 4 >= 1) {
                other_point = new Point(car_point.getX(), car_point.getY() + 4);
                carrier = new Carrier(car_point, other_point);
                break;
            }
        }
        for (Point p : carrier.hitbox){
            occupied[p.getX()][p.getY()] = true;
            occupied[p.getX()+1][p.getY()] = true;
            occupied[p.getX()][p.getY()+1] = true;
            occupied[p.getX()-1][p.getY()] = true;
            occupied[p.getX()][p.getY()-1] = true;
        }

        while(true){
            Point bat_point = new Point();  //create a random point
            Random rand = new Random();

            boolean vert = rand.nextBoolean();
            Point other_point;
            if (vert && bat_point.getX() + 3 <= 10 && bat_point.getX() + 3 >= 1 && !overlaps(bat_point, 3, true)) {
                other_point = new Point(bat_point.getX() + 3, bat_point.getY());
                battleship = new Battleship(bat_point, other_point);
                break;
            }
            else if (!vert && bat_point.getY() + 3 <= 10 && bat_point.getY() + 3 >= 1 && !overlaps(bat_point, 3, false)) {
                other_point = new Point(bat_point.getX(), bat_point.getY() + 3);
                battleship = new Battleship(bat_point, other_point);
                break;
            }
        }
        for (Point p : battleship.hitbox){
            occupied[p.getX()][p.getY()] = true;
            occupied[p.getX()+1][p.getY()] = true;
            occupied[p.getX()][p.getY()+1] = true;
            occupied[p.getX()-1][p.getY()] = true;
            occupied[p.getX()][p.getY()-1] = true;
        }
        while(true){
            Point cru_point = new Point();  //create a random point
            Random rand = new Random();

            boolean vert = rand.nextBoolean();
            Point other_point;
            if (vert && cru_point.getX() + 2 <= 10 && cru_point.getX() + 2 >= 1 && !overlaps(cru_point, 2, true)) {
                other_point = new Point(cru_point.getX() + 2, cru_point.getY());
                cruiser = new Cruiser(cru_point, other_point);
                break;
            }
            else if (!vert && cru_point.getY() + 2 <= 10 && cru_point.getY() + 2 >= 1 && !overlaps(cru_point, 2, false)) {
                other_point = new Point(cru_point.getX(), cru_point.getY() + 2);
                cruiser = new Cruiser(cru_point, other_point);
                break;
            }
        }
        for (Point p : cruiser.hitbox){
            occupied[p.getX()][p.getY()] = true;
            occupied[p.getX()+1][p.getY()] = true;
            occupied[p.getX()][p.getY()+1] = true;
            occupied[p.getX()-1][p.getY()] = true;
            occupied[p.getX()][p.getY()-1] = true;
        }
        while(true){
            Point sub_point = new Point();  //create a random point
            Random rand = new Random();

            boolean vert = rand.nextBoolean();
            Point other_point;
            if (vert && sub_point.getX() + 2 <= 10 && sub_point.getX() + 2 >= 1 && !overlaps(sub_point, 2, true)) {
                other_point = new Point(sub_point.getX() + 2, sub_point.getY());
                submarine = new Submarine(sub_point, other_point);
                break;
            }
            else if (!vert && sub_point.getY() + 2 <= 10 && sub_point.getY() + 2 >= 1 && !overlaps(sub_point, 2, false)) {
                other_point = new Point(sub_point.getX(), sub_point.getY() + 2);
                submarine = new Submarine(sub_point, other_point);
                break;
            }
        }
        for (Point p : submarine.hitbox){
            occupied[p.getX()][p.getY()] = true;
            occupied[p.getX()+1][p.getY()] = true;
            occupied[p.getX()][p.getY()+1] = true;
            occupied[p.getX()-1][p.getY()] = true;
            occupied[p.getX()][p.getY()-1] = true;
        }
        while(true){
            Point des_point = new Point();  //create a random point
            Random rand = new Random();

            boolean vert = rand.nextBoolean();
            Point other_point;
            if (vert && des_point.getX() + 1 <= 10 && des_point.getX() + 1 >= 1 && !overlaps(des_point, 1, true)) {
                other_point = new Point(des_point.getX() + 1, des_point.getY());
                destroyer = new Destroyer(des_point, other_point);
                break;
            }
            else if (!vert && des_point.getY() + 1 <= 10 && des_point.getY() + 1 >= 1 && !overlaps(des_point, 1, false)) {
                other_point = new Point(des_point.getX(), des_point.getY() + 1);
                destroyer = new Destroyer(des_point, other_point);
                break;
            }
        }
        for (Point p : destroyer.hitbox){
            occupied[p.getX()][p.getY()] = true;
            occupied[p.getX()+1][p.getY()] = true;
            occupied[p.getX()][p.getY()+1] = true;
            occupied[p.getX()-1][p.getY()] = true;
            occupied[p.getX()][p.getY()-1] = true;
        }

        map_hitpoints = 17;
    }

    //returns true if there is no occupied tile between p and p + dist (vertically or horizontally)
    private boolean overlaps(Point p, int dist, boolean vert){
        if(vert){
            for(int target_x = p.getX(); target_x <= p.getX() + dist; target_x++){
                if(occupied[target_x][p.getY()]) return true;
            }
        }
        else{
            for(int target_y = p.getY(); target_y <= p.getY() + dist; target_y++){
                if(occupied[p.getX()][target_y]) return true;
            }
        }
        return false;
    }
}
