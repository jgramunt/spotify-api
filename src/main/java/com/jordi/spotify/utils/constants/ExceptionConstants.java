package com.jordi.spotify.utils.constants;

public class ExceptionConstants {

    public static final String ERROR = "ERROR";

    public static final String MESSAGE_INEXISTENT_SEASON = "SEASON INEXISTENT - Season does not exist";
    public static final String MESSAGE_INEXISTENT_CHAPTER = "CHAPTER INEXISTENT - Chapter does not exist";
    public static final String MESSAGE_INEXISTENT_CATEGORY = "CATEGORY INEXISTENT - Category does not exist";
    public static final String MESSAGE_INEXISTENT_TV_SHOW = "TV SHOW INEXISTENT - Category does not exist";

    public static final String MESSAGE_NONEXISTENT_ARTIST = "NONEXISTENT ARTIST - Artist does not exist";
    public static final String MESSAGE_EXISTING_ARTIST = "ARTIST ALREADY EXIST - An artist with the same name does already exist";
    public static final String MESSAGE_NONEXISTENT_ALBUM = "NONEXISTENT ALBUM - Album does not exist";
    public static final String MESSAGE_EXISTING_ALBUM = "ALBUM ALREADY EXIST - An album with the same name does already exist";
    public static final String MESSAGE_NONEXISTENT_SONG = "NONEXISTENT SONG - Song does not exist";
    public static final String MESSAGE_EXISTING_SONG = "SONG ALREADY EXIST - A song with the same name, artist and album does already exist";
    public static final String MESSAGE_EXISTING_TRACK_NUMBER = "TRACK NUMBER ALREADY EXIST - A song with the same track number for this album does already exist";




    public static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR - An internal server error has ocurred";

    private ExceptionConstants() {
        throw new IllegalStateException("Utility Class");
    }

}
