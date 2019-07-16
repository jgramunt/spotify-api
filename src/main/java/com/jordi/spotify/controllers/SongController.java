package com.jordi.spotify.controllers;

import com.jordi.spotify.exceptions.SpotifyException;
import com.jordi.spotify.json.SongRest;
import com.jordi.spotify.responses.SpotifyResponse;

import java.util.List;

public interface SongController {

    SpotifyResponse<List<SongRest>> getSongs() throws SpotifyException;

    SpotifyResponse<SongRest> getSongById(Long id) throws SpotifyException;
}
