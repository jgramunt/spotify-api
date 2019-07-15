package com.jordi.spotify.controllers;

import com.jordi.spotify.exceptions.SpotifyException;
import com.jordi.spotify.json.AlbumRest;
import com.jordi.spotify.json.album.AlbumCreateRest;
import com.jordi.spotify.responses.SpotifyResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

public interface AlbumController {

    SpotifyResponse<List<AlbumRest>> getAlbums() throws SpotifyException;

    SpotifyResponse<AlbumRest> getAlbumById(Long id) throws SpotifyException;

    SpotifyResponse<AlbumRest> createAlbum(AlbumCreateRest albumCreateRest) throws SpotifyException;

    SpotifyResponse<AlbumRest> updateAlbum(Long id, AlbumRest albumRest) throws SpotifyException;

    SpotifyResponse<String> deleteAlbum(Long id) throws SpotifyException;
}
