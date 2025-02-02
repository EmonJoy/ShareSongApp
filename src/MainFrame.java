import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.sound.sampled.*;

public class MainFrame {
    private String loginUser;
    public JPanel centerPanel;
    Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    int width = (int) size.getWidth();
    int height = (int) size.getHeight();

    JButton admin = new JButton("Profile");

    private Clip currentClip = null;
    private long clipTimePosition = 0;

    MainFrame(String loginUser) {
        this.loginUser = loginUser;

        JFrame frame = new JFrame("Music Share");
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome, " + loginUser + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        JPanel topPanel = new JPanel();
        topPanel.add(welcomeLabel);
        frame.add(topPanel, BorderLayout.NORTH);

        centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(centerPanel);
        frame.add(scrollPane, BorderLayout.CENTER);

        addDemoPosts();

        JButton uploadButton = new JButton("Upload Song");
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(uploadButton);
        bottomPanel.add(admin);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        admin.setFocusable(false);
        admin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Admin a = new Admin();
            }
        });

        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Select a Song File");
                fileChooser.setFileFilter(new FileNameExtensionFilter("Audio Files", "mp3", "wav"));
                int result = fileChooser.showOpenDialog(frame);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String fileName = selectedFile.getName();

                    addSongPost(fileName, loginUser, selectedFile);
                    try {
                        BufferedWriter r = new BufferedWriter(new FileWriter("Post.txt", true));
                        File f = new File("Post.txt");
                        if (f.length() == 0) {
                            r.write("\t\tYour Post\t\t\n");
                            r.newLine();
                        }
                        r.write("=====> " + fileName + " " + loginUser + " " + songTitle);
                        r.newLine();
                        r.close();
                    } catch (IOException ae) {
                        throw new RuntimeException(ae);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "No file selected.");
                }
            }
        });

        frame.setVisible(true);
    }

    String songTitle;
    public void addSongPost(String songTitle, String userName, File songFile) {
        this.songTitle = songTitle;
        JPanel songPost = new JPanel();
        songPost.setLayout(new BoxLayout(songPost, BoxLayout.Y_AXIS));

        JPanel songInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        songInfo.add(new JLabel("Song Title: " + songTitle));
        songInfo.add(new JLabel("Posted by: " + userName));

        songPost.add(songInfo);

        ImageIcon like = new ImageIcon(getClass().getResource("like.png"));
        Image img = like.getImage();
        Image resizedImg = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImg);

        JPanel songControls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton likeButton = new JButton("Like (0)", resizedIcon);
        likeButton.setFocusable(false);

        final boolean[] isLiked = {false};
        final int[] likeCount = {0};

        likeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isLiked[0]) {
                    likeCount[0]--;
                    likeButton.setText("Like (" + likeCount[0] + ")");
                } else {
                    likeCount[0]++;
                    likeButton.setText("Unlike (" + likeCount[0] + ")");
                }
                isLiked[0] = !isLiked[0];
            }
        });

        ImageIcon pause = new ImageIcon(getClass().getResource("pause.png"));
        Image img1 = pause.getImage();
        Image resizedImg1 = img1.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon1 = new ImageIcon(resizedImg1);

        ImageIcon play = new ImageIcon(getClass().getResource("play.png"));
        Image img2 = pause.getImage();
        Image resizedImg2 = img2.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon2 = new ImageIcon(resizedImg2);

        JButton playButton = new JButton("Play", resizedIcon2);
        JButton pauseButton = new JButton("Pause", resizedIcon1);

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playMusic(songFile);
            }
        });

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pauseMusic();
            }
        });

        songControls.add(playButton);
        songControls.add(pauseButton);
        songControls.add(likeButton);

        songPost.add(songControls);
        centerPanel.add(songPost);
        centerPanel.revalidate();
        centerPanel.repaint();
    }

    private void playMusic(File songFile) {
        try {
            if (currentClip != null && currentClip.isRunning()) {
                currentClip.stop();
            }

            if (currentClip == null || !currentClip.isOpen()) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(songFile);
                currentClip = AudioSystem.getClip();
                currentClip.open(audioStream);
            }

            currentClip.setMicrosecondPosition(clipTimePosition);
            currentClip.start();

            JOptionPane.showMessageDialog(null, "Now playing: " + songFile.getName());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error playing song: " + e.getMessage());
        }
    }

    private void pauseMusic() {
        if (currentClip != null && currentClip.isRunning()) {
            clipTimePosition = currentClip.getMicrosecondPosition();
            currentClip.stop();
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
