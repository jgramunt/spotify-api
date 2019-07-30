package com.jordi.spotify.services.creators;

import com.jordi.spotify.entities.Album;
import com.jordi.spotify.entities.Song;
import com.jordi.spotify.json.song.UserInputSongRest;
import com.jordi.spotify.repositories.AlbumRepository;
import com.jordi.spotify.repositories.ArtistRepository;
import com.jordi.spotify.repositories.SongRepository;
import org.apache.catalina.User;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;

@SpringBootTest
public class SongCreatorTest {

    @Mock
    SongRepository songRepository;

    @Mock
    ArtistRepository artistRepository;

    @Mock
    AlbumRepository albumRepository;

    @InjectMocks
    SongCreator songCreator;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();


    @Test
    public void returnsSongType() {
        // given
        UserInputSongRest userInputSongRest = new UserInputSongRest();
        userInputSongRest.setName("Pet Sounds");

        // when
        Song newSong = songCreator.createSong(userInputSongRest);

        //then
        assertNotNull(newSong);
        assertEquals(Song.class, newSong.getClass());
    }

    @Test
    public void createsName() {
        // given
        UserInputSongRest userInputSongRest = new UserInputSongRest();
        userInputSongRest.setName("Here, There And Everywhere");

        // when
        Song newSong = songCreator.createSong(userInputSongRest);

        //then
        System.out.println(newSong.toString());
        assertEquals(newSong.getName(), userInputSongRest.getName());

    }

}