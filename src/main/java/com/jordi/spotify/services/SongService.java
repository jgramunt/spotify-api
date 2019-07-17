package com.jordi.spotify.services;

import com.jordi.spotify.exceptions.SpotifyException;
import com.jordi.spotify.json.SongRest;
import com.jordi.spotify.json.song.CreateSongRest;

import java.util.List;

public interface SongService {

    List<SongRest> getSongs() throws SpotifyException;

    SongRest getSongById(Long id) throws SpotifyException;

    SongRest createSong(CreateSongRest createSongRest) throws SpotifyException;

    SongRest updateSong(Long id, CreateSongRest createSongRest) throws SpotifyException;

}
