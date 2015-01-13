import javax.swing.*;
import java.awt.*;

/**
 * Created by Harry on 1/8/2015.
 */
public class SwingTimer extends JFrame{

    public SwingTimer(){
        initUI();
    }

    public void initUI(){
        add(new Surface());

        setResizable(false);
        pack();

        setTitle("MANET Mobility Simulator");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame ex = new SwingTimer();
                ex.setVisible(true);
            }
        });
    }

}
