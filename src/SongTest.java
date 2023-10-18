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

}