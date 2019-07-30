package com.jordi.spotify.utils.converters;


import com.jordi.spotify.entities.Album;
import com.jordi.spotify.entities.Artist;
import com.jordi.spotify.entities.Song;
import com.jordi.spotify.exceptions.DuplicateEntryException;
import com.jordi.spotify.exceptions.NotFoundException;
import com.jordi.spotify.exceptions.SpotifyException;
import com.jordi.spotify.json.song.UserInputSongRest;
import com.jordi.spotify.repositories.AlbumRepository;
import com.jordi.spotify.repositories.ArtistRepository;
import com.jordi.spotify.utils.constants.ExceptionConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInputRestToSongEntityConverter {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private AlbumRepository albumRepository;

    private Song returnSong;
    private UserInputSongRest songRest;

    public Song convertSong(Song actualSong, UserInputSongRest songRest) throws SpotifyException {
        this.returnSong = actualSong;
        this.songRest = songRest;
        convertName();
        convertArtist();
        convertAlbum();
        convertTrackNumber();
        return actualSong;
    }

    public Song convertSong(UserInputSongRest updatedSong) throws SpotifyException {
        this.returnSong = new Song();
        this.songRest = updatedSong;
        convertName();
        convertArtist();
        convertAlbum();
        convertTrackNumber();
        return returnSong;
    }

    private void convertName() {
        if (songRest.getName() != null) {
            returnSong.setName(songRest.getName());
        }
    }

    private void convertArtist() throws NotFoundException {
        if (songRest.getArtistId() != null) {
            returnSong.setArtist(getArtistOrThrow(songRest.getArtistId()));
        }
    }

    private Artist getArtistOrThrow(Long id) throws NotFoundException {
        return artistRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionConstants.MESSAGE_NONEXISTENT_ARTIST));
    }

    private void convertAlbum() throws NotFoundException {
        if (songRest.getAlbumId() != null) {
            returnSong.setAlbum(getAlbumOrThrow(songRest.getAlbumId()));
        }
    }

    private Album getAlbumOrThrow(Long id) throws NotFoundException {
        return albumRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionConstants.MESSAGE_NONEXISTENT_ALBUM));
    }

    private void convertTrackNumber() throws DuplicateEntryException {
        if (songRest.getTrackNumber() != null) {
            returnSong.setTrackNumber(songRest.getTrackNumber());
        }
        if (returnSong.getAlbum() != null) {
            throwExceptionIfTrackNumberIsAlreadyInAlbum(returnSong.getAlbum(), returnSong.getTrackNumber());
        }
    }

    private void throwExceptionIfTrackNumberIsAlreadyInAlbum(Album album, Integer trackNumber) throws DuplicateEntryException {
        for (Song song : album.getSongList()) {
            if (song.getTrackNumber().equals(trackNumber)) {
                throw new DuplicateEntryException(ExceptionConstants.MESSAGE_EXISTING_TRACK_NUMBER);
            }
        }
    }
}
