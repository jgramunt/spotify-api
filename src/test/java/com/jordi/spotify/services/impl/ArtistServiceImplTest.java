package com.jordi.spotify.services.impl;

import com.jordi.spotify.entities.Artist;
import com.jordi.spotify.exceptions.DuplicateEntryException;
import com.jordi.spotify.exceptions.NotFoundException;
import com.jordi.spotify.exceptions.SpotifyException;
import com.jordi.spotify.json.artist.ArtistRest;
import com.jordi.spotify.json.artist.UserInputArtistRest;
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
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class ArtistServiceImplTest {

    @Mock
    ArtistRepository artistRepository;

    @InjectMocks
    ArtistServiceImpl artistService;

    @Before
    public void init() {
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
        artistList.add(new Artist(1L, "Oasis"));
        artistList.add(new Artist(2L, "Eagles"));

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

    @Test
    public void createArtistShouldBeOk() throws SpotifyException {
        // given
        UserInputArtistRest userInputArtistRest = new UserInputArtistRest();
        userInputArtistRest.setName("Oasis");

        Artist artist = new Artist(2L, "Oasis");

        // when
        Mockito.when(artistRepository.save(Mockito.any(Artist.class))).thenReturn(artist);

        // then
        ArtistRest result = artistService.createArtist(userInputArtistRest);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(userInputArtistRest.getName(), result.getName());
    }

    @Test
    public void createArtistAlreadyExists() throws SpotifyException {
        // given
        expectedException.expect(DuplicateEntryException.class);
        expectedException.expectMessage("ARTIST ALREADY EXIST - An artist with the same name does already exist");
//        Optional<Artist> alreadyExistingArtist = Optional.of(new Artist(1L, "Oasis"));

        //when
        Mockito.when(artistRepository.existsByName(any(String.class))).thenReturn(true);

        // then
        artistService.createArtist(new UserInputArtistRest("Oasis"));
    }

    @Test
    public void updateArtistWorksFine() throws SpotifyException {
        // given
        ArtistRest updatedArtistInfoRest = new ArtistRest(null, "The Beatles");
        Artist artistToUpdate = new Artist(2L, "Oasis");
        Artist artistUpdated = new Artist(2L, "The Beatles");
        ArtistRest updatedArtist = new ArtistRest(2L, "The Beatles");

        // when
        Mockito.when(artistRepository.findById(2L)).thenReturn(Optional.of(artistToUpdate));
        Mockito.when(artistRepository.save(any())).thenReturn(artistUpdated);

        // then
        ArtistRest result = artistService.updateArtist(2L, updatedArtistInfoRest);
        assertNotNull(result);
        assertEquals(updatedArtist.getId(), result.getId());
        assertEquals(updatedArtist.getName(), result.getName());
    }

    @Test
    public void updatedArtistNotFound() throws SpotifyException {
        // given
        expectedException.expect(NotFoundException.class);
        expectedException.expectMessage("NONEXISTENT ARTIST - Artist does not exist");

        // then
        artistService.updateArtist(1L, new ArtistRest());
    }

    @Test
    public void deleteArtistWorksFine() throws SpotifyException {
        // when
        Mockito.when(artistRepository.findById(1L)).thenReturn(Optional.of(new Artist()));

        // then
        String result = artistService.deleteArtist(1L);
        assertNotNull(result);
        assertEquals("Artist deleted", result);
    }

    @Test
    public void deleteArtistNotFound() throws SpotifyException {
        // given
        expectedException.expect(NotFoundException.class);
        expectedException.expectMessage("NONEXISTENT ARTIST - Artist does not exist");

        // when
        Mockito.when(artistRepository.findById(1L)).thenReturn(Optional.empty());

        // then
        artistService.deleteArtist(1L);
    }


}