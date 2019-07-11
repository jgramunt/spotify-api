package com.jordi.spotify.services;

import com.jordi.spotify.exceptions.SpotifyException;
import com.jordi.spotify.json.AlbumRest;

import java.util.List;

public interface AlbumService {
    List<AlbumRest> getAll() throws SpotifyException;
}
