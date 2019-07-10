package com.jordi.spotify.json.artist;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArtistCreateRest implements Serializable {

    private static final long serialVersionUID = -5315177547027094619L;

    private String name;

    public ArtistCreateRest(){}

    public ArtistCreateRest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

