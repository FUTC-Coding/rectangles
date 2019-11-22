package main;

public class Main {

    public static void main(String[] args) {
	    Rectangle r = new Rectangle(1,4,2,3);
	    Rectangle r1 = new Rectangle(2,5,3,3);
	    Rectangle r2 = new Rectangle(2,4,4,4);
	    Rectangle r3 = new Rectangle(2,3,2,1);
	    Rectangle rnew = Rectangle.intersection(r,r1,r2,r3);
	    System.out.println(Integer.toString(rnew.x) + " " + Integer.toString(rnew.y) + " " + Integer.toString(rnew.width) + " " + Integer.toString(rnew.height));
    }
}
