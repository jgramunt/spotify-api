package com.jordi.spotify.services.impl;


import com.jordi.spotify.entities.Album;
import com.jordi.spotify.exceptions.DuplicateEntryException;
import com.jordi.spotify.exceptions.NotFoundException;
import com.jordi.spotify.exceptions.SpotifyException;
import com.jordi.spotify.json.AlbumRest;
import com.jordi.spotify.json.album.AlbumCreateRest;
import com.jordi.spotify.repositories.AlbumRepository;
import com.jordi.spotify.services.AlbumService;
import com.jordi.spotify.utils.constants.ExceptionConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    private AlbumRepository albumRepository;


    @Override
    public List<AlbumRest> getAlbums() throws SpotifyException {
        return albumRepository.findAll()
                .stream().map(album -> toRest(album)).collect(Collectors.toList());
    }

    @Override
    public AlbumRest getAlbumById(Long id) throws SpotifyException {
        return toRest(getAlbumOrThrow(id));
    }

    @Override
    public AlbumRest createAlbum(AlbumCreateRest albumCreateRest) throws SpotifyException {
        if (albumRepository.existsByName(albumCreateRest.getName())) {
            throw new DuplicateEntryException(ExceptionConstants.MESSAGE_EXISTING_ALBUM);
        }
        Album savedEntity = albumRepository.save(createRestToEntity(albumCreateRest));
        return toRest(savedEntity);
    }

    @Override
    public AlbumRest updateAlbum(Long id, AlbumRest albumRest) throws SpotifyException {
        Album updatedAlbum = updateAlbumEntity(getAlbumOrThrow(id), albumRest);
        Album savedUpdatedAlbum = albumRepository.save(updatedAlbum);
        return toRest(savedUpdatedAlbum);
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

    private Album createRestToEntity(AlbumCreateRest albumCreateRest) {
        Album album = new Album();
        album.setName(albumCreateRest.getName());
        return album;
    }

    private Album updateAlbumEntity(Album albumToUpdate, AlbumRest updatedAlbumRest) {
        if (updatedAlbumRest.getId() != null) {
            albumToUpdate.setId(updatedAlbumRest.getId());
        }
        if (updatedAlbumRest.getName() != null) {
            albumToUpdate.setName(updatedAlbumRest.getName());
        }
        return albumToUpdate;
    }
}
