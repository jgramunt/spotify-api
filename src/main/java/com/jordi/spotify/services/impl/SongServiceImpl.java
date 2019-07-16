package com.jordi.spotify.services.impl;

import com.jordi.spotify.entities.Song;
import com.jordi.spotify.exceptions.NotFoundException;
import com.jordi.spotify.exceptions.SpotifyException;
import com.jordi.spotify.json.SongRest;
import com.jordi.spotify.repositories.SongRepository;
import com.jordi.spotify.services.SongService;
import com.jordi.spotify.utils.constants.ExceptionConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SongServiceImpl implements SongService {

    @Autowired
    private SongRepository songRepository;

    @Override
    public List<SongRest> getSongs() throws SpotifyException {
        return songRepository.findAll().stream().map(song -> toRest(song)).collect(Collectors.toList());
    }

    @Override
    public SongRest getSongById(Long id) throws SpotifyException {
        return toRest(getSongOrThrow(id));
    }


    // PRIVATE
    private SongRest toRest(Song song) {
        SongRest songRest = new SongRest();
        songRest.setId(song.getId());
        songRest.setName(song.getName());
        if (song.getAlbum() != null) { songRest.setAlbumName(song.getAlbum().getName()); }
        if (song.getArtist() != null) { songRest.setArtistName(song.getArtist().getName()); }
        return songRest;
    }

    private Song getSongOrThrow(Long id) throws NotFoundException {
        return songRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionConstants.MESSAGE_NONEXISTENT_SONG));
    }
}
