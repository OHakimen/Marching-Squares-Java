import javax.swing.*;

public class Main extends JFrame {
    public Main()
    {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        JPanel panel = new DrawStuff();
        add(panel);
        setDefaultCloseOperation(3);
        setVisible(true);
    }
    public static void main(String[] args) {
        new Main();
    }


}
