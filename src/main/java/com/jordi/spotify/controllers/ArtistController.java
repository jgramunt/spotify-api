package com.jordi.spotify.controllers;

import com.jordi.spotify.exceptions.SpotifyException;
import com.jordi.spotify.json.artist.ArtistRest;
import com.jordi.spotify.json.artist.UserInputArtistRest;
import com.jordi.spotify.responses.SpotifyResponse;

import java.net.URISyntaxException;
import java.util.List;

public interface ArtistController {

    SpotifyResponse<List<ArtistRest>> getArtists() throws SpotifyException;

    SpotifyResponse<ArtistRest> getArtistById(Long id) throws SpotifyException;

    SpotifyResponse<ArtistRest> createArtist(UserInputArtistRest userInputArtistRest) throws SpotifyException, URISyntaxException;

    SpotifyResponse<ArtistRest> updateArtist(Long id, ArtistRest updatedArtistInfo) throws SpotifyException;

    SpotifyResponse<String> deleteArtist(Long id) throws SpotifyException;
}
