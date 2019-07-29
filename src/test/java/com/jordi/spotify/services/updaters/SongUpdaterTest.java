package com.jordi.spotify.services.updaters;

import com.jordi.spotify.entities.Album;
import com.jordi.spotify.entities.Artist;
import com.jordi.spotify.entities.Song;
import com.jordi.spotify.exceptions.SpotifyException;
import com.jordi.spotify.json.song.UserInputSongRest;
import com.jordi.spotify.repositories.AlbumRepository;
import com.jordi.spotify.repositories.ArtistRepository;
import com.jordi.spotify.repositories.SongRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class SongUpdaterTest {

    @Mock
    SongRepository songRepository;

    @Mock
    ArtistRepository artistRepository;

    @Mock
    AlbumRepository albumRepository;

    @InjectMocks
    SongUpdater songUpdater;

    @Before
    public void init() { MockitoAnnotations.initMocks(this); }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void updateSongWorksFine() throws SpotifyException {
        // given
        UserInputSongRest userInputSongRest = new UserInputSongRest();
        userInputSongRest.setName("Mr Blue Sky");
        userInputSongRest.setAlbumId(2L);
        userInputSongRest.setArtistId(2L);
        userInputSongRest.setTrackNumber(1);

        Album album = new Album(2L, "Out Of The Blue");
        album.setSongList(new ArrayList<>());
        Artist artist = new Artist(2L, "Electric Light Orchestra");

        Song songToUpdate = new Song(1L, "Hotel California");

        Song song = new Song();
        song.setId(1L);
        song.setName("Mr Blue Sky");
        song.setArtist(artist);
        song.setAlbum(album);
        song.setTrackNumber(1);

        //when
        Mockito.when(albumRepository.findById(2L)).thenReturn(Optional.of(album));
        Mockito.when(artistRepository.findById(2L)).thenReturn(Optional.of(artist));
        Mockito.when(songRepository.save(any())).thenReturn(any(Song.class));

        // then
        Song result = songUpdater.updateSong(songToUpdate, userInputSongRest);
        assertNotNull(result);
        assertEquals(song.getId(), result.getId());
        assertEquals(song.getName(), result.getName());
        assertEquals(song.getAlbum(), result.getAlbum());
        assertEquals(song.getArtist(), result.getArtist());
        assertEquals(song.getTrackNumber(), result.getTrackNumber());
    }
}