package com.jordi.spotify.utils.constants;

public class ExceptionConstants {

    public static final String ERROR = "ERROR";

    public static final String MESSAGE_INEXISTENT_SEASON = "SEASON INEXISTENT - Season does not exist";
    public static final String MESSAGE_INEXISTENT_CHAPTER = "CHAPTER INEXISTENT - Chapter does not exist";
    public static final String MESSAGE_NONEXISTENT_ARTIST = "NONEXISTENT ARTIST - Artist does not exist";
    public static final String MESSAGE_INEXISTENT_CATEGORY = "CATEGORY INEXISTENT - Category does not exist";
    public static final String MESSAGE_INEXISTENT_TV_SHOW = "TV SHOW INEXISTENT - Category does not exist";



    public static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR - An internal server error has ocurred";

    private ExceptionConstants() {
        throw new IllegalStateException("Utility Class");
    }

}
