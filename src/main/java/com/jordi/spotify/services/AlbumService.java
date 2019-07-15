package com.jordi.spotify.services;

import com.jordi.spotify.exceptions.SpotifyException;
import com.jordi.spotify.json.AlbumRest;
import com.jordi.spotify.json.album.AlbumCreateRest;

import java.util.List;

public interface AlbumService {
    List<AlbumRest> getAlbums() throws SpotifyException;

    AlbumRest getAlbumById(Long id) throws SpotifyException;

    AlbumRest createAlbum(AlbumCreateRest albumCreateRest) throws SpotifyException;

    AlbumRest updateAlbum(Long id, AlbumRest albumRest) throws SpotifyException;

    String deleteAlbum(Long id) throws SpotifyException;

}
