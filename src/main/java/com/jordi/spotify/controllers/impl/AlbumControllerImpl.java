package com.jordi.spotify.controllers.impl;

import com.jordi.spotify.services.AlbumService;
import com.jordi.spotify.services.ArtistService;
import com.jordi.spotify.utils.constants.RestConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RestConstants.APPLICATION_NAME + RestConstants.API_VERSION_1 + RestConstants.RESOURCE_ALBUMS)
public class AlbumControllerImpl {

    @Autowired
    private AlbumService albumService;


}
