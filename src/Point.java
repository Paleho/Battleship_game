class Point{
    private int x,y;
    public Point(int x, int y){
        if(x >= 1 && x <= 10 && y >= 1 && y <= 10){
            this.x = x;
            this.y = y;
        }
        else{
            System.out.print("Invalid point initialization!\n");
        }
    }

    public Point(Point other_point, Direction dir, int distance){
        int new_x, new_y;

        switch(dir){
            case Up:
                new_y = other_point.getY();
                new_x = other_point.getX() - distance;
                break;
            case Down:
                new_y = other_point.getY();
                new_x = other_point.getX() + distance;
                break;
            case Left:
                new_x = other_point.getX();
                new_y = other_point.getY() - distance;
                break;
            case Right:
                new_x = other_point.getX();
                new_y = other_point.getY() + distance;
                break;
            default:
                new_x = 1;
                new_y = 1;
                System.out.println("Invalid point (relative) initialization");
                break;
        }

        if(new_x >= 1 && new_x <= 10 && new_y >= 1 && new_y <= 10){
            this.x = new_x;
            this.y = new_y;
        }
        else{
            System.out.print("Invalid point initialization!\n");
        }
    }
    public int getX(){return x;}
    public int getY(){return y;}
    public void print_Point(){
        System.out.printf("(%d,%d)", x, y);
    }
}