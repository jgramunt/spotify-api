package com.jordi.spotify.services.impl;

import com.jordi.spotify.entities.Artist;
import com.jordi.spotify.exceptions.DuplicateEntryException;
import com.jordi.spotify.exceptions.NotFoundException;
import com.jordi.spotify.exceptions.SpotifyException;
import com.jordi.spotify.json.ArtistRest;
import com.jordi.spotify.json.artist.ArtistCreateRest;
import com.jordi.spotify.repositories.ArtistRepository;
import com.jordi.spotify.services.ArtistService;
import com.jordi.spotify.utils.constants.ExceptionConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtistServiceImpl implements ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    @Override
    public List<ArtistRest> findAll() throws SpotifyException {
        List<Artist> artistList = artistRepository.findAll();

        return artistList.stream().map(artist -> toRest(artist)).collect(Collectors.toList());
    }

    @Override
    public ArtistRest findById(Long id) throws SpotifyException {
        return toRest(getArtistOrThrow(id));
    }

    @Override
    public ArtistRest createArtist(ArtistCreateRest createdArtist) throws SpotifyException {
        if (artistRepository.existsByName(createdArtist.getName())) {
            throw new DuplicateEntryException(ExceptionConstants.MESSAGE_EXISTING_ARTIST);
        }
        Artist artist = new Artist();
        artist.setName(createdArtist.getName());

        Artist newArtist = artistRepository.save(artist);

        return new ArtistRest(newArtist.getId(), newArtist.getName());
    }

    private boolean itExists(ArtistCreateRest createdArtist) {
        return artistRepository.existsByName(createdArtist.getName());
    }

    ;



    // PRIVATE

    private ArtistRest toRest(Artist artist) {
        ArtistRest artistRest = new ArtistRest();
        artistRest.setId(artist.getId());
        artistRest.setName(artist.getName());
        return artistRest;
    }

    private Artist getArtistOrThrow(Long id) throws NotFoundException {
        return artistRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionConstants.MESSAGE_NONEXISTENT_ARTIST));
    }

}
