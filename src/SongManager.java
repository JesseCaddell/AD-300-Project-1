import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;

public class SongManager {

    public void songReader() {
        try {
            CSVReader songReader = new CSVReader(new FileReader("spotify-2023.csv"));
            for (int i=0; i<1; i++) {
                for (int j=0; j<1; j++) {
                    String[] songInfo = songReader.readNext();
                    System.out.println(songInfo[0]);
                }
            }
        } catch (IOException | CsvValidationException e) {
            System.out.println("error");

        }

    }

}
