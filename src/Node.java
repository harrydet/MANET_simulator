import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Harry on 1/8/2015.
 */
public class Node {
    private int location_X, location_Y, radius, frameCounter;
    private double speedX, speedY;

    private final static int BOUND_LOW_X = 50;
    private final static int BOUND_LOW_Y = 50;
    private final static int BOUND_HIGH_X = 750;
    private final static int BOUND_HIGH_Y = 750;
    private Color color;
    private double timer;
    private ArrayList<Node> nodesSentTo;
    private boolean hasReceived;


    public void incrementFrame(int byNo) {
        this.frameCounter += byNo;
    }

    public boolean hasReceived() {
        return hasReceived;
    }

    public void setHasReceived(boolean hasReceived) {
        this.hasReceived = hasReceived;
    }

    public Node() {
        color = Color.red;

        Random r = new Random();
        location_X = r.nextInt(BOUND_HIGH_X - BOUND_LOW_X) + BOUND_LOW_X;
        location_Y = r.nextInt(BOUND_HIGH_Y - BOUND_LOW_Y) + BOUND_LOW_Y;
        radius = 0;
        speedX = 0;
        speedY = 0;
        timer = 100 * Math.random();
        frameCounter = 0;
        nodesSentTo = new ArrayList<Node>();
        hasReceived = false;

    }

    public Node(int x, int y, Color color){
        location_X = x;
        location_Y = y;
        this.color = color;
        radius = 0;

    }

    private void calculateSpeeds(){
        speedX = (Math.random() > 0.5) ? - 1 : 1;
        speedY = (Math.random() > 0.5) ? - 1 : 1;
    }

    private void checkBounaries(){
        if(location_X >= BOUND_HIGH_X){
            speedX = -1;
            frameCounter = 0;
        } else if (location_X <= BOUND_LOW_X){
            speedX = 1;
            frameCounter = 0;
        } else if (location_Y >= BOUND_HIGH_Y){
            speedY = -1;
            frameCounter = 0;
        } else if(location_Y <= BOUND_LOW_Y){
            speedY = 1;
            frameCounter = 0;
        }

    }

    public void addNodeToList(Node n){
        nodesSentTo.add(n);
    }

    public void move(){
        checkBounaries();
        location_X += speedX;
        location_Y += speedY;
        resetSpeeds();
    }

    public int getLocation_X() {
        return location_X;
    }

    public void setLocation_X(int location_X) {
        this.location_X = location_X;
    }

    public int getLocation_Y() {
        return location_Y;
    }

    public void setLocation_Y(int location_Y) {
        this.location_Y = location_Y;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void resetSpeeds(){
        if(frameCounter >= timer){
            calculateSpeeds();
            frameCounter = 0;
        }
    }

    public boolean isInList(Node n){
        return nodesSentTo.contains(n);
    }

}
