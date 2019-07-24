package com.jordi.spotify.json.song;

import java.io.Serializable;

public class CreateSongRest implements Serializable {

    private static final long serialVersionUID = 7939466948855144972L;

    private String name;
    private Long artistId;
    private Long albumId;
    private Integer trackNumber;

    public CreateSongRest() {}

    public CreateSongRest(String name) { this.name = name; }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public Integer getTrackNumber() { return trackNumber; }

    public void setTrackNumber(Integer trackNumber) { this.trackNumber = trackNumber; }
}
