class Map {
    public Carrier carrier;
    public Battleship battleship;
    public Cruiser cruiser;
    public Submarine submarine;
    public Destroyer destroyer;

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

    }

    public void print_map(){
        carrier.print_Ship();
        battleship.print_Ship();
        cruiser.print_Ship();
        submarine.print_Ship();
        destroyer.print_Ship();
    }

}
