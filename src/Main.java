public class Main {
    public static void main(String[] args) {
        SongManager songManager = new SongManager();

        songManager.populateSongs();

        System.out.println(songManager.getSongCount(5));


    }
}