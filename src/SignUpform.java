import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class SignUpform {
    BaseFrame bs ;
    Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    int width = (int) size.getWidth();
    int height = (int) size.getHeight();

    JFrame frame = new JFrame("Sign Up");
    JLabel label = new JLabel("Sign Up");
    JLabel userName = new JLabel("User name:");
    JTextField textField = new JTextField(20);
    JLabel e_lable = new JLabel("Email: ");
    JTextField email = new JTextField(20);
    JLabel passName = new JLabel("Password:");
    JPasswordField passwordField = new JPasswordField(20);
    JButton submitButton = new JButton("Submit");

    SignUpform() {


        submitButton.setFocusable(false);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = textField.getText();
                String emails = email.getText();
                String pass = new String(passwordField.getPassword());

                try (BufferedWriter writer = new BufferedWriter(new FileWriter("Data.txt", true))) {
                    writer.write(name + "," + emails + "," + pass);
                    writer.newLine();
                    JOptionPane.showMessageDialog(frame, "Sign Up Successfully");
                    frame.dispose();
                    bs = new BaseFrame();

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error saving data: " + ex.getMessage());
                }

            }
        });


        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        label.setFont(new Font("Times New Roman", Font.BOLD, 40));
        label.setForeground(Color.RED);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        userName.setAlignmentX(Component.CENTER_ALIGNMENT);
        textField.setMaximumSize(new Dimension(300, 30));
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);

        e_lable.setAlignmentX(Component.CENTER_ALIGNMENT);
        email.setMaximumSize(new Dimension(300,30));
        email.setAlignmentX(Component.CENTER_ALIGNMENT);

        passName.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField.setMaximumSize(new Dimension(300, 30));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);

        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        frame.add(Box.createVerticalStrut(100));
        frame.add(label);
        frame.add(Box.createVerticalStrut(10));

        frame.add(userName);

        frame.add(textField);
        frame.add(Box.createVerticalStrut(10));
        frame.add(e_lable);

        frame.add(email);
        frame.add(Box.createVerticalStrut(10));
        frame.add(passName);
        frame.add(passwordField);
        frame.add(Box.createVerticalStrut(10));
        frame.add(submitButton);


        frame.getContentPane().setBackground(new Color(128, 128, 0));
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }


}
