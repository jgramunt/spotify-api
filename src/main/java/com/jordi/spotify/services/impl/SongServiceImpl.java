package com.jordi.spotify.services.impl;

import com.jordi.spotify.entities.Album;
import com.jordi.spotify.entities.Artist;
import com.jordi.spotify.entities.Song;
import com.jordi.spotify.exceptions.NotFoundException;
import com.jordi.spotify.exceptions.SpotifyException;
import com.jordi.spotify.json.AlbumRest;
import com.jordi.spotify.json.SongRest;
import com.jordi.spotify.json.song.CreateSongRest;
import com.jordi.spotify.repositories.AlbumRepository;
import com.jordi.spotify.repositories.ArtistRepository;
import com.jordi.spotify.repositories.SongRepository;
import com.jordi.spotify.services.SongService;
import com.jordi.spotify.utils.constants.ExceptionConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SongServiceImpl implements SongService {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Override
    public List<SongRest> getSongs() throws SpotifyException {
        return songRepository.findAll().stream().map(song -> toRest(song)).collect(Collectors.toList());
    }

    @Override
    public SongRest getSongById(Long id) throws SpotifyException {
        return toRest(getSongOrThrow(id));
    }

    @Override
    public SongRest createSong(CreateSongRest createSongRest) throws SpotifyException {
        return toRest(songRepository.save(createToEntity(createSongRest)));
    }

    @Override
    public SongRest updateSong(Long id, CreateSongRest createSongRest) throws SpotifyException {
        Song updatedSong = updateSongEntity(getSongOrThrow(id), createSongRest);
        return toRest(updatedSong);
    }

    @Override
    public String deleteSong(Long id) throws SpotifyException {
        songRepository.delete(getSongOrThrow(id));
        return "Song deleted";
    }

    // PRIVATE
    private SongRest toRest(Song song) {
        SongRest songRest = new SongRest();
        songRest.setId(song.getId());
        songRest.setName(song.getName());
        if (song.getAlbum() != null) {
            songRest.setAlbumName(song.getAlbum().getName());
        }
        if (song.getArtist() != null) {
            songRest.setArtistName(song.getArtist().getName());
        }
        return songRest;
    }


    private Song createToEntity(CreateSongRest createSongRest) throws NotFoundException {
        Song song = new Song();
        song.setName(createSongRest.getName());
        if (createSongRest.getAlbumId() != null) {
            song.setAlbum(getAlbumOrThrow(createSongRest.getAlbumId()));
        }
        if (createSongRest.getArtistId() != null) {
            song.setArtist(getArtistOrThrow(createSongRest.getArtistId()));
        }
        return song;
    }

    private Song getSongOrThrow(Long id) throws NotFoundException {
        return songRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionConstants.MESSAGE_NONEXISTENT_SONG));
    }

    private Album getAlbumOrThrow(Long id) throws NotFoundException {
        return albumRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionConstants.MESSAGE_NONEXISTENT_ALBUM));
    }

    private Artist getArtistOrThrow(Long id) throws NotFoundException {
        return artistRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionConstants.MESSAGE_NONEXISTENT_ARTIST));
    }

    private Song updateSongEntity(Song actualSong, CreateSongRest updatedSong) throws NotFoundException {
        if (updatedSong.getName() != null) { actualSong.setName(updatedSong.getName()); }
        if (updatedSong.getArtistId() != null) { actualSong.setArtist(getArtistOrThrow(updatedSong.getArtistId()));}
        if (updatedSong.getAlbumId() != null) { actualSong.setAlbum(getAlbumOrThrow(updatedSong.getAlbumId()));}
        return actualSong;
    }
}
