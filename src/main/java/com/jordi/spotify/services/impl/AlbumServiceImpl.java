package com.jordi.spotify.services.impl;


import com.jordi.spotify.entities.Album;
import com.jordi.spotify.exceptions.NotFoundException;
import com.jordi.spotify.exceptions.SpotifyException;
import com.jordi.spotify.json.AlbumRest;
import com.jordi.spotify.repositories.AlbumRepository;
import com.jordi.spotify.services.AlbumService;
import com.jordi.spotify.utils.constants.CommonConstants;
import com.jordi.spotify.utils.constants.ExceptionConstants;
import com.jordi.spotify.utils.constants.RestConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    private AlbumRepository albumRepository;


    @Override
    public List<AlbumRest> getAll() throws SpotifyException {
        return albumRepository.findAll()
                .stream().map(album -> toRest(album)).collect(Collectors.toList());
    }

    @Override
    public AlbumRest getById(Long id) throws SpotifyException {
        return toRest(getAlbumOrThrow(id));
    }



    // PRIVATE

    private AlbumRest toRest(Album album) {
        AlbumRest albumRest = new AlbumRest();
        albumRest.setId(album.getId());
        albumRest.setName(album.getName());
        return albumRest;
    }

    private Album getAlbumOrThrow(Long id) throws NotFoundException {
        return albumRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionConstants.MESSAGE_NONEXISTENT_ALBUM));
    }
}
