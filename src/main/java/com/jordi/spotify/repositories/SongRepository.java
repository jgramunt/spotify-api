package com.jordi.spotify.repositories;

import com.jordi.spotify.entities.Album;
import com.jordi.spotify.entities.Artist;
import com.jordi.spotify.entities.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

}
