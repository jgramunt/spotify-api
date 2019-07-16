package com.jordi.spotify.controllers.impl;

import com.jordi.spotify.exceptions.NotFoundException;
import com.jordi.spotify.exceptions.SpotifyException;
import com.jordi.spotify.json.SongRest;
import com.jordi.spotify.services.SongService;
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
import org.springframework.test.web.servlet.result.HandlerResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.HttpRequestHandler;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(SongControllerImpl.class)
public class SongControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SongService songService;

    @InjectMocks
    private SongControllerImpl songController;

    private String appversion = "/spotify/v1/";

    @Test
    public void getSongsWorksFine() throws Exception {
        // given
        SongRest song1 = new SongRest(1L, "Mr Blue Sky");
        SongRest song2 = new SongRest(2L, "Hotel California");
        List<SongRest> songList = new ArrayList<>();
        songList.add(song1);
        songList.add(song2);

        // when
        Mockito.when(songService.getSongs()).thenReturn(songList);

        // then
        mockMvc.perform(get(appversion + "songs").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data[0].id").value("1"))
                .andExpect(jsonPath("$.data[0].name").value("Mr Blue Sky"))
                .andExpect(jsonPath("$.data[1].id").value("2"))
                .andExpect(jsonPath("$.data[1].name").value("Hotel California"));
    }

    @Test
    public void getSongByIdWorksFine() throws Exception {
        // given
        SongRest songRest = new SongRest(1L, "Mr Blue Sky");

        // when
        Mockito.when(songService.getSongById(1L)).thenReturn(songRest);

        // then
        mockMvc.perform(get(appversion + "songs/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data.id").value("1"))
                .andExpect(jsonPath("$.data.name").value("Mr Blue Sky"));

    }

    @Test
    public void getSongByIdFails() throws Exception {
        // when
        Mockito.when(songService.getSongById(any())).thenThrow(new NotFoundException(ExceptionConstants.MESSAGE_NONEXISTENT_SONG));

        // then
        mockMvc.perform(get(appversion + "songs/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("ERROR"))
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.message").value("NONEXISTENT SONG - Song does not exist"));
    }

}