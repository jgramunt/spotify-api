package com.jordi.spotify.services.impl;

import com.jordi.spotify.entities.Album;
import com.jordi.spotify.exceptions.NotFoundException;
import com.jordi.spotify.exceptions.SpotifyException;
import com.jordi.spotify.json.AlbumRest;
import com.jordi.spotify.repositories.AlbumRepository;
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
        List<AlbumRest> result = albumService.getAll();
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
        List<AlbumRest> result = albumService.getAll();
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
        AlbumRest result = albumService.getById(2L);
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
        albumService.getById(1L);
    }
}