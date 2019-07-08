package com.jordi.spotify.services.impl;

import com.jordi.spotify.entities.Artist;
import com.jordi.spotify.exceptions.NotFoundException;
import com.jordi.spotify.exceptions.SpotifyException;
import com.jordi.spotify.json.ArtistRest;
import com.jordi.spotify.repositories.ArtistRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class ArtistServiceImplTest {

    @Mock
    ArtistRepository artistRepository;

    @InjectMocks
    ArtistServiceImpl artistService;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();


    @Test
    public void getAllGetsNoneWhenListIsEmpty() throws SpotifyException {
        // given
        List<Artist> artistList = new ArrayList<>();

        // when
        Mockito.when(artistRepository.findAll()).thenReturn(artistList);

        // then
        assertNotNull(artistService.findAll());
    }

    @Test
    public void getAllGetsRestList() throws SpotifyException {
        // given
        List<Artist> artistList = new ArrayList<>();
        artistList.add(new Artist((long) 1, "Oasis"));
        artistList.add(new Artist((long) 2, "Eagles"));

        // when
        Mockito.when(artistRepository.findAll()).thenReturn(artistList);
        List<ArtistRest> artistRestList = artistService.findAll();

        // then
        assertNotNull(artistRestList);
        assertEquals(2, artistRestList.size());
        assertEquals(artistList.get(0).getId(), artistRestList.get(0).getId());
        assertEquals(artistList.get(0).getName(), artistRestList.get(0).getName());
        assertEquals(artistList.get(1).getId(), artistRestList.get(1).getId());
        assertEquals(artistList.get(1).getName(), artistRestList.get(1).getName());
    }

    @Test
    public void getById() throws SpotifyException {
        // given
        Artist artist = new Artist(1L, "Oasis");
        ArtistRest artistRest = new ArtistRest(1L, "Oasis");

        // when
        Mockito.when(artistRepository.findById(any())).thenReturn(java.util.Optional.of(artist));

        // then
        ArtistRest result = artistService.findById(1L);
        assertNotNull(result);
        assertEquals(artistRest.getId(), result.getId());
        assertEquals(artistRest.getName(), result.getName());
    }

    @Test
    public void getByIdShouldThrowNotFound() throws SpotifyException {
        // given
        expectedException.expect(NotFoundException.class);
        expectedException.expectMessage("NONEXISTENT ARTIST - Artist does not exist");
        Optional<Artist> artistOptional = Optional.empty();

        //when
        Mockito.when(artistRepository.findById(any())).thenReturn(artistOptional);

        // then
        artistService.findById(1L);
    }

}