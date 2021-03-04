package battleship;

import java.util.Random;

class Map {
    public Carrier carrier;
    public Battleship battleship;
    public Cruiser cruiser;
    public Submarine submarine;
    public Destroyer destroyer;
    private int map_hitpoints;

    private boolean[][] occupied = new boolean[12][12];

    /**
     * Class constructor specifying a starting point and a direction for each ship that will be placed in the map.
     *
     * The map object created represents the board of a player and stores the position of the 5 ships. It can be
     * used to shoot/attack the player that is regarded as the map's owner and get information about it's state.
     *
     * @param car_point a point representing one end of Carrier
     * @param car_direction the direction in which Carrier is placed
     * @param bat_point a point representing one end of Battleship
     * @param bat_direction the direction in which Battleship is placed
     * @param cru_point a point representing one end of Cruiser
     * @param cru_direction the direction in which Cruiser is placed
     * @param sub_point a point representing one end of Submarine
     * @param sub_direction the direction in which Submarine is placed
     * @param des_point a point representing one end of Destroyer
     * @param des_direction the direction in which Destroyer is placed
     * @throws ShipsOverlap if the cells of two (or more) ships overlap; or if at least two ships occupy
     *                      adjacent (vertically or horizontally) cells
     * @throws OversizeException if one or more ships need to occupy cells that are beyond the borders of
     *                           the 10x10 grid.
     */
    public Map(Point car_point, Direction car_direction,
               Point bat_point, Direction bat_direction,
               Point cru_point, Direction cru_direction,
               Point sub_point, Direction sub_direction,
               Point des_point, Direction des_direction) throws ShipsOverlap, OversizeException {
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
            if(occupied[p.getX()][p.getY()]) {
                System.out.println("battleship.Map error: Ships overlap (" + p.getX() + "," + p.getY() + ") (battleship)");
                throw new ShipsOverlap("Battleship overlap");
            }
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
            if(occupied[p.getX()][p.getY()]) {
                System.out.println("battleship.Map error: Ships overlap (" + p.getX() + "," + p.getY() + ") (cruiser)");
                throw new ShipsOverlap("Cruiser overlap");
            }
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
            if(occupied[p.getX()][p.getY()]) {
                System.out.println("battleship.Map error: Ships overlap (" + p.getX() + "," + p.getY() + ") (submarine)");
                throw new ShipsOverlap("Submarine overlap");
            }
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
            if(occupied[p.getX()][p.getY()]) {
                System.out.println("battleship.Map error: Ships overlap (" + p.getX() + "," + p.getY() + ") (destroyer)");
                throw new ShipsOverlap("Destroyer overlap");
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

    //random map constructor

    /**
     * Class constructor that generates a random map.
     *
     * The placement of the ships is according to the rules. It can be used for the "defence" board of CPU-player
     */
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

    /**
     * Prints each ship's position in the map.
     */
    public void print_map(){
        carrier.print_Ship();
        battleship.print_Ship();
        cruiser.print_Ship();
        submarine.print_Ship();
        destroyer.print_Ship();
    }

    /**
     * Performs an attack at the (x, y) cell and informs the state of the map accordingly.
     * <ul>
     * <li>on successful hit returns score
     * <li>on unsuccessful attempt returns 0
     * <li>on invalid attempt returns -1
     * <li>on all ships down returns -2
     * </ul>
     *
     * @param x Specifies the vertical coordinate.
     * @param y Specifies the horizontal coordinate.
     * @return the outcome of the attack
     */
    public int shoot(int x, int y){
        if(!(x >= 1 && x <= 10 && y >= 1 && y <= 10)){
            //out of bounds shot
            System.out.println("battleship.Map error: out of bounds shot");
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

    /**
     *A getter method for map's hitpoints
     *
     * @return the hitpoints of the map (map's "health")
     */
    public int getHitpoints(){ return map_hitpoints;}

    /**
     * Returns the amount of ships that are either in Intact or Damaged state.
     *
     * @return the number of active ships
     * @see Ship.ShipState
     */
    public int getActiveShips(){
        int count = 0;
        count += (carrier.state == Ship.ShipState.Destroyed) ? 0 : 1;
        count += (battleship.state == Ship.ShipState.Destroyed) ? 0 : 1;
        count += (cruiser.state == Ship.ShipState.Destroyed) ? 0 : 1;
        count += (submarine.state == Ship.ShipState.Destroyed) ? 0 : 1;
        count += (destroyer.state == Ship.ShipState.Destroyed) ? 0 : 1;

        return count;
    }

    /**
     * Returns the type of ship that occupies the (x,y) cell or "None" if there is no ships there.
     *
     * @param x Specifies the vertical coordinate.
     * @param y Specifies the horizontal coordinate.
     * @return the ship type at the specified coordinates
     */
    public String getShipType(int x, int y){
        for(Point i : carrier.hitbox){
            if(i != null && i.getX() == x && i.getY() == y){
                return "Carrier";
            }
        }
        for(Point i : battleship.hitbox){
            if(i != null && i.getX() == x && i.getY() == y){
                return "Battleship";
            }
        }
        for(Point i : cruiser.hitbox){
            if(i != null && i.getX() == x && i.getY() == y){
                return "Cruiser";
            }
        }
        for(Point i : submarine.hitbox){
            if(i != null && i.getX() == x && i.getY() == y){
                return "Submarine";
            }
        }
        for(Point i : destroyer.hitbox){
            if(i != null && i.getX() == x && i.getY() == y){
                return "Destroyer";
            }
        }
        return "None";
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
