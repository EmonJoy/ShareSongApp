import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BaseFrame  {
    JFrame frame = new JFrame("Share Songs");
    JLabel right_label = new JLabel();
    JLabel Lable = new JLabel();
    JLabel Background;
    JButton btn1 = new JButton();
    JButton btn2 = new JButton();
    JLabel or_label = new JLabel("OR");

    SignUpform s;
    Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    int width = (int) size.getWidth();
    int height = (int) size.getHeight();
    BaseFrame() {

        Lable.setText("Welcome to The ShareSongs Application");
        Lable.setFont(new Font("Times New Roman", Font.BOLD, 30));
        Lable.setAlignmentX(Component.CENTER_ALIGNMENT);
        Lable.setForeground(Color.BLACK);

        btn1.setText("Login");
        btn1.setFocusable(false);
        btn1.setBackground(Color.CYAN);
        btn1.setFont(new Font("Lucida Handwriting",Font.BOLD,20));
        btn1.setMaximumSize(new Dimension(300,30));
        btn1.setAlignmentX(Component.CENTER_ALIGNMENT);

        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginForm l = new LoginForm();
                frame.dispose();
            }
        });

        btn2.setText("Sign Up");
        btn2.setFocusable(false);
        btn2.setBackground(Color.CYAN);
        btn2.setFont(new Font("Lucida Handwriting",Font.BOLD,20));
        btn2.setMaximumSize(new Dimension(300,30));
        btn2.setAlignmentX(Component.CENTER_ALIGNMENT);

        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                s = new SignUpform();
                frame.dispose();
            }
        });




        or_label.setFont(new Font("Lucida Handwriting",Font.BOLD,20));
        or_label.setAlignmentX(Component.CENTER_ALIGNMENT);
        or_label.setForeground(Color.BLACK);



        ImageIcon image = new ImageIcon(getClass().getResource("/pp.jpg"));
        Image img = image.getImage();


        Image scaledImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        image = new ImageIcon(scaledImage);


        Background = new JLabel(image);



        Background.setSize(width, height);
        Background.setOpaque(true);
        Background.add(Box.createVerticalStrut(20));
        Background.add(Lable);
        Background.add(Box.createVerticalStrut(20));
        Background.add(btn1);

        Background.add(Box.createVerticalStrut(20));
        Background.add(or_label);

        Background.add(Box.createVerticalStrut(20));
        Background.add(btn2);

        Background.setLayout(new BoxLayout(Background,BoxLayout.Y_AXIS));



        frame.setLayout(null);

        frame.add(Background);
        frame.setContentPane(Background);


        frame.setSize(width, height);
        frame.add(right_label);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);
    }


}
