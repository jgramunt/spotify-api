package com.jordi.spotify.controllers;

import com.jordi.spotify.exceptions.SpotifyException;
import com.jordi.spotify.json.SongRest;
import com.jordi.spotify.json.song.CreateSongRest;
import com.jordi.spotify.responses.SpotifyResponse;

import java.util.List;

public interface SongController {

    SpotifyResponse<List<SongRest>> getSongs() throws SpotifyException;

    SpotifyResponse<SongRest> getSongById(Long id) throws SpotifyException;

    SpotifyResponse<SongRest> createSong(CreateSongRest createSongRest) throws SpotifyException;

    SpotifyResponse<SongRest> updateSong(Long id, CreateSongRest createSongRest) throws SpotifyException;

    SpotifyResponse<String> deleteSong(Long id) throws SpotifyException;

}
