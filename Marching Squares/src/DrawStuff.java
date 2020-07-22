

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.PixelGrabber;

import static java.lang.Math.ceil;

public class DrawStuff extends JPanel implements ActionListener {
    Timer t = new Timer(1, this);
    int res = 10;

    int cols = 1336/res;
    int rows = 768/res;
    float zoff = 0;
    float increment = 0.125f;
    float[][] field = new float[cols+1][rows+1];
    //Setup
    public DrawStuff() {
        setFocusable(true);
        t.start();
    }

    //Draw
    @Override
    protected void paintComponent(Graphics graphics) {
        OpenSimplexNoise noise = new OpenSimplexNoise();

        float xoff = 0;
        for (int i = 0; i < cols; i++) {
            xoff += increment;
            float yoff = increment;

            for (int j = 0; j < rows; j++) {

                field[i][j] = (float)noise.eval(xoff,yoff,zoff);
                yoff += 0.1;
            }


        }

            super.paintComponent(graphics);
            this.setBackground(Color.GRAY);
            Graphics2D g = (Graphics2D) graphics;

            for (int i = 0; i < cols; i++) {
                for (int j = 0; j < rows; j++) {

                    g.setStroke(new BasicStroke((int) (res * 0.2)));
                    g.setColor(new Color((int)ceil(field[i][j])*255,(int)ceil(field[i][j])*255,(int)ceil(field[i][j])*255));
                    g.drawRect(i * res, j * res, 1, 1);
                }
            }
            g.setColor(Color.WHITE);
            g.setStroke(new BasicStroke(1));
            for (int i = 0; i < cols; i++) {
                for (int j = 0; j < rows; j++) {
                    int x = (int) i * res;
                    int y = (int) j * res;

                    int currentState = getState((int)ceil(field[i][j]),
                            (int)ceil(field[i + 1][j]),
                            (int)ceil(field[i + 1][j + 1]),
                            (int)ceil(field[i][j + 1]));
                    Point a = new Point((int) (x + (res * 0.5)), y);
                    Point b = new Point(x + res, (int) (y + (res * 0.5)));
                    Point c = new Point((int) (x + (res * 0.5)), y + res);
                    Point d = new Point(x, (int) (y + (res * 0.5)));

                    if (currentState == 0 || currentState == 15) {

                    }
                    if (currentState == 1 || currentState == 14) {
                        g.drawLine(d.x, d.y, c.x, c.y);
                    }
                    if (currentState == 2 || currentState == 13) {
                        g.drawLine(c.x, c.y, b.x, b.y);
                    }
                    if (currentState == 3 || currentState == 12) {
                        g.drawLine(d.x, d.y, b.x, b.y);
                    }
                    if (currentState == 4 || currentState == 11) {
                        g.drawLine(a.x, a.y, b.x, b.y);
                    }
                    if (currentState == 5) {
                        g.drawLine(a.x, a.y, d.x, d.y);
                        g.drawLine(c.x, c.y, b.x, b.y);
                    }
                    if (currentState == 6 || currentState == 9) {
                        g.drawLine(a.x, a.y, c.x, c.y);
                    }
                    if (currentState == 7 || currentState == 8) {
                        g.drawLine(d.x, d.y, a.x, a.y);
                    }
                    if (currentState == 10) {
                        g.drawLine(d.x, d.y, c.x, c.y);
                        g.drawLine(a.x, a.y, b.x, b.y);
                    }
                }
            }
        }


    int getState(int a,int b,int c,int d)
    {
     return (a*8+b*4+c*2+d*1);
    }
    //Update
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        zoff+=0.001;
        repaint();
    }
}
