package com.jordi.spotify.controllers.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.jordi.spotify.controllers.ArtistController;
import com.jordi.spotify.entities.Artist;
import com.jordi.spotify.exceptions.DuplicateEntryException;
import com.jordi.spotify.exceptions.NotFoundException;
import com.jordi.spotify.exceptions.SpotifyException;
import com.jordi.spotify.json.ArtistRest;
import com.jordi.spotify.json.artist.ArtistCreateRest;
import com.jordi.spotify.services.ArtistService;
import com.jordi.spotify.utils.constants.ExceptionConstants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ArtistController.class)
public class ArtistControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArtistService artistService;

    @InjectMocks
    private ArtistControllerImpl artistController;

    private String appversion = "/spotify/v1/";


    @Test
    public void getArtistsStatusReturnsOKStatus() throws Exception {
        mockMvc.perform(get(appversion + "artists").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Success"))
                .andExpect(jsonPath("$.code").value("200 OK"))
                .andExpect(jsonPath("$.message").value("OK"));
    }

    @Test
    public void doesGetAllArtists() throws Exception {
        // given
        List<ArtistRest> artistList = new ArrayList<>();
        artistList.add(new ArtistRest((long) 1, "Oasis"));
        artistList.add(new ArtistRest((long) 2, "Eagles"));

        // when
        Mockito.when(artistService.findAll()).thenReturn(artistList);

        // then
        mockMvc.perform(get(appversion + "artists").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].id").value("1"))
                .andExpect(jsonPath("$.data[0].name").value("Oasis"))
                .andExpect(jsonPath("$.data[1].id").value("2"))
                .andExpect(jsonPath("$.data[1].name").value("Eagles"));
    }

    @Test
    public void getByIdTest() throws Exception {
        // given
        ArtistRest artistRest = new ArtistRest(1L, "Oasis");

        // when
        Mockito.when(artistService.findById(1L)).thenReturn(artistRest);

        // then
        mockMvc.perform(get(appversion + "/artists/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Success"))
                .andExpect(jsonPath("$.code").value("200 OK"))
                .andExpect(jsonPath("$.message").value("OK"))

                .andExpect(jsonPath("$.data.id").value("1"))
                .andExpect(jsonPath("$.data.name").value("Oasis"));

    }

    @Test
    public void getByIdFails() throws Exception {
        // when
        Mockito.when(artistService.findById(any())).thenThrow(new NotFoundException(ExceptionConstants.MESSAGE_NONEXISTENT_ARTIST));

        // then
        mockMvc.perform(get(appversion + "artists/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.status").value("ERROR"))
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.message").value("NONEXISTENT ARTIST - Artist does not exist"));

    }

    @Test
    public void createArtistWorksFine() throws Exception {
        // given
        ArtistCreateRest createdArtist = new ArtistCreateRest();
        createdArtist.setName("Oasis");
        ArtistRest result = new ArtistRest(2L, "Oasis");

        // when
        Mockito.when(artistService.createArtist(any())).thenReturn(result);

        // then
        mockMvc.perform(post(appversion + "artists").contentType(MediaType.APPLICATION_JSON).content(asJsonString(createdArtist)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("CREATED"))
                .andExpect(jsonPath("$.code").value("201"))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data.id").value("2"))
                .andExpect(jsonPath("$.data.name").value("Oasis"));
    }

    @Test
    public void createArtistFails() throws Exception {
        // when
        Mockito.when(artistService.createArtist(any())).thenThrow(new DuplicateEntryException(ExceptionConstants.MESSAGE_EXISTING_ARTIST));

        //then
        mockMvc.perform(post(appversion + "artists").contentType(MediaType.APPLICATION_JSON).content(asJsonString(new ArtistCreateRest())))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.status").value("ERROR"))
                .andExpect(jsonPath("$.code").value("409"))
                .andExpect(jsonPath("$.message").value("ARTIST ALREADY EXIST - An artist with the same name does already exist"));
    }

    @Test
    public void updateArtistWorksFine() throws Exception {
        // given
        ArtistRest modifiedArtist = new ArtistRest();
        modifiedArtist.setName("The Beatles");

        ArtistRest savedModifiedArtist = new ArtistRest(1L, "The Beatles");

        // when
        Mockito.when(artistService.updateArtist(any(), any())).thenReturn(savedModifiedArtist);

        // then
        mockMvc.perform(patch(appversion + "artists/1").contentType(MediaType.APPLICATION_JSON).content(asJsonString(modifiedArtist)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Success"))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data.id").value("1"))
                .andExpect(jsonPath("$.data.name").value("The Beatles"));
    }

    @Test
    public void updateArtistNotFound() throws Exception {
        // given
        ArtistRest modifiedArtist = new ArtistRest();
        modifiedArtist.setName("The Beatles");

        // when
        Mockito.when(artistService.updateArtist(any(), any())).thenThrow(new NotFoundException(ExceptionConstants.MESSAGE_NONEXISTENT_ARTIST));

        // then
        mockMvc.perform(patch(appversion + "artists/1").contentType(MediaType.APPLICATION_JSON).content(asJsonString(modifiedArtist)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.status").value("ERROR"))
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.message").value("NONEXISTENT ARTIST - Artist does not exist"));

    }

    @Test
    public void deleteArtistWorksFine() throws Exception {

        // when
        Mockito.when(artistService.deleteArtist(any())).thenReturn("Artist deleted");
        // then
        mockMvc.perform(delete(appversion + "artists/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("NO CONTENT"))
                .andExpect(jsonPath("$.code").value("204"))
                .andExpect(jsonPath("$.message").value("Artist deleted"));
    }

    @Test
    public void deleteArtistNotFound() throws Exception {
        // when
        Mockito.when(artistService.deleteArtist(any())).thenThrow(new NotFoundException(ExceptionConstants.MESSAGE_NONEXISTENT_ARTIST));

        // then
        mockMvc.perform(delete(appversion + "artists/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.status").value("ERROR"))
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.message").value("NONEXISTENT ARTIST - Artist does not exist"));
    }

    // PRIVATE
    private String asJsonString(Object object) throws JsonProcessingException {
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            return ow.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}