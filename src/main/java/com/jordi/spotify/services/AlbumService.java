package com.jordi.spotify.services;

import com.jordi.spotify.exceptions.SpotifyException;
import com.jordi.spotify.json.album.AlbumRest;
import com.jordi.spotify.json.album.UserInputAlbumRest;
import com.jordi.spotify.json.album.AlbumRestWithSongs;

import java.util.List;

public interface AlbumService {
    List<AlbumRest> getAlbums() throws SpotifyException;

    AlbumRestWithSongs getAlbumById(Long id) throws SpotifyException;

    AlbumRest createAlbum(UserInputAlbumRest userInputAlbumRest) throws SpotifyException;

    AlbumRest updateAlbum(Long id, AlbumRest albumRest) throws SpotifyException;

    String deleteAlbum(Long id) throws SpotifyException;

}
