package com.jordi.spotify.json;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArtistRest implements Serializable {

    private static final long serialVersionUID = -870160837236964362L;

    private Long id;
    private String name;

    public ArtistRest(){}

    public ArtistRest(Long id, String name) {
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
