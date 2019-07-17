package com.jordi.spotify.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ALBUMS")
public class Album implements Serializable {

    private static final long serialVersionUID = 1514054414801740965L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="NAME")
    private String name;

    public Album() {}

    public Album(Long id, String name) {
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
