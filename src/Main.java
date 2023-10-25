public class Main {
    public static void main(String[] args) {
        SongManager songManager = new SongManager();

        songManager.setReleaseYears();
        songManager.setSongs();

        System.out.println(songManager.getSongCount(42));


    }
}