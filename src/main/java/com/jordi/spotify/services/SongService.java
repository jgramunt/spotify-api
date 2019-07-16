package com.jordi.spotify.services;

import com.jordi.spotify.exceptions.SpotifyException;
import com.jordi.spotify.json.SongRest;

import java.util.List;

public interface SongService {

    List<SongRest> getSongs() throws SpotifyException;

    SongRest getSongById(Long id) throws SpotifyException;

}
