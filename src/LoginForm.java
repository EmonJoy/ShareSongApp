import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;



public class LoginForm {
    private MainFrame mainFrame;
    String LoginUser;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int width = (int) screenSize.getWidth();
    private int height = (int) screenSize.getHeight();
    private JFrame frame = new JFrame("Login");

    LoginForm() {

        ImageIcon image = new ImageIcon(getClass().getResource("/login.jpg"));
        Image img = image.getImage();
        Image scaledImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        image = new ImageIcon(scaledImage);

        JLabel background = new JLabel(image);
        JLabel loginLabel = new JLabel("Login");
        JLabel nameLabel = new JLabel("User Name: ");
        JLabel passLabel = new JLabel("Password: ");
        JTextField nameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");
        loginLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));
        loginLabel.setForeground(Color.WHITE);
        loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);



        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);






        passLabel.setFont(new Font("Arial", Font.BOLD, 20));
        passLabel.setForeground(Color.WHITE);
        passLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        nameField.setMaximumSize(new Dimension(300, 30));
        nameField.setAlignmentX(Component.CENTER_ALIGNMENT);

        passwordField.setMaximumSize(new Dimension(300, 30));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginButton.setFocusable(false);
        loginButton.setForeground(new Color(128, 128, 0));
        loginButton.setFont(new Font("Lucida Handwriting", Font.BOLD, 20));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String password = new String(passwordField.getPassword());
                boolean isAuthenticated = false;

                try (BufferedReader reader = new BufferedReader(new FileReader("Data.txt"));
                        BufferedWriter r = new BufferedWriter(new FileWriter("Profile.txt"))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(",");
                        if (parts.length == 3 && parts[0].equals(name) && parts[2].equals(password)) {
                            JOptionPane.showMessageDialog(frame, "Welcome " + parts[0] + "!");
                            LoginUser = parts[0];
                            isAuthenticated = true;
                            JOptionPane.showMessageDialog(null,"Login Succeed");

                            mainFrame  = new MainFrame(LoginUser);
                            frame.dispose();

                            r.write("-----------------------");
                            r.newLine();
                            r.write("Name :"+parts[0]);
                            r.newLine();
                            r.write("Email :"+parts[1]);
                            r.newLine();
                            r.write("Passowrd: "+parts[2]);
                            r.newLine();
                            r.write("-----------------------");
                            break;
                        }
                    }

                    if (!isAuthenticated) {
                        JOptionPane.showMessageDialog(frame, "Email or Password does not match!");
                    }

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error reading data: " + ex.getMessage());
                }
            }
        });




        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);
        centerPanel.add(Box.createVerticalStrut(50));
        centerPanel.add(loginLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(nameLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(nameField);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(passLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(passwordField);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(loginButton);

        background.setLayout(new GridBagLayout());
        background.add(centerPanel);

        frame.setContentPane(background);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new LoginForm();
    }

}
