package com.jordi.spotify.services;

import com.jordi.spotify.exceptions.SpotifyException;
import com.jordi.spotify.json.artist.ArtistRest;
import com.jordi.spotify.json.artist.UserInputArtistRest;

import java.util.List;

public interface ArtistService {

    List<ArtistRest> findAll() throws SpotifyException;

    ArtistRest findById(Long id) throws SpotifyException;

    ArtistRest createArtist(UserInputArtistRest createdArtist) throws SpotifyException;

    ArtistRest updateArtist(Long id, ArtistRest updatedArtistInfoRest) throws SpotifyException;

    String deleteArtist(Long id) throws SpotifyException;

}
