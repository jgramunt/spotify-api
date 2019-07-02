package com.jordi.spotify.utils.constants;

public class RestConstants {

    public static final String APPLICATION_NAME = "/spotify";
    public static final String API_VERSION_1 = "/v1";
    public static final String SUCCESS = "Success";

    public static final String RESOURCE_CATEGORY = "/categories";
    public static final String RESOURCE_ID = "/{id}";
    public static final String RESOURCE_NUMBER = "/{number}";
    public static final String RESOURCE_ACTORS = "/actors";



    public static final String PARAMETER_CATEGORY = "categories";

    private RestConstants() {
        throw new IllegalStateException("Utility Class");
    }

}
