package com.jordi.spotify.controllers.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.jordi.spotify.controllers.AlbumController;
import com.jordi.spotify.exceptions.DuplicateEntryException;
import com.jordi.spotify.exceptions.NotFoundException;
import com.jordi.spotify.json.AlbumRest;
import com.jordi.spotify.json.album.AlbumCreateRest;
import com.jordi.spotify.json.album.AlbumRestWithSongs;
import com.jordi.spotify.json.album.AlbumSongRest;
import com.jordi.spotify.services.AlbumService;
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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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


    @Test
    public void getAlbumsWorksFine() throws Exception {
        // given
        AlbumRest albumRest = new AlbumRest(1L, "Definitely Maybe");
        AlbumRest albumRest2 = new AlbumRest(2L, "Let It Be");
        List<AlbumRest> albumRestList = new ArrayList<>();
        albumRestList.add(albumRest);
        albumRestList.add(albumRest2);

        // when
        Mockito.when(albumService.getAlbums()).thenReturn(albumRestList);

        // then
        mockMvc.perform(get(appversion + "albums").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].id").value("1"))
                .andExpect(jsonPath("$.data[0].name").value("Definitely Maybe"))
                .andExpect(jsonPath("$.data[1].id").value("2"))
                .andExpect(jsonPath("$.data[1].name").value("Let It Be"));
    }

    @Test
    public void getByIdWorksFine() throws Exception {
        // given
        AlbumSongRest albumSongRest1 = new AlbumSongRest();
        albumSongRest1.setId(1L);
        albumSongRest1.setArtistName("The Beatles");
        albumSongRest1.setName("Two Of Us");
        albumSongRest1.setTrackNumber(1);
        AlbumSongRest albumSongRest2 = new AlbumSongRest();
        albumSongRest2.setId(2L);
        albumSongRest2.setArtistName("The Beatles");
        albumSongRest2.setName("Dig A Pony");
        albumSongRest2.setTrackNumber(2);
        List<AlbumSongRest> albumSongRestList = new ArrayList<>();

        AlbumRestWithSongs albumRestWithSongs = new AlbumRestWithSongs(2L, "Let It Be");
        albumSongRestList.add(albumSongRest1);
        albumSongRestList.add(albumSongRest2);
        albumRestWithSongs.setSongs(albumSongRestList);

        // when
        Mockito.when(albumService.getAlbumById(2L)).thenReturn(albumRestWithSongs);

        // then
        mockMvc.perform(get(appversion + "albums/2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data.id").value("2"))
                .andExpect(jsonPath("$.data.name").value("Let It Be"))
                .andExpect(jsonPath("$.data.songs[0].name").value("Two Of Us"))
                .andExpect(jsonPath("$.data.songs[0].artistName").value("The Beatles"))
                .andExpect(jsonPath("$.data.songs[0].trackNumber").value("1"))
                .andExpect(jsonPath("$.data.songs[0].id").value("1"))
                .andExpect(jsonPath("$.data.songs[1].name").value("Dig A Pony"))
                .andExpect(jsonPath("$.data.songs[1].artistName").value("The Beatles"))
                .andExpect(jsonPath("$.data.songs[1].trackNumber").value("2"))
                .andExpect(jsonPath("$.data.songs[1].id").value("2"));
    }

    @Test
    public void getByIdNotFound() throws Exception {
        // when
        Mockito.when(albumService.getAlbumById(any())).thenThrow(new NotFoundException(ExceptionConstants.MESSAGE_NONEXISTENT_ALBUM));

        //then
        mockMvc.perform(get(appversion + "albums/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("ERROR"))
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.message").value("NONEXISTENT ALBUM - Album does not exist"));

    }

    @Test
    public void createAlbumWorksFine() throws Exception {
        // given
        AlbumCreateRest albumCreateRest = new AlbumCreateRest("Definitely Maybe");
        AlbumRest result = new AlbumRest(2L, "Definitely Maybe");

        // when
        Mockito.when(albumService.createAlbum(any())).thenReturn(result);

        // then
        mockMvc.perform(post(appversion + "albums").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(albumCreateRest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("CREATED"))
                .andExpect(jsonPath("$.code").value("201"))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data.id").value("2"))
                .andExpect(jsonPath("$.data.name").value("Definitely Maybe"));
    }

    @Test
    public void createAlbumFails() throws Exception {
        // when
        Mockito.when(albumService.createAlbum(any())).thenThrow(new DuplicateEntryException(ExceptionConstants.MESSAGE_EXISTING_ALBUM));

        //then
        mockMvc.perform(post(appversion + "albums").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new AlbumCreateRest())))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.status").value("ERROR"))
                .andExpect(jsonPath("$.code").value("409"))
                .andExpect(jsonPath("$.message").value("ALBUM ALREADY EXIST - An album with the same name does already exist"));
    }

    @Test
    public void updateAlbumWorksFine() throws Exception {
        // given
        AlbumRest modifiedAlbum = new AlbumRest();
        modifiedAlbum.setName("Let It Be");

        AlbumRest savedModifiedAlbum = new AlbumRest();
        savedModifiedAlbum.setId(1L);
        savedModifiedAlbum.setName("Let It Be");

        // when
        Mockito.when(albumService.updateAlbum(any(), any())).thenReturn(savedModifiedAlbum);

        // then
        mockMvc.perform(patch(appversion + "albums/1").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(modifiedAlbum)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data.id").value("1"))
                .andExpect(jsonPath("$.data.name").value("Let It Be"));
    }

    @Test
    public void updateAlbumNotFound() throws Exception {
        // given
        AlbumRest modifiedAlbum = new AlbumRest();
        modifiedAlbum.setName("Let It Be");

        // when
        Mockito.when(albumService.updateAlbum(any(), any())).thenThrow(new NotFoundException(ExceptionConstants.MESSAGE_NONEXISTENT_ALBUM));

        // then
        mockMvc.perform(patch(appversion + "albums/1").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(modifiedAlbum)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.status").value("ERROR"))
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.message").value("NONEXISTENT ALBUM - Album does not exist"));
    }

    @Test
    public void deleteAlbumWorksFine() throws Exception {
        // when
        Mockito.when(albumService.deleteAlbum(any())).thenReturn("Album deleted");

        // then
        mockMvc.perform(delete(appversion + "albums/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("NO CONTENT"))
                .andExpect(jsonPath("$.code").value("204"))
                .andExpect(jsonPath("$.message").value("Album deleted"));
    }

    @Test
    public void deleteAlbumNotFound() throws Exception {
        // when
        Mockito.when(albumService.deleteAlbum(any())).thenThrow(new NotFoundException(ExceptionConstants.MESSAGE_NONEXISTENT_ALBUM));

        // then
        mockMvc.perform(delete(appversion + "albums/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.status").value("ERROR"))
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.message").value("NONEXISTENT ALBUM - Album does not exist"));
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