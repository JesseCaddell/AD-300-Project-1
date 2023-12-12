import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SongManager implements SongManagerInterface {

    int[] releaseYears;
    Song[][] songs;

    public void populateSongs() {
        try {
            //read data from count by release
            File countFile = new File("src/count-by-release-year.csv");
            Scanner countScanner = new Scanner(countFile);

            //read number of years
            int numYears = Integer.parseInt(countScanner.nextLine());
            releaseYears = new int[numYears];
            songs = new Song[numYears][];

            //Skip next line
            countScanner.nextLine();


            //Read and store release years and counts
            int yearCount = 0;
            while (countScanner.hasNextLine()) {
                String line = countScanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    int year = Integer.parseInt(parts[0].trim());
                    releaseYears[yearCount] = year;
                    int count = Integer.parseInt(parts[1].trim());
                    songs[yearCount] = new Song[count];
                    yearCount++;
                }
            }
            countScanner.close();


        } catch (IOException e) {
            System.err.println("Error reading count-by-release-year.csv: " +e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing numeric values: " + e.getMessage());
        }

        try {
            //Read data from Spotify2023.csv
            CSVReader songReader = new CSVReader(new FileReader("src/spotify-2023.csv"));
            List<String[]> allSongData = songReader.readAll();

            for (String[] songInfo : allSongData.subList(1, allSongData.size())) {
                int year = Integer.parseInt(songInfo[3]);
                int yearIndex = Arrays.binarySearch(releaseYears, year);
                if (yearIndex >= 0 ) {
                    String trackName = songInfo[0];
                    String artistName = songInfo[1];
                    String releasedYear = songInfo[3];
                    String releasedMonth = songInfo[4];
                    String releasedDay = songInfo[5];
                    String totalNumberOfStreamsOnSpotify = songInfo[8];

                    //Check for space
                    for (int songIndex = 0; songIndex < songs[yearIndex].length; songIndex++) {
                        if (songs[yearIndex][songIndex] == null) {
                            songs[yearIndex][songIndex] = new Song(trackName, artistName, releasedYear, releasedMonth, releasedDay, totalNumberOfStreamsOnSpotify);
                            break;
                        }
                    }
                }
            }
        } catch (IOException | CsvException e) {
            System.err.println("Error reading spotify-2023.csv: " + e.getMessage());
        }
    }




    @Override
    public int getYearCount() {
        return releaseYears.length;
    }

    @Override
    public int getSongCount(int yearIndex) {
        if (yearIndex >= 0 && yearIndex < releaseYears.length) {
            return songs[yearIndex].length;
        } else {
            return 0;
        }
    }

    @Override
    public int getSongCount() {
        int totalSongCount = 0;
        for (int i = 0; i < songs.length; i++) {
            totalSongCount += songs[i].length;
        }
        return totalSongCount;
    }

    @Override
    public String getYearName(int yearIndex) {
        if (yearIndex >= 0 && yearIndex < releaseYears.length) {
            return Integer.toString(releaseYears[yearIndex]);
        }
        return null;
    }

    @Override
    public int getSongCount(String year) {
        int yearIndex = -1;
        for (int i = 0; i < releaseYears.length; i++) {
            if (getYearName(i).equals(year)) {
                yearIndex = i;
                break;
            }
        }
        //if year was found
        if (yearIndex >= 0) {
            return songs[yearIndex].length;
        } else {
            return 0;
        }
    }

    @Override
    public Song getSong(int yearIndex, int songIndex) {
        if (yearIndex >= 0 && yearIndex < songs.length && songIndex >= 0 && songIndex < songs[yearIndex].length) {
            return songs[yearIndex][songIndex];
        }
        return null;
    }

    @Override
    public Song[] getSongs(int yearIndex) {
        if (yearIndex >= 0 && yearIndex < songs.length) {
            return songs[yearIndex];
        }
        return new Song[0]; //out of range indexes
    }


    @Override
    public int findSongYear(String trackName) {
        for (int i = 0; i < songs.length; i++) {
            for (int j = 0; j < songs[i].length; j++) {
                if (songs[i][j].getTrackName().equals(trackName)) {
                    return releaseYears[i];
                }
            }
        }
        return -1; //return -1 to show track was not found
    }
}
