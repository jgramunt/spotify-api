package com.jordi.spotify.controllers.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.jordi.spotify.entities.Song;
import com.jordi.spotify.exceptions.DuplicateEntryException;
import com.jordi.spotify.exceptions.NotFoundException;
import com.jordi.spotify.exceptions.SpotifyException;
import com.jordi.spotify.json.SongRest;
import com.jordi.spotify.json.song.CreateSongRest;
import com.jordi.spotify.services.SongService;
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
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.HandlerResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.HttpRequestHandler;

import javax.print.attribute.standard.Media;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        songRest.setTrackNumber(2);

        // when
        Mockito.when(songService.getSongById(1L)).thenReturn(songRest);

        // then
        mockMvc.perform(get(appversion + "songs/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data.id").value("1"))
                .andExpect(jsonPath("$.data.name").value("Mr Blue Sky"))
                .andExpect(jsonPath("$.data.trackNumber").value("2"));
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

    @Test
    public void createSongWorksFine() throws Exception {
        // given
        CreateSongRest createSongRest = new CreateSongRest("Mr Blue Sky");
        SongRest songRest = new SongRest(1L, "Mr Blue Sky");

        // when
        Mockito.when(songService.createSong(any())).thenReturn(songRest);

        // then
        mockMvc.perform(post(appversion + "songs").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(createSongRest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("CREATED"))
                .andExpect(jsonPath("$.code").value("201"))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data.id").value("1"))
                .andExpect(jsonPath("$.data.name").value("Mr Blue Sky"));
    }

    @Test
    public void createSongFails() throws Exception {
        // when
        Mockito.when(songService.createSong(any())).thenThrow(new DuplicateEntryException(ExceptionConstants.MESSAGE_EXISTING_SONG));

        // then
        mockMvc.perform(post(appversion + "songs").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new CreateSongRest())))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.status").value("ERROR"))
                .andExpect(jsonPath("$.code").value("409"))
                .andExpect(jsonPath("$.message").value("SONG ALREADY EXIST - A song with the same name, artist and album does already exist"));
    }

    @Test
    public void updateSongWorksFine() throws Exception {
        // given
        CreateSongRest createSongRest = new CreateSongRest("Mr Blue Sky");
        SongRest songRest = new SongRest(1L, "Mr Blue Sky");

        // when
        Mockito.when(songService.updateSong(any(), any())).thenReturn(songRest);

        // then
        mockMvc.perform(patch(appversion + "songs/1").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(createSongRest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data.id").value("1"))
                .andExpect(jsonPath("$.data.name").value("Mr Blue Sky"));
    }

    @Test
    public void updateSongNotFound() throws Exception {
        // when
        Mockito.when(songService.updateSong(any(), any())).thenThrow(new NotFoundException(ExceptionConstants.MESSAGE_NONEXISTENT_SONG));

        // then
        mockMvc.perform(patch(appversion + "songs/1").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new CreateSongRest())))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.status").value("ERROR"))
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.message").value("NONEXISTENT SONG - Song does not exist"));
    }

    @Test
    public void deleteSongWorksFine() throws Exception {
        // when
        Mockito.when(songService.deleteSong(any())).thenReturn("Song deleted");

        // then
        mockMvc.perform(delete(appversion + "songs/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.status").value("NO CONTENT"))
                .andExpect(jsonPath("$.code").value("204"))
                .andExpect(jsonPath("$.message").value("Song deleted"));
    }

    @Test
    public void deleteSongNotFound() throws Exception {
        // when
        Mockito.when(songService.deleteSong(any())).thenThrow(new NotFoundException(ExceptionConstants.MESSAGE_NONEXISTENT_SONG));

        // then
        mockMvc.perform(delete(appversion + "songs/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.status").value("ERROR"))
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.message").value("NONEXISTENT SONG - Song does not exist"));
    }

    // PRIVATE
    private String asJsonString(Object object) {
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            return ow.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}