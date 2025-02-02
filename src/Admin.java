import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Admin extends JFrame  {



    Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
   private  int width = (int) size.getWidth();
    private int height = (int) size.getHeight();



    Admin(){
        setTitle("Profile");
        setLayout(null);
        getContentPane().setBackground(Color.CYAN);
        setSize(width,height);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        ImageIcon img = new ImageIcon(getClass().getResource("user.png"));
        Image dp = img.getImage();

        Image scaledImage = dp.getScaledInstance(180,180, Image.SCALE_SMOOTH);
        img = new ImageIcon(scaledImage);

        JLabel adminPhoto = new JLabel(img);
        adminPhoto.setBounds(800,80,180,180);
        add(adminPhoto);

        JLabel adminLabel = new JLabel("User Profile");
        adminLabel.setBounds(500,5,600,50);
        adminLabel.setFont(new Font("serif", Font.BOLD, 40));
        add(adminLabel);

        JSeparator sep = new JSeparator();
        sep.setBounds(500,58,200,2);
        add(sep);



        JLabel userInfo = new JLabel("User Info: ");
        userInfo.setBounds(100,60,600,40);
        userInfo.setFont(new Font("times new roman",Font.BOLD,40));
        add(userInfo);

        JLabel post_details = new JLabel("Post Details: ");
        post_details.setFont(new Font("times new roman",Font.BOLD,40));
        post_details.setBounds(100,340,600,40);
        add(post_details);

        JTextArea j = new JTextArea();
        j.setBounds(100, 100 , 600,200);
        j.setFont(new Font("Times new roman",Font.BOLD, 20));


        JTextArea j1 = new JTextArea();
        j1.setBounds(100, 380 , 600,300);
        j1.setFont(new Font("Times new roman",Font.BOLD, 20));

        JButton btn = new JButton("back");
        btn.setBounds(100,700, 200,40);
        btn.setBackground(new Color(204,255,204));
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
        logout.setBackground(new Color(255,153,255));
        logout.setBounds(320, 700 , 200,40);

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



        // read file and add file

        try (BufferedReader r = new BufferedReader(new FileReader("Post.txt"))){
            String line1;
            String text1 = "";
            while ((line1 = r.readLine())!=null){
                text1 += line1 +"\n";
            }
            j1.setText(text1);


        }catch (IOException ae){
            JOptionPane.showMessageDialog(null,"Error","Error Title",JOptionPane.ERROR_MESSAGE);

        }


        add(j);
        add(j1);


        setVisible(true);
    }

    public static void main(String[] args) {
        new Admin();
    }


}
