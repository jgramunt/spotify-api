package com.jordi.spotify.services.impl;

import com.jordi.spotify.entities.Album;
import com.jordi.spotify.entities.Artist;
import com.jordi.spotify.entities.Song;
import com.jordi.spotify.exceptions.DuplicateEntryException;
import com.jordi.spotify.exceptions.NotFoundException;
import com.jordi.spotify.exceptions.SpotifyException;
import com.jordi.spotify.json.song.SongRest;
import com.jordi.spotify.json.song.UserInputSongRest;
import com.jordi.spotify.repositories.AlbumRepository;
import com.jordi.spotify.repositories.ArtistRepository;
import com.jordi.spotify.repositories.SongRepository;
import com.jordi.spotify.services.SongService;
import com.jordi.spotify.utils.constants.ExceptionConstants;
import com.jordi.spotify.services.updaters.SongUpdater;
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

    @Autowired
    private SongUpdater songUpdater;

    @Override
    public List<SongRest> getSongs() throws SpotifyException {
        return songRepository.findAll().stream().map(song -> toRest(song)).collect(Collectors.toList());
    }

    @Override
    public SongRest getSongById(Long id) throws SpotifyException {
        return toRest(getSongOrThrow(id));
    }

    @Override
    public SongRest createSong(UserInputSongRest userInputSongRest) throws SpotifyException {
        Song newSong = createSongEntity(userInputSongRest);
        return toRest(songRepository.save(newSong));
    }

    @Override
    public SongRest updateSong(Long id, UserInputSongRest userInputSongRest) throws SpotifyException {
        Song songUpdated = songUpdater.updateSong(getSongOrThrow(id), userInputSongRest);
        return toRest(songUpdated);
    }

    @Override
    public String deleteSong(Long id) throws SpotifyException {
        songRepository.delete(getSongOrThrow(id));
        return "Song deleted";
    }

    // PRIVATE

    private Song getSongOrThrow(Long id) throws NotFoundException {
        return songRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionConstants.MESSAGE_NONEXISTENT_SONG));
    }

    private SongRest toRest(Song song) {
        SongRest songRest = new SongRest();
        songRest.setId(song.getId());
        songRest.setName(song.getName());
        if (song.getAlbum() != null) {
            songRest.setAlbumName(
                    song.getAlbum().getName());
        }
        if (song.getArtist() != null) {
            songRest.setArtistName(
                    song.getArtist().getName());
        }
        songRest.setTrackNumber(song.getTrackNumber());

        return songRest;
    }


    private Album getAlbumOrThrow(Long id) throws NotFoundException {
        return albumRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionConstants.MESSAGE_NONEXISTENT_ALBUM));
    }

    private Artist getArtistOrThrow(Long id) throws NotFoundException {
        return artistRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionConstants.MESSAGE_NONEXISTENT_ARTIST));
    }

    private Song createSongEntity(UserInputSongRest userInputSongRest) throws NotFoundException, DuplicateEntryException {
        Song song = new Song();
        song.setName(userInputSongRest.getName());
        if (userInputSongRest.getAlbumId() != null) {
            song.setAlbum(getAlbumOrThrow(userInputSongRest.getAlbumId()));
        }
        if (userInputSongRest.getArtistId() != null) {
            song.setArtist(getArtistOrThrow(userInputSongRest.getArtistId()));
        }
        if (userInputSongRest.getTrackNumber() != null) {
            song.setTrackNumber(userInputSongRest.getTrackNumber());
            if (song.getAlbum() != null) {
                throwExceptionIfTrackNumberIsAlreadyInAlbum(song.getAlbum(), userInputSongRest.getTrackNumber());
            }
        }
        return song;
    }


    private Song updateSongEntity(Song actualSong, UserInputSongRest updatedSong) throws NotFoundException, DuplicateEntryException {
        if (updatedSong.getName() != null) { actualSong.setName(updatedSong.getName()); }
        if (updatedSong.getArtistId() != null) { actualSong.setArtist(getArtistOrThrow(updatedSong.getArtistId()));}
        if (updatedSong.getAlbumId() != null) { actualSong.setAlbum(getAlbumOrThrow(updatedSong.getAlbumId()));}
        if (updatedSong.getTrackNumber() != null) {
            if (actualSong.getAlbum() != null) {
                throwExceptionIfTrackNumberIsAlreadyInAlbum(getAlbumOrThrow(updatedSong.getAlbumId()), updatedSong.getTrackNumber());
            }
            actualSong.setTrackNumber(updatedSong.getTrackNumber());
        }
        return actualSong;
    }

    private void throwExceptionIfTrackNumberIsAlreadyInAlbum(Album album, Integer trackNumber) throws DuplicateEntryException {
        for (Song song : album.getSongList()) {
            if (song.getTrackNumber().equals(trackNumber)) {
                throw new DuplicateEntryException(ExceptionConstants.MESSAGE_EXISTING_TRACK_NUMBER);
            }
        }
    }
}
