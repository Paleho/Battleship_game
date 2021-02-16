package battleship;

public class ShotRecord {
    public int x, y;
    public boolean successful;
    public String shipType;

    public ShotRecord(int x, int y, boolean successful, String shipType){
        this.x = x;
        this.y = y;
        this.successful = successful;
        this.shipType = shipType;
    }

    @Override
    public String toString(){
        String outcome = successful ? "successful" : "unsuccessful";
        return "x = " + x + ", y = " + y + ", " + outcome + ", Ship type: " + shipType;
    }
}
