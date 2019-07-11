package com.jordi.spotify.controllers.impl;

import com.jordi.spotify.controllers.AlbumController;
import com.jordi.spotify.controllers.ArtistController;
import com.jordi.spotify.entities.Album;
import com.jordi.spotify.json.AlbumRest;
import com.jordi.spotify.services.AlbumService;
import org.junit.Before;
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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(AlbumController.class)
public class AlbumControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlbumService albumService;

    @InjectMocks
    private AlbumControllerImpl albumController;

    private String appversion = "/spotify/v1/";

    private Album album;
    private AlbumRest albumRest;

    private Album album2;
    private AlbumRest albumRest2;

    private List<AlbumRest> albumRestList;

    private Album albumWithoutId;


    @Before
    public void setup() {

        album = new Album(1L, "Definitely Maybe");
        albumRest = new AlbumRest(1L, "Definitely Maybe");
        album2 = new Album(2L, "Let It Be");
        albumRest2 = new AlbumRest(2L, "Let It Be");

        albumRestList = new ArrayList<>();
        albumRestList.add(albumRest);
        albumRestList.add(albumRest2);

        albumWithoutId = new Album();
        albumWithoutId.setName("Hell Freezes Over");
    }

    @Test
    public void getAlbumsWorksFine() throws Exception {
        // given
        albumRest = new AlbumRest(1L, "Definitely Maybe");
        albumRest2 = new AlbumRest(2L, "Let It Be");
        albumRestList = new ArrayList<>();
        albumRestList.add(albumRest);
        albumRestList.add(albumRest2);

        // when
        Mockito.when(albumService.getAll()).thenReturn(albumRestList);

        // then
        mockMvc.perform(get(appversion + "albums").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("Success"))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].id").value("1"))
                .andExpect(jsonPath("$.data[0].name").value("Definitely Maybe"))
                .andExpect(jsonPath("$.data[1].id").value("2"))
                .andExpect(jsonPath("$.data[1].name").value("Let It Be"));
    }

}