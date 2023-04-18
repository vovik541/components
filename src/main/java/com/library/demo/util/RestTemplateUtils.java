package com.library.demo.util;

import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class RestTemplateUtils {

    public static URI buildUrlForTemplate(String url){
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        return builder.buildAndExpand(new HashMap<>()).toUri();
    }

    public static URI buildUrlForTemplate(Map<String, String> urlParams, String url){
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        return builder.buildAndExpand(urlParams).toUri();
    }
    String url = "http://test.com/solarSystem/planets/{planet}/moons/{moon}";

}
