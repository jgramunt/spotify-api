package com.jordi.spotify.controllers.impl;

import com.jordi.spotify.controllers.ArtistController;
import com.jordi.spotify.exceptions.SpotifyException;
import com.jordi.spotify.json.ArtistRest;
import com.jordi.spotify.json.artist.ArtistCreateRest;
import com.jordi.spotify.responses.SpotifyResponse;
import com.jordi.spotify.services.ArtistService;
import com.jordi.spotify.utils.constants.CommonConstants;
import com.jordi.spotify.utils.constants.RestConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(RestConstants.APPLICATION_NAME + RestConstants.API_VERSION_1 + RestConstants.RESOURCE_ARTISTS)

public class ArtistControllerImpl implements ArtistController {

    @Autowired
    private ArtistService artistService;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public SpotifyResponse<List<ArtistRest>> getArtists() throws SpotifyException {
        return new SpotifyResponse<>(CommonConstants.SUCCESS, String.valueOf(HttpStatus.OK), CommonConstants.OK,
                artistService.findAll());
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = RestConstants.RESOURCE_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public SpotifyResponse<ArtistRest> getArtistById(@PathVariable Long id) throws SpotifyException {
        return new SpotifyResponse<>(CommonConstants.SUCCESS, String.valueOf(HttpStatus.OK), CommonConstants.OK,
                artistService.findById(id));
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public SpotifyResponse<ArtistRest> createArtist(@RequestHeader @RequestBody @Valid ArtistCreateRest artistCreateRest) throws SpotifyException {
        return new SpotifyResponse<>(CommonConstants.SUCCESS, String.valueOf(HttpStatus.OK), CommonConstants.OK,
                artistService.createArtist(artistCreateRest));
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(value = RestConstants.RESOURCE_ID)
    public SpotifyResponse<ArtistRest> updateArtist(@PathVariable Long id, @RequestBody ArtistRest updatedArtistInfo) throws SpotifyException {
        return new SpotifyResponse<>(CommonConstants.SUCCESS, String.valueOf(HttpStatus.OK), CommonConstants.OK,
                artistService.updateArtist(id, updatedArtistInfo));
    }

}
