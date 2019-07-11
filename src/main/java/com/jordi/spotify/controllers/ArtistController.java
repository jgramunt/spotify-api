package com.jordi.spotify.controllers;

import com.jordi.spotify.exceptions.SpotifyException;
import com.jordi.spotify.json.ArtistRest;
import com.jordi.spotify.json.artist.ArtistCreateRest;
import com.jordi.spotify.responses.SpotifyResponse;

import java.net.URISyntaxException;
import java.util.List;

public interface ArtistController {

    SpotifyResponse<List<ArtistRest>> getArtists() throws SpotifyException;

    SpotifyResponse<ArtistRest> getArtistById(Long id) throws SpotifyException;

    SpotifyResponse<ArtistRest> createArtist(ArtistCreateRest artistCreateRest) throws SpotifyException, URISyntaxException;

    SpotifyResponse<ArtistRest> updateArtist(Long id, ArtistRest updatedArtistInfo) throws SpotifyException;
}
