package com.jordi.spotify.services.updaters;

import com.jordi.spotify.entities.Album;
import com.jordi.spotify.entities.Artist;
import com.jordi.spotify.entities.Song;
import com.jordi.spotify.exceptions.DuplicateEntryException;
import com.jordi.spotify.exceptions.NotFoundException;
import com.jordi.spotify.json.song.UserInputSongRest;
import com.jordi.spotify.repositories.AlbumRepository;
import com.jordi.spotify.repositories.ArtistRepository;
import com.jordi.spotify.repositories.SongRepository;
import com.jordi.spotify.utils.constants.ExceptionConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SongUpdater {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private AlbumRepository albumRepository;

    private Song actualSong;
    private UserInputSongRest updatedSong;


    public Song updateSong(Song actualSong, UserInputSongRest updatedSong) throws NotFoundException, DuplicateEntryException {
        this.actualSong = actualSong;
        this.updatedSong = updatedSong;
        updateName();
        updateArtist();
        updateAlbum();
        updateTrackNumber();
        songRepository.save(actualSong);
        return actualSong;

    }

    private void updateName() {
        if (updatedSong.getName() != null) {
            actualSong.setName(updatedSong.getName());
        }
    }

    private void updateArtist() throws NotFoundException {
        if (updatedSong.getArtistId() != null) {
            actualSong.setArtist(getArtistOrThrow(updatedSong.getArtistId()));
        }
    }

    private void updateAlbum() throws NotFoundException {
        if (updatedSong.getAlbumId() != null) {
            actualSong.setAlbum(getAlbumOrThrow(updatedSong.getAlbumId()));
        }
    }

    private void updateTrackNumber() throws DuplicateEntryException {
        if (updatedSong.getTrackNumber() != null) {
            actualSong.setTrackNumber(updatedSong.getTrackNumber());
        }
        if (actualSong.getAlbum() != null) {
            throwExceptionIfTrackNumberIsAlreadyInAlbum(actualSong.getAlbum(), actualSong.getTrackNumber());
        }
    }

    private void throwExceptionIfTrackNumberIsAlreadyInAlbum(Album album, Integer trackNumber) throws DuplicateEntryException {
        for (Song song : album.getSongList()) {
            if (song.getTrackNumber().equals(trackNumber)) {
                throw new DuplicateEntryException(ExceptionConstants.MESSAGE_EXISTING_TRACK_NUMBER);
            }
        }
    }


    private Album getAlbumOrThrow(Long id) throws NotFoundException {
        return albumRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionConstants.MESSAGE_NONEXISTENT_ALBUM));
    }

    private Artist getArtistOrThrow(Long id) throws NotFoundException {
        return artistRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionConstants.MESSAGE_NONEXISTENT_ARTIST));
    }
}
