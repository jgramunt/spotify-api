package com.jordi.spotify.controllers;

import com.jordi.spotify.exceptions.SpotifyException;
import com.jordi.spotify.json.AlbumRest;
import com.jordi.spotify.responses.SpotifyResponse;

import java.util.List;

public interface AlbumController {

    SpotifyResponse<List<AlbumRest>> getAll() throws SpotifyException;

    SpotifyResponse<AlbumRest> getById(Long id) throws SpotifyException;

}
