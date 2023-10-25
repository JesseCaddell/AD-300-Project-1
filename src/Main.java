import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        SongManager songManager = new SongManager();

        songManager.populateSongs();


//        System.out.println(songManager.getSongCount());
//        System.out.println(songManager.getSongCount("2012"));
//        System.out.println(songManager.getSongCount(6));
        System.out.println(songManager.getSong(3, 0));
        System.out.println(Arrays.toString(songManager.getSongs(19)));
//        System.out.println(songManager.getYearName(5));
//        System.out.println(songManager.getYearCount());
//        System.out.println(songManager.findSongYear("Driving home for christmas - 2019 remaster"));



    }
}