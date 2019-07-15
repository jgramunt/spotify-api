package com.jordi.spotify.services.impl;

import com.jordi.spotify.entities.Album;
import com.jordi.spotify.exceptions.DuplicateEntryException;
import com.jordi.spotify.exceptions.NotFoundException;
import com.jordi.spotify.exceptions.SpotifyException;
import com.jordi.spotify.json.AlbumRest;
import com.jordi.spotify.json.ArtistRest;
import com.jordi.spotify.json.album.AlbumCreateRest;
import com.jordi.spotify.repositories.AlbumRepository;
import com.jordi.spotify.utils.constants.ExceptionConstants;
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
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class AlbumServiceImplTest {

    @Mock
    AlbumRepository albumRepository;

    @InjectMocks
    AlbumServiceImpl albumService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();


    @Test
    public void getAllGetsNoneWhenListIsEmpty() throws SpotifyException {
        // given
        List<Album> albumList = new ArrayList<>();

        //when
        Mockito.when(albumRepository.findAll()).thenReturn(albumList);

        // assert
        List<AlbumRest> result = albumService.getAlbums();
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void getAllWorksFine() throws SpotifyException {
        // given
        List<Album> albumList = new ArrayList<>();
        albumList.add(new Album(1L, "Definitely Maybe"));
        albumList.add(new Album(1L, "Let It Be"));

        // when
        Mockito.when(albumRepository.findAll()).thenReturn(albumList);

        // then
        List<AlbumRest> result = albumService.getAlbums();
        assertNotNull(result);
        assertEquals(albumList.get(0).getId(), result.get(0).getId());
        assertEquals(albumList.get(0).getName(), result.get(0).getName());
        assertEquals(albumList.get(1).getId(), result.get(1).getId());
        assertEquals(albumList.get(1).getName(), result.get(1).getName());
    }

    @Test
    public void getByIdWorksFine() throws SpotifyException {
        // given
        Album album = new Album(2L, "Let It Be");
        AlbumRest albumRest = new AlbumRest(2L, "Let It Be");

        // when
        Mockito.when(albumRepository.findById(2L)).thenReturn(java.util.Optional.ofNullable(album));

        // then
        AlbumRest result = albumService.getAlbumById(2L);
        assertNotNull(result);
        assertEquals(albumRest.getId(), result.getId());
        assertEquals(albumRest.getName(), result.getName());
    }

    @Test
    public void getByIdNotFound() throws SpotifyException {
        expectedException.expect(NotFoundException.class);
        expectedException.expectMessage("NONEXISTENT ALBUM - Album does not exist");

        // when
        Mockito.when(albumRepository.findById(any())).thenReturn(Optional.empty());

        // then
        albumService.getAlbumById(1L);
    }

    @Test
    public void createAlbumWorksFine() throws SpotifyException {
        // given
        AlbumCreateRest albumCreateRest = new AlbumCreateRest("Let It Be");
        Album album = new Album(2L, "Let It Be");
        AlbumRest albumRest = new AlbumRest(2L, "Let It Be");

        // when
        Mockito.when(albumRepository.save(any(Album.class))).thenReturn(album);

        // then
        AlbumRest result = albumService.createAlbum(albumCreateRest);
        assertNotNull(result);
        assertEquals(albumRest.getId(), result.getId());
        assertEquals(albumRest.getName(), result.getName());
    }

    @Test
    public void createAlbumFails() throws SpotifyException {
        // given
        expectedException.expect(DuplicateEntryException.class);
        expectedException.expectMessage("ALBUM ALREADY EXIST - An album with the same name does already exist");

        // when
        Mockito.when(albumRepository.existsByName(any())).thenReturn(true);

        // then
        albumService.createAlbum(new AlbumCreateRest("Let It Be"));
    }

    @Test
    public void updateAlbumWorksFine() throws SpotifyException {
        // given
        AlbumRest updatedArtistRest = new AlbumRest(null, "Let It Be");
        Album albumToUpdate = new Album(1L, "Definitely Maybe");
        Album updatedAlbum = new Album(1L, "Let It Be");
        AlbumRest updatedAlbumRest = new AlbumRest(1L, "Let It Be");

        // when
        Mockito.when(albumRepository.findById(any())).thenReturn(Optional.of(albumToUpdate));
        Mockito.when(albumRepository.save(any())).thenReturn(updatedAlbum);

        // then
        AlbumRest result = albumService.updateAlbum(1L, updatedAlbumRest);
        assertNotNull(result);
        assertEquals(updatedAlbumRest.getId(), result.getId());
        assertEquals(updatedAlbumRest.getName(), result.getName());
    }

    @Test
    public void updatedAlbumNotFound() throws SpotifyException {
        // given
        expectedException.expect(NotFoundException.class);
        expectedException.expectMessage("NONEXISTENT ALBUM - Album does not exist");

        // when
        Mockito.when(albumRepository.findById(any())).thenReturn(Optional.empty());

        // then
        albumService.updateAlbum(1L, new AlbumRest());
    }

    @Test
    public void deleteAlbumWorksFine() throws SpotifyException {
        // when
        Mockito.when(albumRepository.findById(1L)).thenReturn(Optional.of(new Album()));

        // then
        String result = albumService.deleteAlbum(1L);
        assertNotNull(result);
        assertEquals("Album deleted", result);
    }

    @Test
    public void deleteAlbumNotFound() throws SpotifyException {
        // given
        expectedException.expect(NotFoundException.class);
        expectedException.expectMessage("NONEXISTENT ALBUM - Album does not exist");

        // when
        Mockito.when(albumRepository.findById(1L)).thenReturn(Optional.empty());

        // then
        albumService.deleteAlbum(1L);
    }
}