package com.jordi.spotify.json.album;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AlbumRestWithSongs implements Serializable {

    private static final long serialVersionUID = 756578343946110994L;

    private Long id;
    private String name;
    private List<AlbumSongRest> songs;

    public AlbumRestWithSongs() {}

    public AlbumRestWithSongs(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public List<AlbumSongRest> getSongs() { return songs; }

    public void setSongs(List<AlbumSongRest> songs) { this.songs = songs; }
}
