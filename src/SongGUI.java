import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SongGUI extends JFrame {
    private SongManager songManager;
    private JComboBox<String> dropMenu;
    private JButton loadButton;
    private JButton nextButton;
    private JButton prevButton;
    private JLabel trackLabel;
    private JLabel artistLabel;
    private JLabel releaseLabel;
    private JLabel streamLabel;
    private JTextField trackTextField;
    private JTextField artistTextField;
    private JTextField releaseTextField;
    private JTextField streamTextField;
    private int currentYearIndex = -1;
    private int currentSongIndex = 0;




    public SongGUI() {
        super("Popular Songs of 2023");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);


        //instantiating song manager
        songManager = new SongManager();


        //padding
        int paddingX = 20;
        int paddingY = 30;
        int buttonWidth = 100;
        int buttonHeight = 30;

        //buttons
        loadButton = new JButton("Load Data");
        loadButton.setBounds(paddingX, paddingY, buttonWidth, buttonHeight);
        add(loadButton);

        prevButton = new JButton("Prev");
        prevButton.setBounds(100 + paddingX*2,paddingY, buttonWidth, buttonHeight);
        prevButton.setEnabled(false);
        add(prevButton);

        nextButton = new JButton("Next");
        nextButton.setBounds(200 + paddingX*3, paddingY, buttonWidth, buttonHeight);
        nextButton.setEnabled(false);
        add(nextButton);


        //dropdown menu
        dropMenu = new JComboBox<>();
        dropMenu.setBounds(paddingX,paddingY * 3,buttonWidth,buttonHeight);
        add(dropMenu);


        //adding labels
        trackLabel = new JLabel("Track Name:");
        trackLabel.setBounds(paddingX, 20 + paddingY * 4, buttonWidth, buttonHeight);
        add(trackLabel);

        artistLabel = new JLabel("Artist(s):");
        artistLabel.setBounds(paddingX, 30 + paddingY*5, buttonWidth, buttonHeight);
        add(artistLabel);

        releaseLabel = new JLabel("Release Date:");
        releaseLabel.setBounds(paddingX, 40 + paddingY * 6, buttonWidth, buttonHeight);
        add(releaseLabel);

        streamLabel = new JLabel("Total Streams:");
        streamLabel.setBounds(paddingX, 50 + paddingY * 7, buttonWidth, buttonHeight);
        add(streamLabel);


        //add text fields
        trackTextField = new JTextField();
        trackTextField.setBounds(110 + paddingX,20 + paddingY * 4, 230, buttonHeight);
        add(trackTextField);

        artistTextField = new JTextField();
        artistTextField.setBounds(110 + paddingX,30 + paddingY * 5, 230, buttonHeight);
        add(artistTextField);

        releaseTextField = new JTextField();
        releaseTextField.setBounds(110 + paddingX, 40 + paddingY * 6, 230, buttonHeight);
        add(releaseTextField);

        streamTextField = new JTextField();
        streamTextField.setBounds(110 + paddingX, 50 + paddingY * 7, 230, buttonHeight);
        add(streamTextField);



        //ActionListener for Load Data
        loadButton.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //load data
                songManager.populateSongs();

                //enable buttons
                prevButton.setEnabled(true);
                nextButton.setEnabled(true);

                //Populate combobox
                for (int i = 0; i < songManager.getYearCount(); i++) {
                dropMenu.addItem(songManager.getYearName(i));
                }
            }
        }));

        //Action listener for combo box
        dropMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = dropMenu.getSelectedIndex();
                if (selectedIndex != -1) {
                    currentYearIndex = selectedIndex;
                    currentSongIndex = 0;
                    displaySongInfo();


                }
            }
        });

        //Action Listener for prev button
        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentYearIndex != -1 && currentSongIndex > 0) {
                    currentSongIndex--;
                    displaySongInfo();
                }
            }
        });

        //Action Listener for next button
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentYearIndex != -1 && currentSongIndex < songManager.getSongCount(currentYearIndex) - 1) {
                    currentSongIndex++;
                    displaySongInfo();
                }
            }
        });




    }

    private void displaySongInfo() {
        if (currentYearIndex != -1) {
            Song song = songManager.getSong(currentYearIndex, currentSongIndex);
            if (song != null) {
                trackTextField.setText(song.getTrackName());
                artistTextField.setText(song.getArtistName());
                releaseTextField.setText(song.getReleasedDay());
                streamTextField.setText(song.getTotalNumberOfStreamsOnSpotify());
            }
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SongGUI gui = new SongGUI();
            gui.setVisible(true);
        });
    }
}
