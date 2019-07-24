package com.jordi.spotify.json.album;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AlbumSongRest implements Serializable {
    private static final long serialVersionUID = -8131979804959569781L;

    private Integer trackNumber;
    private String name;
    private String artistName;
    private Long id;

    public AlbumSongRest() {}

    public AlbumSongRest(String name, String artistName) {
        this.name = name;
        this.artistName = artistName;
    }

    public Integer getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(Integer trackNumber) {
        this.trackNumber = trackNumber;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
