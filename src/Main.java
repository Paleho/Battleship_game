public class Main{
    public static void main(String[] args) {
        Map my_map = new Map(new Point(7, 3), Direction.Up,
                             new Point(3, 6), Direction.Right,
                             new Point(6, 8), Direction.Left,
                             new Point(7, 9), Direction.Down,
                             new Point(10, 5), Direction.Left);

        my_map.print_map();
    }
}
