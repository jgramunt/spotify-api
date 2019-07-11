package com.jordi.spotify.entities;

import javax.persistence.*;

public class Album {
    @Entity
    @Table(name = "ALBUMS")
    public class Artist {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name="NAME")
        private String name;

        public Artist() {}

        public Artist(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
