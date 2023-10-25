/**
 * @param trackName accepts String
 * @param artistName accepts String
 * @param releasedYear accepts String
 * @param releasedMonth accepts String
 * @param releasedDay accepts String
 * @param totalNumberOfStreamsOnSpotify
 */
public record Song(String trackName, String artistName, String releasedYear, String releasedMonth, String releasedDay, String totalNumberOfStreamsOnSpotify)
        implements Comparable<Song> {

    public Song {
        if (trackName == null || artistName == null || releasedYear == null || releasedMonth == null || releasedDay == null || totalNumberOfStreamsOnSpotify == null)
            throw new NullPointerException("Error: Data is null");
        if (trackName.isEmpty() || artistName.isEmpty() || releasedYear.isEmpty() || releasedMonth.isEmpty() || releasedDay.isEmpty() || totalNumberOfStreamsOnSpotify.isEmpty())
            throw new IllegalArgumentException("Data cannot be blank");
    }
    public String getTrackName() {
        return trackName;
    }
    public String getArtistName() {
        return artistName;
    }
    public String getReleasedYear() {
        return releasedYear;
    }
    public String getReleasedMonth() {
        return releasedMonth;
    }
    public String getReleasedDay() {
        return releasedDay;
    }
    public String getTotalNumberOfStreamsOnSpotify() {
        return totalNumberOfStreamsOnSpotify;
    }

    //override of equals method
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return trackName.equals(song.trackName) && artistName.equals(song.artistName) && releasedYear.equals(song.releasedYear) && releasedMonth.equals(song.releasedMonth) && releasedDay.equals(song.releasedDay) && totalNumberOfStreamsOnSpotify.equals(song.totalNumberOfStreamsOnSpotify);
    }

    //compareTo implementing natural ordering. Require implements comparable<Song>
    @Override
    public int compareTo(Song otherSong) {
        return this.trackName.compareTo(otherSong.trackName);
    }
    @Override
    public String toString() {
        return String.format("Track: %s, Artist: %s, Year: %s/%s/%s, Streams on Spotify: %s",
                trackName, artistName, releasedYear, releasedMonth, releasedDay, totalNumberOfStreamsOnSpotify);
    }

}
