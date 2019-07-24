package com.jordi.spotify.controllers;

import com.jordi.spotify.exceptions.SpotifyException;
import com.jordi.spotify.json.AlbumRest;
import com.jordi.spotify.json.album.AlbumCreateRest;
import com.jordi.spotify.json.album.AlbumRestWithSongs;
import com.jordi.spotify.responses.SpotifyResponse;

import java.util.List;

public interface AlbumController {

    SpotifyResponse<List<AlbumRest>> getAlbums() throws SpotifyException;

    SpotifyResponse<AlbumRestWithSongs> getAlbumById(Long id) throws SpotifyException;

    SpotifyResponse<AlbumRest> createAlbum(AlbumCreateRest albumCreateRest) throws SpotifyException;

    SpotifyResponse<AlbumRest> updateAlbum(Long id, AlbumRest albumRest) throws SpotifyException;

    SpotifyResponse<String> deleteAlbum(Long id) throws SpotifyException;
}
