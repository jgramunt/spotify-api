package com.jordi.spotify.services;

import com.jordi.spotify.exceptions.SpotifyException;
import com.jordi.spotify.json.ArtistRest;

import java.util.List;

public interface ArtistService {

    List<ArtistRest> findAll() throws SpotifyException;

    ArtistRest findById(Long id) throws SpotifyException;
}
