import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class SongManager implements SongManagerInterface {

    int[] releaseYears = null;
    Song[][] songs;

    public void setReleaseYears() {
        try {
            File file = new File("count-by-release.csv");
            Scanner fileScanner = new Scanner(file);

            //Skip first two lines
            fileScanner.nextLine();
            fileScanner.nextLine();

            //read and store release years
            int yearCount = 0;
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                int year = Integer.parseInt(line);
                releaseYears[yearCount] = year;
                yearCount++;
            }
            fileScanner.close();

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }


    }

    public void setSongs() {
        try {
            CSVReader songReader = new CSVReader(new FileReader("spotify-2023.csv"));
            List<String[]> allSongData = songReader.readAll();

            int numRows = releaseYears.length;
            int numColumns = allSongData.size() -1; //header row -1

            songs = new Song[numRows][numColumns];

            //Song array
            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numColumns; j++) {
                    String[] songInfo = allSongData.get(j + 1); //skip header row
                    String trackName = songInfo[0];
                    String artistName = songInfo[1];
                    String releasedYear = songInfo[3];
                    String releasedMonth = songInfo[4];
                    String releasedDay = songInfo[5];
                    String totalNumberOfStringsOnSpotify = songInfo[8];

                    songs[i][j] = new Song(trackName, artistName, releasedYear, releasedMonth, releasedDay, totalNumberOfStringsOnSpotify);
                }
            }
        } catch (IOException | CsvException e) {
            System.out.println("error: " + e.getMessage());

        }

    }

    @Override
    public int getYearCount() {
        return releaseYears.length;
    }

    @Override
    public int getSongCount(int yearIndex) {
        return songs[yearIndex].length;
    }

    @Override
    public int getSongCount() {
        return 0;
    }

    @Override
    public String getYearName(int yearIndex) {
        return null;
    }

    @Override
    public int getSongCount(String year) {
        return 0;
    }

    @Override
    public Song getSong(int yearIndex, int songIndex) {
        return null;
    }

    @Override
    public Song[] getSongs(int yearIndex) {
        return new Song[0];
    }

    @Override
    public int findSongYear(String trackName) {
        return 0;
    }
}
