
class Point{
    private int x,y;
    @SuppressWarnings("RedundantStringFormatCall")
    public Point(int x, int y){
        if(x >= 1 && x <= 10 && y >= 1 && y <= 10){
            this.x = x;
            this.y = y;
        }
        else{
            System.out.printf("Invalid point initialization!\n");
        }
    }
    public int getX(){return x;}
    public int getY(){return y;}
    public void print_Point(){
        System.out.printf("(%d,%d)", x, y);
    }
}

@SuppressWarnings("ManualMinMaxCalculation")
class Ship{
    public Point edge1, edge2;
    public boolean vertical;
    public Point[] hitbox;
    public int length = 0;

    @SuppressWarnings("RedundantStringFormatCall")
    public Ship(Point p1, Point p2){
        edge1 = p1;
        edge2 = p2;

        int begin = 0;
        if(edge1.getX() == edge2.getX()){
            vertical = false;
            length = Math.abs(edge1.getY()-edge2.getY()) + 1;
            hitbox = new Point[length];
            // begin = lowest y
            begin = (edge1.getY() < edge2.getY())? edge1.getY() : edge2.getY();
        }
        else if(edge1.getY() == edge2.getY()){
            vertical = true;
            length = Math.abs(edge1.getX()-edge2.getX()) + 1;
            hitbox = new Point[length];
            // begin = lowest x
            begin = (edge1.getX() < edge2.getX())? edge1.getX() : edge2.getX();
        }
        else
            System.out.printf("Invalid ship initialization!\n");

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


    }
}

public class Main{
    public static void main(String[] args) {
        Ship myship = new Ship(new Point(3, 4), new Point(3, 7));

        System.out.println("Ship:");
        for(Point p : myship.hitbox){
            p.print_Point();
            System.out.println();
        }

        Carrier mycarrier = new Carrier(new Point(3, 4), new Point(7, 4));

        System.out.println("Carrier:");
        for(Point p : mycarrier.hitbox){
            p.print_Point();
            System.out.println();
        }
    }
}
