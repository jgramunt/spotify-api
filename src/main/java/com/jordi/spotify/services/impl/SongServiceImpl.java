package com.jordi.spotify.services.impl;

import com.jordi.spotify.repositories.SongRepository;
import com.jordi.spotify.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SongServiceImpl implements SongService {

    @Autowired
    private SongRepository songRepository;

}
