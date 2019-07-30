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
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
public class UserInputRestToSongEntityConverterTest {

    @Mock
    ArtistRepository artistRepository;

    @Mock
    AlbumRepository albumRepository;

    @InjectMocks
    UserInputRestToSongEntityConverter converter;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void createSongEntityWorksFine() throws SpotifyException {
        // given
        UserInputSongRest userInputSongRest = new UserInputSongRest();
        userInputSongRest.setName("Mr Blue Sky");
        userInputSongRest.setAlbumId(2L);
        userInputSongRest.setArtistId(2L);
        userInputSongRest.setTrackNumber(1);

        Album album = new Album(2L, "Out Of The Blue");
        album.setSongList(new ArrayList<>());
        Artist artist = new Artist(2L, "Electric Light Orchestra");

        Song song = new Song();
        song.setName("Mr Blue Sky");
        song.setArtist(artist);
        song.setAlbum(album);
        song.setTrackNumber(1);

        //when
        Mockito.when(albumRepository.findById(2L)).thenReturn(Optional.of(album));
        Mockito.when(artistRepository.findById(2L)).thenReturn(Optional.of(artist));

        // then
        Song result = converter.convertSong(userInputSongRest);
        assertNotNull(result);
        assertEquals(song.getName(), result.getName());
        assertEquals(song.getAlbum(), result.getAlbum());
        assertEquals(song.getArtist(), result.getArtist());
        assertEquals(song.getTrackNumber(), result.getTrackNumber());
    }

    @Test
    public void updateSongEntityWorksFine() throws SpotifyException {
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

        // then
        Song result = converter.convertSong(songToUpdate, userInputSongRest);
        assertNotNull(result);
        assertEquals(song.getId(), result.getId());
        assertEquals(song.getName(), result.getName());
        assertEquals(song.getAlbum(), result.getAlbum());
        assertEquals(song.getArtist(), result.getArtist());
        assertEquals(song.getTrackNumber(), result.getTrackNumber());
    }

    @Test
    public void shouldThrowArtistNotFoundExceptionWhenArtistNotFound() throws SpotifyException {
        // given
        expectedException.expect(NotFoundException.class);
        expectedException.expectMessage("NONEXISTENT ARTIST - Artist does not exist");

        Song songToUpdate = new Song(1L, "Fixing A Hole");
        UserInputSongRest userInputSongRest = new UserInputSongRest();
        userInputSongRest.setArtistId(2L);

        // when
        Mockito.when(artistRepository.findById(2L)).thenReturn(Optional.empty());

        // then
        converter.convertSong(songToUpdate, userInputSongRest);
    }

    @Test
    public void shouldThrowAlbumNotFoundExceptionWhenAlbumNotFound() throws SpotifyException {
        // given
        expectedException.expect(NotFoundException.class);
        expectedException.expectMessage("NONEXISTENT ALBUM - Album does not exist");

        Song songToUpdate = new Song(1L, "Fixing A Hole");
        UserInputSongRest userInputSongRest = new UserInputSongRest();
        userInputSongRest.setAlbumId(2L);

        // when
        Mockito.when(albumRepository.findById(2L)).thenReturn(Optional.empty());

        // then
        converter.convertSong(songToUpdate, userInputSongRest);
    }

    @Test
    public void shouldThrowExceptionWhenTrackNumberIsAlreadyInAlbum() throws SpotifyException {
        // given
        expectedException.expect(DuplicateEntryException.class);
        expectedException.expectMessage("TRACK NUMBER ALREADY EXIST - A song with the same track number for this album does already exist");

        Song songOnAlbum = new Song(1L, "Song 1");
        songOnAlbum.setTrackNumber(1);
        List<Song> albumSongList = new ArrayList<>();
        albumSongList.add(songOnAlbum);
        Album album = new Album(1L, "Some Album");
        album.setSongList(albumSongList);

        Song songToUpdate = new Song(2L, "Song To Update");
        UserInputSongRest userInputSongRest = new UserInputSongRest();
        userInputSongRest.setAlbumId(1L);
        userInputSongRest.setTrackNumber(1);

        // when
        Mockito.when(albumRepository.findById(1L)).thenReturn(Optional.of(album));

        // then
        converter.convertSong(songToUpdate, userInputSongRest);

    }
}