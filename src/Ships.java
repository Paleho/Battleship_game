class Ship{
    public Point edge1, edge2;
    public boolean vertical;
    public Point[] hitbox;
    public int length = 0;

    enum ShipState{
        Intact,
        Damaged,
        Destroyed
    }
    public ShipState state;

    public Ship(Point p1, Point p2){
        edge1 = p1;
        edge2 = p2;

        int begin = 0;
        if(edge1.getX() == edge2.getX()){
            vertical = false;
            length = Math.abs(edge1.getY()-edge2.getY()) + 1;
            hitbox = new Point[length];
            // begin = lowest y
            begin = Math.min(edge1.getY(), edge2.getY());
        }
        else if(edge1.getY() == edge2.getY()){
            vertical = true;
            length = Math.abs(edge1.getX()-edge2.getX()) + 1;
            hitbox = new Point[length];
            // begin = lowest x
            begin = Math.min(edge1.getX(), edge2.getX());
        }
        else
            System.out.print("Invalid ship initialization!\n");

        if(!vertical){
            for(int i = 0; i < length; i++){
                hitbox[i] = new Point(edge1.getX(), begin);
                begin++;
            }
        }
        else{
            for(int i = 0; i < length; i++){
                hitbox[i] = new Point(begin, edge1.getY());
                begin++;
            }
        }

        state = ShipState.Intact;
    }

    public void print_Ship(){
        for (Point p : hitbox){
            p.print_Point();
            System.out.print(" ");
        }
        System.out.println();
    }

}

class Carrier extends Ship{
    public Carrier(Point p1, Point p2){
        super(p1, p2);
        if(length != 5)
            System.out.print("Invalid Carrier length!\n");
    }

    public void print_Ship(){
        System.out.print("Carrier: ");
        super.print_Ship();
    }
    public int shot(){ return 350;}
    public int sink(){ return 1000;}
}

class Battleship extends Ship{
    public Battleship(Point p1, Point p2){
        super(p1, p2);
        if(length != 4)
            System.out.print("Invalid Battleship length!\n");
    }

    public void print_Ship(){
        System.out.print("Battleship: ");
        super.print_Ship();
    }
    public int shot(){ return 250;}
    public int sink(){ return 500;}
}

class Cruiser extends Ship{
    public Cruiser(Point p1, Point p2){
        super(p1, p2);
        if(length != 3)
            System.out.print("Invalid Cruiser length!\n");
    }

    public void print_Ship(){
        System.out.print("Cruiser: ");
        super.print_Ship();
    }
    public int shot(){ return 100;}
    public int sink(){ return 250;}
}

class Submarine extends Ship{
    public Submarine(Point p1, Point p2){
        super(p1, p2);
        if(length != 3)
            System.out.print("Invalid Submarine length!\n");
    }

    public void print_Ship(){
        System.out.print("Submarine: ");
        super.print_Ship();
    }
    public int shot(){ return 100;}
    public int sink(){ return 0;}
}

class Destroyer extends Ship{
    public Destroyer(Point p1, Point p2){
        super(p1, p2);
        if(length != 2)
            System.out.print("Invalid Destroyer length!\n");
    }

    public void print_Ship(){
        System.out.print("Destroyer: ");
        super.print_Ship();
    }
    public int shot(){ return 50;}
    public int sink(){ return 0;}
}
