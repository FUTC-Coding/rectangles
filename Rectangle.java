package main;

public class Rectangle {
    int x,y,width,height;

    /**
     * Konstruktor der Klasse Rectangle der ein Rechteck mit den übergebenen Werten erstellt
     * @param xInput die x-koordinate
     * @param yInput die y-koordinate
     * @param widthInput die Breite des Rechtecks
     * @param heightInput die Hoehe des Rechtecks
     * @return das erstellte Rechteck
     */
    public Rectangle(int xInput, int yInput, int widthInput, int heightInput) {
        x = xInput;
        y = yInput;
        if (widthInput >= 0) {
            width = widthInput;
        } else {
            Utils.error("Breite kann nicht kleiner 0 sein");
            return;
        }

        if (heightInput >= 0) {
            height = heightInput;
        } else {
            Utils.error("Hoehe kann nicht kleiner 0 sein");
            return;
        }
    }

    /**
     * Konstruktor der Klasse Rectangle der Quadrat mit den übergebenen Werten erstellt
     * @param xInput die x-koordinate
     * @param yInput die y-koordinate
     * @param sidelengthInput Die Seitenlaenge des Quadrats
     */
    public Rectangle(int xInput, int yInput, Integer sidelengthInput) {
        x = xInput;
        y = yInput;
        if (sidelengthInput >= 0) {
            width = sidelengthInput;
            height = sidelengthInput;
        } else {
            Utils.error("Seitenlaenge kann nicht kleiner 0 sein");
        }
    }

    /**
     * Gibt ein objekt der Klasse Rechteck zurück mit den gleichen werten als das übergebene Objekt
     * @param toCopy Das Rechteck mit den Werten die kopiert werden sollen
     * @return Das kopierte Rechteck
     */
    public static Rectangle copy(Rectangle toCopy) {
        return new Rectangle(toCopy.x, toCopy.y, toCopy.width, toCopy.height);
    }

    public RectangleSpecies determineSpecies()  {
        if (this.x == 0 && this.y == 0) {
            return RectangleSpecies.POINT;
        } else if (this.x == 1 && this.y == 1) {
            return RectangleSpecies.PIXEL;
        } else if (this.height == 0 && this.width >= 1) {
            return RectangleSpecies.HLINE;
        } else if (this.height >= 1 && this.width == 0) {
            return RectangleSpecies.VLINE;
        } else if (this.width == this.height && this.width >= 2) {
            return RectangleSpecies.SQUARE;
        } else if (this.height == 1 && this.width >= 2) {
            return RectangleSpecies.ROW;
        } else if (this.height >= 2 && this.width == 1) {
            return RectangleSpecies.COLUMN;
        } else {
            return RectangleSpecies.OTHER;
        }
    }

    int getX() {
       return this.x;
    }

    void setX(int x) {
        this.x = x;
    }

    int getY() {
        return this.y;
    }

    void setY(int y) {
        this.y = y;
    }

    int getWidth() {
        return this.width;
    }

    void setWidth(int width) {
        if (width > 0) {
            this.width = width;
        } else {
            Utils.error("Breite kann nicht kleiner 1 sein");
        }

    }

    int getHeight() {
        return this.height;
    }

    void setHeight() {
        if (height > 0) {
            this.height = height;
        } else {
            Utils.error("Hoehe kann nicht kleiner 1 sein");
        }
    }

    /**
     * Gibt die koordinaten aller ecken des Rechtecks als String an von dem Rechteck auf dem es aufgerufen wird
     * @return koordinaten als string von der Ecke rechts unten gegen den Uhrzeigersinn
     */
    public String toString() {
        return ("(" + Integer.toString(this.x + this.width) + "|" + Integer.toString(this.y - this.height) + "),(" +
                Integer.toString(this.x + this.width) + "|" + (this.y) + "),(" +
                Integer.toString(this.x) + "|" + Integer.toString(this.y) + "),(" +
                Integer.toString(this.x) + "|" + Integer.toString(this.y - this.height) + ")");
    }

    //methode muss nicht auf ein Objekt aufgerufen werden deswegen als statisch deklariert
    static Rectangle union(Rectangle... rectangles) {
        if (rectangles.length == 0) {
            return null;
        }
        //Basiswert für alle attribute setzen
        int minX = rectangles[0].x;
        int maxX = rectangles[0].x+rectangles[0].width;
        int maxY = rectangles[0].y;
        int minY = rectangles[0].y-rectangles[0].height;

        //Minimale und maximale suchen
        for (Rectangle r: rectangles ) {
            if (r.x < minX) minX = r.x;
            if (r.y > maxY) maxY = r.y;
            if (r.x + r.width > maxX) maxX = r.x + r.width;
            if (r.y - r.height < minY) minY = r.y - r.height;
        }

        //neues Rechteck
        return new Rectangle(minX, maxY, maxX-minX, maxY-minY);
    }

    static Rectangle intersection(Rectangle... rectangles) {
        //use first rectangle as base
        Rectangle rnew = rectangles[0];

        if (rectangles.length == 0) {
            return null;
        } else if (rectangles.length == 1) {
            return rectangles[0];
        } else {
            //intersect every rect. in rectangles with each other
            for (Rectangle r : rectangles) {
                for (Rectangle t : rectangles) {
                    Rectangle rTemp = intersectTwo(r, t);
                    if (rTemp == null) return null;
                    if (rTemp.width * rTemp.height < rnew.width * rnew.height) rnew = rTemp; //make the rectangle with smallest size the new rectangle
                }
            }
        }
        return rnew;
    }

    private static Rectangle intersectTwo (Rectangle r1, Rectangle r2) {
        Rectangle rnew = new Rectangle(0,0,1,1);
        //check if rectangles intersect and which corner intersects
        int status = intersect(r1, r2);
        if (status == 0) {
            //reverse order of rectangles because if corner 1 and 2 of r1 arent in r2, corner 1 or 2 of r2 have to be in r1 (except if they dont intersect)
            status = intersect(r2, r1);
            if (status == 0) {
                Utils.error("rectangles don't intersect");
                return null;
            } else {
                status += 2; //add 2 to status so the status becomes 3 or 4 meaning corner 1 or two of r2
            }
        }
        if (status == 1){
            rnew.x = r1.x;
            rnew.y = r1.y;
            rnew.width = r2.x + r2.width - r1.x;
            if (r1.y - r1.height > r2.y - r2.height) {
                rnew.height = r1.height;
            } else {
                rnew.height = r1.y - (r2.y - r2.height);
            }
        } else if (status == 2) {
            rnew.x = r2.x;
            rnew.y = r1.y;
            rnew.width = r1.x + r1.width - r2.x;
            rnew.height = r1.y - (r2.y - r2.height);
        } else if (status == 3) {
            rnew.x = r2.x;
            rnew.y = r2.y;
            rnew.width = r1.x + r1.width - r2.x;
            rnew.height = r2.y - (r1.y - r1.height);
        } else if (status == 4) {
            rnew.x = r1.x;
            rnew.y = r2.y;
            rnew.width = r2.x + r2.width - r1.x;
            rnew.height = r2.y - (r1.y - r1.height);
        }
        return rnew;
    }

    /*checks if two rectangles intersect
    returns 1 if top left corner of r1 is in r2
    returns 2 if top right corner of r1 is in r2
    returns 0 if r1 and r2 don't intersect*/
    private static int intersect(Rectangle r1, Rectangle r2) {
        if (r1.x >= r2.x && r1.x < r2.x + r2.width && r1.y <= r2.y && r1.y > r2.y - r2.height) {
            return 1;
        } else if (r1.x + r1.width > r2.x && r1.x + r1.width <= r2.x + r2.width && r1.y <= r2.y && r1.y > r2.y - r2.height) {
            return 2;
        } else {
            return 0;
        }
    }
}
