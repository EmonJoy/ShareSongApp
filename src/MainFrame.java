import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.sound.sampled.*;

public class MainFrame  {
    private String loginUser; // Stores the logged-in username
    public JPanel centerPanel; // Panel to display song posts
    Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    int width = (int) size.getWidth();
    int height = (int) size.getHeight();

    JButton admin = new JButton("Profile");

    private Clip currentClip = null; // Stores the currently playing clip
    private long clipTimePosition = 0; // Stores the paused position

    MainFrame(String loginUser) {
        this.loginUser = loginUser;

        // Create the main frame
        JFrame frame = new JFrame("Music Share");
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Top Panel - Welcome message
        JLabel welcomeLabel = new JLabel("Welcome, " + loginUser + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        JPanel topPanel = new JPanel();
        topPanel.add(welcomeLabel);
        frame.add(topPanel, BorderLayout.NORTH);

        // Center Panel - Song list
        centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(centerPanel);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Add demo song posts (as if posted by other users)
        addDemoPosts();

        // Bottom Panel - Upload button
        JButton uploadButton = new JButton("Upload Song");
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(uploadButton);
        bottomPanel.add(admin); // Admin Btn
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Admin Button -->
        admin.setFocusable(false);
        admin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Admin a = new Admin();

            }
        });



        // Action for Upload Button
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open file chooser to select a song
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Select a Song File");
                fileChooser.setFileFilter(new FileNameExtensionFilter("Audio Files", "mp3", "wav"));
                int result = fileChooser.showOpenDialog(frame);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String fileName = selectedFile.getName();

                    // Create a new song post
                    addSongPost(fileName, loginUser, selectedFile);
                } else {
                    JOptionPane.showMessageDialog(frame, "No file selected.");
                }
            }
        });

        // Make the frame visible
        frame.setVisible(true);
    }

    // Method to add a new song post to the list
    private void addSongPost(String songTitle, String userName, File songFile) {
        JPanel songPost = new JPanel();
        songPost.setLayout(new BoxLayout(songPost, BoxLayout.Y_AXIS));

        // Song Information
        JPanel songInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        songInfo.add(new JLabel("Song Title: " + songTitle));
        songInfo.add(new JLabel("Posted by: " + userName));
        songPost.add(songInfo);

        // Like Button and Counter
        ImageIcon like = new ImageIcon(getClass().getResource("like.png"));

// Resize the image
        Image img = like.getImage();
        Image resizedImg = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH); // Resize to 20x20
        ImageIcon resizedIcon = new ImageIcon(resizedImg);

// Use the resized image for the button
        JPanel songControls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton likeButton = new JButton("Like (0)", resizedIcon);
        likeButton.setFocusable(false);

        final boolean[] isLiked = {false}; // Track if liked or not
        final int[] likeCount = {0}; // Track like count

        likeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isLiked[0]) {
                    // If already liked, unlike it
                    likeCount[0]--; // Decrement like count
                    likeButton.setText("Like (" + likeCount[0] + ")");
                } else {
                    // If not liked, like it
                    likeCount[0]++; // Increment like count
                    likeButton.setText("Unlike (" + likeCount[0] + ")");
                }
                isLiked[0] = !isLiked[0]; // Toggle the like state
            }
        });


        ImageIcon pause = new ImageIcon(getClass().getResource("pause.png"));

// Resize the image
        Image img1 = pause.getImage();
        Image resizedImg1 = img1.getScaledInstance(20, 20, Image.SCALE_SMOOTH); // Resize to 20x20
        ImageIcon resizedIcon1 = new ImageIcon(resizedImg1);


        ImageIcon play = new ImageIcon(getClass().getResource("play.png"));

// Resize the image
        Image img2 = pause.getImage();
        Image resizedImg2 = img2.getScaledInstance(20, 20, Image.SCALE_SMOOTH); // Resize to 20x20
        ImageIcon resizedIcon2 = new ImageIcon(resizedImg2);

        // Play Button
        JButton playButton = new JButton("Play",resizedIcon2);
        JButton pauseButton = new JButton("Pause",resizedIcon1); // Pause button


        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Play the selected music
                playMusic(songFile);
            }
        });

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Pause the selected music
                pauseMusic();
            }
        });

        songControls.add(playButton);
        songControls.add(pauseButton); // Add the Pause button
        songControls.add(likeButton);

        // Add buttons and song info to the post
        songPost.add(songControls);
        centerPanel.add(songPost);
        centerPanel.revalidate(); // Refresh the panel to display the new post
        centerPanel.repaint();
    }

    // Method to play the selected music
    private void playMusic(File songFile) {
        try {
            if (currentClip != null && currentClip.isRunning()) {
                currentClip.stop(); // Stop any currently playing clip
            }

            // Open the audio file as a Clip
            if (currentClip == null || !currentClip.isOpen()) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(songFile);
                currentClip = AudioSystem.getClip();
                currentClip.open(audioStream);
            }

            currentClip.setMicrosecondPosition(clipTimePosition); // Resume from the paused position
            currentClip.start();

            JOptionPane.showMessageDialog(null, "Now playing: " + songFile.getName());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error playing song: " + e.getMessage());
        }
    }


    private void pauseMusic() {
        if (currentClip != null && currentClip.isRunning()) {
            clipTimePosition = currentClip.getMicrosecondPosition(); // Save the current position
            currentClip.stop(); // Pause the clip
            JOptionPane.showMessageDialog(null, "Music paused.");
        } else {
            JOptionPane.showMessageDialog(null, "No music is currently playing.");
        }
    }


    private void addDemoPosts() {

        addSongPost("Song Battamiz", "Nazia", new File("Battamiz.wav"));
        addSongPost("Song Teri chunariyaa", "Zihad Sikder", new File("Teri chunariyaa.wav"));
        addSongPost("Song Barsaaateen", "Tushar6204", new File("Barsaaateen.mp3"));
        addSongPost("Song Barsaaateen", "Emon Joy", new File("Love.mp3"));
        addSongPost("Song Barsaaateen", "Asif Chowdhury", new File("Despacito.mp3"));
        addSongPost("Song Barsaaateen", "Asif Chowdhury", new File("Despacito.mp3"));
        addSongPost("Song Barsaaateen", "Asif Chowdhury", new File("Despacito.mp3"));
    }


}
