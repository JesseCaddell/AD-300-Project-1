import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SongTest {

    @Test
    public void constructor() {
        Song testSong = new Song("Hello", "Frank", "1989", "May", "12th", "30");
        assertEquals("Hello",testSong.trackName());
        assertEquals("Frank",testSong.artistName());
        assertEquals("1989", testSong.releasedYear());
        assertEquals("May", testSong.releasedMonth());
        assertEquals("12th",testSong.releasedDay());
        assertEquals("30",testSong.totalNumberOfStreamsOnSpotify());
    }

    @Test
    public void equals() {
        Song testSong = new Song("Hello", "ArtistA", "1989", "May", "12th","30");
        Song testSong2 = new Song("Hello", "ArtistA", "1989", "May", "12th","30");

        assertTrue(testSong.equals(testSong2));
            }

    @Test
    public void compareTo() {
        Song testSong = new Song("TrackA", "ArtistA", "1989", "May", "12th", "30");
        Song testSong2 = new Song("TrackB", "ArtistB", "1985", "April", "13th", "20");

        int result = testSong.compareTo(testSong2);
        assertTrue(result < 0);

    }

}