package battleship;

class InvalidPointException extends Exception {
    public InvalidPointException(String s)
    {
        super(s);
    }
}

class ShipsOverlap extends Exception{
    public ShipsOverlap(String s){
        super(s);
    }
}

class OversizeException extends Exception{
    public OversizeException(String s){
        super(s);
    }
}
