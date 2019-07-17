package com.jordi.spotify.controllers.impl;

import com.jordi.spotify.controllers.SongController;
import com.jordi.spotify.exceptions.SpotifyException;
import com.jordi.spotify.json.SongRest;
import com.jordi.spotify.json.song.CreateSongRest;
import com.jordi.spotify.responses.SpotifyResponse;
import com.jordi.spotify.services.SongService;
import com.jordi.spotify.utils.constants.CommonConstants;
import com.jordi.spotify.utils.constants.RestConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping(RestConstants.APPLICATION_NAME + RestConstants.API_VERSION_1 + RestConstants.RESOURCE_SONGS)
public class SongControllerImpl implements SongController {

    @Autowired
    private SongService songService;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public SpotifyResponse<List<SongRest>> getSongs() throws SpotifyException {
        return new SpotifyResponse<>(CommonConstants.OK, String.valueOf(HttpStatus.OK.value()), CommonConstants.SUCCESS,
                songService.getSongs());
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = RestConstants.RESOURCE_ID)
    public SpotifyResponse<SongRest> getSongById(@PathVariable Long id) throws SpotifyException {
        return new SpotifyResponse<>(CommonConstants.OK, String.valueOf(HttpStatus.OK.value()), CommonConstants.SUCCESS,
                songService.getSongById(id));
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public SpotifyResponse<SongRest> createSong(@RequestBody CreateSongRest createSongRest) throws SpotifyException {
        return new SpotifyResponse<>(CommonConstants.CREATED, String.valueOf(HttpStatus.CREATED.value()), CommonConstants.SUCCESS,
                songService.createSong(createSongRest));
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(value = RestConstants.RESOURCE_ID)
    public SpotifyResponse<SongRest> updateSong(@PathVariable Long id, @RequestBody CreateSongRest createSongRest) throws SpotifyException {
        return new SpotifyResponse<>(CommonConstants.OK, String.valueOf(HttpStatus.OK.value()), CommonConstants.SUCCESS,
                songService.updateSong(id, createSongRest));
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = RestConstants.RESOURCE_ID)
    public SpotifyResponse<String> deleteSong(@PathVariable Long id) throws SpotifyException {
        return new SpotifyResponse<>(CommonConstants.NO_CONTENT, String.valueOf(HttpStatus.NO_CONTENT.value()),
                songService.deleteSong(id));
    }

}
