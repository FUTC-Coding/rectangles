package main;

public class Main {

    public static void main(String[] args) {
	    Rectangle r = new Rectangle(-5,5,3,3);
	    Rectangle r1 = new Rectangle(-4,4,1,1);
	    Rectangle r2 = new Rectangle(3,5,3,4);
	    Rectangle rnew = Rectangle.intersection(r,r1);
	    System.out.println(Integer.toString(rnew.x) + " " + Integer.toString(rnew.y) + " " + Integer.toString(rnew.width) + " " + Integer.toString(rnew.height));
    	System.out.println(r2.toString());
    	System.out.println(r1.determineSpecies());
    }
}
