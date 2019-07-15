package com.jordi.spotify.controllers.impl;

import com.jordi.spotify.controllers.SongController;
import com.jordi.spotify.services.SongService;
import com.jordi.spotify.utils.constants.RestConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RestConstants.APPLICATION_NAME + RestConstants.API_VERSION_1 + RestConstants.RESOURCE_SONGS)
public class SongControllerImpl implements SongController {

    @Autowired
    private SongService songService;

    // TO-DO write classes here
}
