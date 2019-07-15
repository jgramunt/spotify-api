package com.jordi.spotify.json.album;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AlbumCreateRest implements Serializable {

    private static final long serialVersionUID = 3177317750566878350L;

    private String name;

    public AlbumCreateRest() {}

    public AlbumCreateRest(String name) { this.name = name; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
