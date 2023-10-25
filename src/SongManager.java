import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SongManager implements SongManagerInterface {

    int[] releaseYears;
    Song[][] songs;

    public void setReleaseYears() {
        try {
            File file = new File("src/count-by-release-year.csv");
            Scanner fileScanner = new Scanner(file);

            //read number of years
            int numYears = Integer.parseInt(fileScanner.nextLine());
            releaseYears = new int[numYears];
            int[] counts = new int[numYears];

            //Skip next line lines
            fileScanner.nextLine();

            //read and store release years
            int yearCount = 0;
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                //Split by comma
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    int year = Integer.parseInt(parts[0].trim());
                    int count = Integer.parseInt(parts[1].trim());
                    releaseYears[yearCount] = year;
                    counts[yearCount] = count;
                    yearCount++;
                }

            }
            fileScanner.close();

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }


    }

    public void setSongs() {
        try {
            CSVReader songReader = new CSVReader(new FileReader("src/spotify-2023.csv"));
            List<String[]> allSongData = songReader.readAll();


            //Song array
            for (String[] songInfo : allSongData.subList(1, allSongData.size())) {
                int year = Integer.parseInt(songInfo[3]);
                int yearIndex = Arrays.binarySearch(releaseYears, year);
                if (yearIndex >= 0) {
                    String trackName = songInfo[0];
                    String artistName = songInfo[1];
                    String releasedYear = songInfo[3];
                    String releasedMonth = songInfo[4];
                    String releasedDay = songInfo[5];
                    String totalNumberOfStringsOnSpotify = songInfo[8];
//    WTF AM I DOING WRONG HERE. This seems to be the problem area. Tried many different things. Maybe I am not just doing
                    //the basic plus 1 to song index?? Come back here after break
                    if (songs[yearIndex] == null) {
                        songs[yearIndex] = new Song[1];
                    } else {
                        int songCount = getSongCount(yearIndex);
                        if (songCount == songs[yearIndex].length) {
                            songs[yearIndex] = Arrays.copyOf(songs[yearIndex], songCount * 2);
                        }
                    }

                    int songCount = getSongCount(yearIndex);
                    songs[yearIndex][songCount] = new Song(trackName, artistName, releasedYear, releasedMonth, releasedDay, totalNumberOfStringsOnSpotify);
                }
            }
//            for (int i = 0; i < numRows; i++) {
//                for (int j = 0; j < numColumns; j++) {
//                    String[] songInfo = allSongData.get(j + 1); //skip header row
//                    String trackName = songInfo[0];
//                    String artistName = songInfo[1];
//                    String releasedYear = songInfo[3];
//                    String releasedMonth = songInfo[4];
//                    String releasedDay = songInfo[5];
//                    String totalNumberOfStringsOnSpotify = songInfo[8];
//
//                    songs[i][j] = new Song(trackName, artistName, releasedYear, releasedMonth, releasedDay, totalNumberOfStringsOnSpotify);
//                    //System.out.println(songs[i][j]); //for testing purposes
//                }
//            }
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
        if (yearIndex >= 0 && yearIndex < releaseYears.length) {
            int songCount = songs[yearIndex].length;
            return songCount;
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
