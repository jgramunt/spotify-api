package com.jordi.spotify.controllers;

import com.jordi.spotify.exceptions.SpotifyException;
import com.jordi.spotify.json.song.SongRest;
import com.jordi.spotify.json.song.UserInputSongRest;
import com.jordi.spotify.responses.SpotifyResponse;

import java.util.List;

public interface SongController {

    SpotifyResponse<List<SongRest>> getSongs() throws SpotifyException;

    SpotifyResponse<SongRest> getSongById(Long id) throws SpotifyException;

    SpotifyResponse<SongRest> createSong(UserInputSongRest userInputSongRest) throws SpotifyException;

    SpotifyResponse<SongRest> updateSong(Long id, UserInputSongRest userInputSongRest) throws SpotifyException;

    SpotifyResponse<String> deleteSong(Long id) throws SpotifyException;

}
