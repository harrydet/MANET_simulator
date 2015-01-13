import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.*;


public class Surface extends JPanel implements ActionListener {
    private final int TOTAL_NODES = 50;
    private final int S_WIDTH = 800;
    private final int S_HEIGHT = 800;
    private final int DELAY = 25;
    private final int INITIAL_X = 200;
    private final int INITIAL_Y = 200;
    private final int RADIUS = 10;
    private Timer timer;
    private ArrayList<Node> nodes;
    private double totalSimFrames;
    private int source, target;
    private long startTime;

    public Surface() {
        initSurface();
    }

    public void initSurface() {
        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(S_WIDTH, S_HEIGHT));
        setDoubleBuffered(true);

        nodes = new ArrayList<Node>();
        for (int i = 0; i < TOTAL_NODES; i++) {
            nodes.add(new Node());
        }

        timer = new Timer(DELAY, this);
        timer.start();
        startTime = System.nanoTime();

        totalSimFrames = 0;

        source = ((int)Math.floor(Math.random()*TOTAL_NODES));
        target = ((int)Math.floor(Math.random()*TOTAL_NODES));

        nodes.get(source).setHasReceived(true);
        nodes.get(source).setColor(Color.GREEN);
        nodes.get(target).setColor(Color.PINK);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawNodes(g);
    }

    private void drawNodes(Graphics graphics) {
        for (int i = 0; i < TOTAL_NODES; i++) {
            Graphics2D g2d = (Graphics2D) graphics;
            g2d.setColor(Color.black);
            g2d.drawOval(nodes.get(i).getLocation_X() - 100 / 2,
                    nodes.get(i).getLocation_Y() - 100 / 2,
                    100, 100);
            g2d.setColor(nodes.get(i).getColor());
            g2d.fillOval(nodes.get(i).getLocation_X() - nodes.get(i).getRadius() / 2,
                    nodes.get(i).getLocation_Y() - nodes.get(i).getRadius() / 2,
                    nodes.get(i).getRadius(), nodes.get(i).getRadius());
            g2d.setColor(Color.black);
            g2d.drawString(Integer.toString(i), nodes.get(i).getLocation_X(), nodes.get(i).getLocation_Y());
        }
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < TOTAL_NODES; i++) {
            nodes.get(i).setRadius(nodes.get(i).getRadius() + 1);

            if (nodes.get(i).getRadius() >= 100) {
                nodes.get(i).setRadius(100);
            }
        }
        randomMovement();
        checkRanges();

        repaint();
        totalSimFrames++;
        checkEnd();
    }

    public void randomMovement() {
        for (int i = 0; i < TOTAL_NODES; i++) {
            nodes.get(i).move();
            nodes.get(i).incrementFrame(1);
        }
    }

    public void checkRanges() {
        int r1, r2, x1, x2, y1, y2;
        for (int i = 0; i < TOTAL_NODES; i++) {

            x1 = nodes.get(i).getLocation_X();
            y1 = nodes.get(i).getLocation_Y();
            r1 = nodes.get(i).getRadius();
            boolean hasContact = false;
            for (int j = 0; j < TOTAL_NODES; j++) {
                if (i != j) {
                    x2 = nodes.get(j).getLocation_X();
                    y2 = nodes.get(j).getLocation_Y();
                    r2 = nodes.get(j).getRadius();

                    int sumR = r1 / 2 + r2 / 2;
                    int subR = r1 / 2 - r2 / 2;
                    double sum = Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2);
                    double sqrt = Math.sqrt(sum);

                    if (sum >= Math.pow(subR, 2) && sum <= Math.pow(sumR, 2)) {
                        nodes.get(i).setColor(Color.YELLOW);
                        hasContact = true;
                        if((nodes.get(j).hasReceived() && !nodes.get(j).isInList(nodes.get(i))) || nodes.get(i).hasReceived()){
                            nodes.get(i).setColor(Color.GREEN);
                            nodes.get(i).setHasReceived(true);
                            nodes.get(j).addNodeToList(nodes.get(i));
                        }
                    }

                }
            }
            if (!hasContact && !nodes.get(i).hasReceived())
                nodes.get(i).setColor(Color.RED);
            if(i == target){
                nodes.get(i).setColor(Color.PINK);
            }
        }
    }

    public void checkEnd(){
        if(nodes.get(target).hasReceived()){
            timer.stop();
            System.out.println("Total time elapsed: " + ((System.nanoTime() - startTime)/1000000) + "ms");
            System.out.println("Source: " + source + "    Destination: " + target);
        }
    }

}
