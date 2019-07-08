package com.jordi.spotify.controllers;

import com.jordi.spotify.exceptions.SpotifyException;
import com.jordi.spotify.json.ArtistRest;
import com.jordi.spotify.responses.SpotifyResponse;

import java.util.List;

public interface ArtistController {

    SpotifyResponse<List<ArtistRest>> getArtists()throws SpotifyException;

    SpotifyResponse<ArtistRest> getArtistById(Long id) throws SpotifyException;
}
