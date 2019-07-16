package com.jordi.spotify.services.impl;

import com.jordi.spotify.entities.Album;
import com.jordi.spotify.entities.Artist;
import com.jordi.spotify.entities.Song;
import com.jordi.spotify.exceptions.NotFoundException;
import com.jordi.spotify.exceptions.SpotifyException;
import com.jordi.spotify.json.SongRest;
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
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class SongServiceImplTest {

    @Mock
    SongRepository songRepository;

    @InjectMocks
    SongServiceImpl songService;

    @Before
    public void init() { MockitoAnnotations.initMocks(this); }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void getSongsWorksFine() throws SpotifyException {
        // given
        Song song1 = new Song(1L, "Mr Blue Sky");
        Song song2 = new Song(2L, "Hotel California");
        List<Song> songList = new ArrayList<>();
        songList.add(song1);
        songList.add(song2);

        SongRest songRest1 = new SongRest(1L, "Mr Blue Sky");
        SongRest songRest2 = new SongRest(2L, "Hotel California");
        List<SongRest> songRestList = new ArrayList<>();
        songRestList.add(songRest1);
        songRestList.add(songRest2);

        // when
        Mockito.when(songRepository.findAll()).thenReturn(songList);

        // then
        List<SongRest> result = songService.getSongs();
        assertNotNull(result);
        assertEquals(songRestList.get(0).getId(), result.get(0).getId());
        assertEquals(songRestList.get(0).getName(), result.get(0).getName());
        assertEquals(songRestList.get(1).getId(), result.get(1).getId());
        assertEquals(songRestList.get(1).getName(), result.get(1).getName());
    }

    @Test
    public void getSongByIdWorksFine() throws SpotifyException {
        // given
        Artist artist = new Artist(1L, "Electric Light Orchestra");
        Album album = new Album(1L, "Out Of The Blue");
        Song song = new Song(1L, "Mr Blue Sky");
        song.setAlbum(album);
        song.setArtist(artist);
        SongRest songRest = new SongRest(1L, "Mr Blue Sky");
        songRest.setAlbumName(album.getName());
        songRest.setArtistName(artist.getName());

        // when
        Mockito.when(songRepository.findById(1L)).thenReturn(java.util.Optional.of(song));

        // then
        SongRest result = songService.getSongById(1L);
        assertNotNull(result);
        assertEquals(songRest.getId(), result.getId());
        assertEquals(songRest.getName(), result.getName());
        assertEquals(songRest.getAlbumName(), result.getAlbumName());
        assertEquals(songRest.getArtistName(), result.getArtistName());
    }

    @Test
    public void getSongByIdNotFound() throws SpotifyException {
        expectedException.expect(NotFoundException.class);
        expectedException.expectMessage("NONEXISTENT SONG - Song does not exist");

        // when
        Mockito.when(songRepository.findById(any())).thenReturn(Optional.empty());

        // then
        songService.getSongById(1L);

    }

}