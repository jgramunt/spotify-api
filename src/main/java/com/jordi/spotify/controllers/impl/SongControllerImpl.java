package com.jordi.spotify.controllers.impl;

import com.jordi.spotify.controllers.SongController;
import com.jordi.spotify.exceptions.SpotifyException;
import com.jordi.spotify.json.SongRest;
import com.jordi.spotify.responses.SpotifyResponse;
import com.jordi.spotify.services.SongService;
import com.jordi.spotify.utils.constants.CommonConstants;
import com.jordi.spotify.utils.constants.RestConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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

}
