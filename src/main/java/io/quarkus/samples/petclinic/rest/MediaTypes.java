package io.quarkus.samples.petclinic.rest;

import javax.ws.rs.core.MediaType;

public class MediaTypes {

    public static final String APPLICATION_JSON_UTF8 = "application/json;charset=utf-8";
    public static final MediaType APPLICATION_JSON_UTF8_TYPE = new MediaType("application", "json", "utf-8");


}
