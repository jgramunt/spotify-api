package com.jordi.spotify.services;

import com.jordi.spotify.exceptions.SpotifyException;
import com.jordi.spotify.json.song.SongRest;
import com.jordi.spotify.json.song.UserInputSongRest;

import java.util.List;

public interface SongService {

    List<SongRest> getSongs() throws SpotifyException;

    SongRest getSongById(Long id) throws SpotifyException;

    SongRest createSong(UserInputSongRest userInputSongRest) throws SpotifyException;

    SongRest updateSong(Long id, UserInputSongRest userInputSongRest) throws SpotifyException;

    String deleteSong(Long id) throws SpotifyException;

}
