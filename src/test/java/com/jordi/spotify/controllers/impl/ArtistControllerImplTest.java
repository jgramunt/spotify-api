package com.jordi.spotify.controllers.impl;

import com.jordi.spotify.controllers.ArtistController;
import com.jordi.spotify.entities.Artist;
import com.jordi.spotify.exceptions.NotFoundException;
import com.jordi.spotify.json.ArtistRest;
import com.jordi.spotify.services.ArtistService;
import com.jordi.spotify.utils.constants.ExceptionConstants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
}