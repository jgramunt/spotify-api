package com.jordi.spotify.json;

import java.io.Serializable;

public class SongRest implements Serializable {

    private static final long serialVersionUID = -3172330637956838711L;

    private Long id;
    private String name;
    private String artistName;
    private String albumName;

    public SongRest() {}

    public SongRest(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }
}
