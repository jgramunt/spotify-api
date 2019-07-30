package com.jordi.spotify.services.creators;

import com.jordi.spotify.entities.Song;
import com.jordi.spotify.json.song.UserInputSongRest;
import com.jordi.spotify.repositories.AlbumRepository;
import com.jordi.spotify.repositories.ArtistRepository;
import com.jordi.spotify.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SongCreator {

    @Autowired
    SongRepository songRepository;

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    ArtistRepository artistRepository;

    public Song createSong(UserInputSongRest userInputSongRest) {
        Song newSong = new Song();
        newSong.setName(userInputSongRest.getName());
        Song savedNewSong = songRepository.save(newSong);
        return savedNewSong;
    }

}
