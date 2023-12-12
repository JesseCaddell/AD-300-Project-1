import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;



class SongTest {

    private final SongManager songManager;

    public SongTest(SongManager songManager) {
        this.songManager = songManager;
    }

    public static void main(String[] args) {
        int[] testCases = {10, 50, 1000, 50000};

        // Prepare the table header
        System.out.printf("%-15s%-15s%-15s\n", "File Size", "Execution Time (ms)", "Memory Usage (MB)");
        System.out.println("-------------------------------------------");

        for (int fileSize : testCases) {
            System.out.println("Running for File Size = " + fileSize);

            // Measure execution time and memory usage for populateSongs
            long startTime = System.currentTimeMillis();
            SongManager songManager = populateSongsAndMeasureMemory(fileSize);
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;

            // Print results
            System.out.printf("%-15d%-15d%-15d\n", fileSize, executionTime, getUsedMemory());

            System.out.println("---------------------------");
        }
    }

    private static SongManager populateSongsAndMeasureMemory(int fileSize) {
        SongManager songManager = new SongManager();
        long memoryBefore = getUsedMemory();

        songManager.populateSongs();

        long memoryAfter = getUsedMemory();
        System.out.println("Memory used during populateSongs: " + (memoryAfter - memoryBefore) + " MB");

        return songManager;
    }

    private static long getUsedMemory() {
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryBean.getHeapMemoryUsage();

        return heapMemoryUsage.getUsed() / (1024 * 1024); // Convert to MB
    }


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