package com.jordi.spotify.repositories;

import com.jordi.spotify.entities.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    Boolean existsByName(String name);
}
