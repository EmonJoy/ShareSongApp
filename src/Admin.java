import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;

public class Admin extends JFrame  {



    Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    int width = (int) size.getWidth();
    int height = (int) size.getHeight();

    Admin(){
        setTitle("Profile");
        setLayout(null);
        getContentPane().setBackground(Color.CYAN);
        setSize(width,height);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTextArea j = new JTextArea();
        j.setBounds(100, 100 , 600,600);
        j.setFont(new Font("Times new roman",Font.BOLD, 20));

        JButton btn = new JButton("back");
        btn.setBounds(800,100, 200,100);
        btn.setFocusable(false);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginForm l = new LoginForm();
                MainFrame m = new MainFrame(l.LoginUser);
                dispose();
            }
        });

        JButton logout = new JButton("Logout");
        logout.setFocusable(false);
        logout.setBounds(800, 300 , 200,100);

        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginForm l = new LoginForm();
                dispose();
            }
        });


        add(logout);



        add(btn);



        try(BufferedReader r= new BufferedReader(new FileReader("Profile.txt"))) {
            String line;
            String text = "";
            while ((line = r.readLine())!=null){
                text += line +"\n";
            }
            j.setText(text);


        }catch (IOException ae)
        {
            JOptionPane.showMessageDialog(null,"Error","Error Title",JOptionPane.ERROR_MESSAGE);
        }

        add(j);


        setVisible(true);
    }

}