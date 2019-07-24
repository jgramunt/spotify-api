package com.jordi.spotify.utils.updaters;

import com.jordi.spotify.entities.Album;
import com.jordi.spotify.entities.Artist;
import com.jordi.spotify.entities.Song;
import com.jordi.spotify.exceptions.DuplicateEntryException;
import com.jordi.spotify.exceptions.NotFoundException;
import com.jordi.spotify.json.song.CreateSongRest;
import com.jordi.spotify.repositories.AlbumRepository;
import com.jordi.spotify.repositories.ArtistRepository;
import com.jordi.spotify.utils.constants.ExceptionConstants;
import org.springframework.beans.factory.annotation.Autowired;

public class SongUpdater {

    private Song actualSong;
    private CreateSongRest updatedSong;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private AlbumRepository albumRepository;

    public SongUpdater(Song actualSong, CreateSongRest updatedSong) {
        this.actualSong = actualSong;
        this.updatedSong = updatedSong;
    }

    public Song returnUpdatedSongEntity() throws NotFoundException, DuplicateEntryException {
        updateSong();
        return actualSong;
    }

    private void updateSong() throws NotFoundException, DuplicateEntryException {
        updateName();
        updateArtist();
        updateAlbum();
        updateTrackNumber();
    }

    private void updateName() {
        if (updatedSong.getName() != null) {
            actualSong.setName(updatedSong.getName());
        }
    }

    private void updateArtist() throws NotFoundException {
        if (updatedSong.getName() != null) {
            actualSong.setArtist(getArtistOrThrow(updatedSong.getArtistId()));
        }
    }

    private void updateAlbum() throws NotFoundException {
        if (updatedSong.getArtistId() != null) {
            actualSong.setAlbum(getAlbumOrThrow(updatedSong.getAlbumId()));
        }
    }

    private void updateTrackNumber() throws DuplicateEntryException {
        if (updatedSong.getName() != null) {
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
