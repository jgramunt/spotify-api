package com.jordi.spotify.controllers.impl;

import com.jordi.spotify.controllers.AlbumController;
import com.jordi.spotify.exceptions.SpotifyException;
import com.jordi.spotify.json.AlbumRest;
import com.jordi.spotify.json.album.AlbumCreateRest;
import com.jordi.spotify.responses.SpotifyResponse;
import com.jordi.spotify.services.AlbumService;
import com.jordi.spotify.utils.constants.CommonConstants;
import com.jordi.spotify.utils.constants.RestConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(RestConstants.APPLICATION_NAME + RestConstants.API_VERSION_1 + RestConstants.RESOURCE_ALBUMS)
public class AlbumControllerImpl implements AlbumController {

    @Autowired
    private AlbumService albumService;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public SpotifyResponse<List<AlbumRest>> getAlbums() throws SpotifyException {
        return new SpotifyResponse<>(CommonConstants.OK, String.valueOf(HttpStatus.OK.value()), CommonConstants.SUCCESS,
                albumService.getAlbums());
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = RestConstants.RESOURCE_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public SpotifyResponse<AlbumRest> getAlbumById(@PathVariable Long id) throws SpotifyException {
        return new SpotifyResponse<>(CommonConstants.OK, String.valueOf(HttpStatus.OK.value()), CommonConstants.SUCCESS,
                albumService.getAlbumById(id));
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public SpotifyResponse<AlbumRest> createAlbum(@RequestBody @Valid AlbumCreateRest albumCreateRest) throws SpotifyException {
        return new SpotifyResponse<>(CommonConstants.CREATED, String.valueOf(HttpStatus.CREATED.value()), CommonConstants.SUCCESS,
                albumService.createAlbum(albumCreateRest));
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(value = RestConstants.RESOURCE_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public SpotifyResponse<AlbumRest> updateAlbum(@PathVariable Long id, @RequestBody @Valid AlbumRest albumRest) throws SpotifyException {
        return new SpotifyResponse<>(CommonConstants.OK, String.valueOf(HttpStatus.OK.value()), CommonConstants.SUCCESS,
                albumService.updateAlbum(id, albumRest));
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = RestConstants.RESOURCE_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public SpotifyResponse<String> deleteAlbum(@PathVariable Long id) throws SpotifyException {
        return new SpotifyResponse<>(CommonConstants.NO_CONTENT, String.valueOf(HttpStatus.NO_CONTENT.value()),
                albumService.deleteAlbum(id));
    }

}
