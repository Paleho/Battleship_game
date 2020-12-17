class Carrier extends Ship{
    public Carrier(Point p1, Point p2){
        super(p1, p2);
        if(length != 5)
            System.out.printf("Invalid Carrier length!\n");
    }

    public int shot(){ return 350;}
    public int sink(){ return 1000;}
}

class Battleship extends Ship{
    public Battleship(Point p1, Point p2){
        super(p1, p2);
        if(length != 4)
            System.out.printf("Invalid Battleship length!\n");
    }

    public int shot(){ return 250;}
    public int sink(){ return 500;}
}

class Cruiser extends Ship{
    public Cruiser(Point p1, Point p2){
        super(p1, p2);
        if(length != 3)
            System.out.printf("Invalid Cruiser length!\n");
    }

    public int shot(){ return 100;}
    public int sink(){ return 250;}
}

class Submarine extends Ship{
    public Submarine(Point p1, Point p2){
        super(p1, p2);
        if(length != 3)
            System.out.printf("Invalid Submarine length!\n");
    }

    public int shot(){ return 100;}
    public int sink(){ return 0;}
}

class Destroyer extends Ship{
    public Destroyer(Point p1, Point p2){
        super(p1, p2);
        if(length != 2)
            System.out.printf("Invalid Destroyer length!\n");
    }

    public int shot(){ return 50;}
    public int sink(){ return 0;}
}
