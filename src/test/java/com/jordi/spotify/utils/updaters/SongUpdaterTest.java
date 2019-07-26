package com.jordi.spotify.utils.updaters;

import com.jordi.spotify.entities.Album;
import com.jordi.spotify.entities.Artist;
import com.jordi.spotify.entities.Song;
import com.jordi.spotify.exceptions.SpotifyException;
import com.jordi.spotify.json.SongRest;
import com.jordi.spotify.json.song.CreateSongRest;
import com.jordi.spotify.repositories.AlbumRepository;
import com.jordi.spotify.repositories.ArtistRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.Assert.*;

@SpringBootTest
public class SongUpdaterTest {

    @Mock
    ArtistRepository artistRepository;

    @Mock
    AlbumRepository albumRepository;


    @Before
    public void init() { MockitoAnnotations.initMocks(this); }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void updateSongWorksFine() throws SpotifyException {
        // given
        CreateSongRest createSongRest = new CreateSongRest();
        createSongRest.setName("Mr Blue Sky");
        createSongRest.setAlbumId(2L);
        createSongRest.setArtistId(2L);

        Album album = new Album(2L, "Out Of The Blue");
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

        // then
        SongUpdater songUpdater = new SongUpdater(songToUpdate, createSongRest);
        Song result = songUpdater.returnUpdatedSongEntity();
        assertNotNull(result);
        assertEquals(song.getId(), result.getId());
        assertEquals(song.getName(), result.getName());
        assertEquals(song.getAlbum(), result.getAlbum());
        assertEquals(song.getArtist(), result.getArtist());
        assertEquals(song.getTrackNumber(), result.getTrackNumber());
    }
}