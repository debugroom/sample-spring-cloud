package org.debugroom.sample.spring.cloud.app.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

public enum MediaType {

    APPLICATION_ATOM_XML(com.google.common.net.MediaType.ATOM_UTF_8.toString()),
    APPLICATION_FORM_URLENCODED(com.google.common.net.MediaType.FORM_DATA.toString()),
    APPLICATION_JSON(com.google.common.net.MediaType.JSON_UTF_8.toString()),
    APPLICATION_OCTET_STREAM(com.google.common.net.MediaType.OCTET_STREAM.toString()),
    APPLICATION_SVG_XML(com.google.common.net.MediaType.SVG_UTF_8.toString()),
    APPLICATION_XHTML_XML(com.google.common.net.MediaType.XHTML_UTF_8.toString()),
    APPLICATION_XML(com.google.common.net.MediaType.XML_UTF_8.toString()),
    MP4_VIDEO(com.google.common.net.MediaType.MP4_VIDEO.toString()),
    MPEG_VIDEO(com.google.common.net.MediaType.MPEG_VIDEO.toString()),
    QUICKTIME_VIDEO(com.google.common.net.MediaType.QUICKTIME.toString()),
    WMV(com.google.common.net.MediaType.WMV.toString()),
    BMP(com.google.common.net.MediaType.BMP.toString()),
    PNG(com.google.common.net.MediaType.PNG.toString()),
    JPEG(com.google.common.net.MediaType.JPEG.toString()),
    GIF(com.google.common.net.MediaType.GIF.toString()),
    TIFF(com.google.common.net.MediaType.TIFF.toString());

    public final String VALUE;
    private static final String EXTENSION = ".";
    private static Map<String, MediaType> cache = new ConcurrentHashMap<>();

    MediaType(String value){
        this.VALUE = value;
    }

    static{
        cache.put(APPLICATION_ATOM_XML.VALUE, APPLICATION_ATOM_XML);
        cache.put(APPLICATION_FORM_URLENCODED.VALUE, APPLICATION_FORM_URLENCODED);
        cache.put(APPLICATION_JSON.VALUE, APPLICATION_JSON);
        cache.put(APPLICATION_OCTET_STREAM.VALUE, APPLICATION_OCTET_STREAM);
        cache.put(APPLICATION_SVG_XML.VALUE, MediaType.APPLICATION_SVG_XML);
        cache.put(APPLICATION_XHTML_XML.VALUE, MediaType.APPLICATION_XHTML_XML);
        cache.put(APPLICATION_XML.VALUE, MediaType.APPLICATION_XML);
        cache.put(MP4_VIDEO.VALUE, MediaType.MP4_VIDEO);
        cache.put(MPEG_VIDEO.VALUE, MediaType.MPEG_VIDEO);
        cache.put(WMV.VALUE, MediaType.WMV);
        cache.put(BMP.VALUE, MediaType.BMP);
        cache.put(PNG.VALUE, MediaType.PNG);
        cache.put(JPEG.VALUE, MediaType.JPEG);
        cache.put(GIF.VALUE, MediaType.GIF);
        cache.put(TIFF.VALUE, MediaType.TIFF);
    }

    public static MediaType getInstance(String type){
        return cache.get(type);
    }

    public static MediaType getMediaType(String fileName){
        String extension = StringUtils.substringAfterLast(fileName, EXTENSION);
        switch(extension.toLowerCase()){
            case "xml":
                return cache.get(APPLICATION_ATOM_XML.VALUE);
            case "json":
                return cache.get(APPLICATION_JSON.VALUE);
            case "jpg":
                return cache.get(JPEG.VALUE);
            case "jpeg":
                return cache.get(JPEG.VALUE);
            case "png":
                return cache.get(PNG.VALUE);
            case "gif":
                return cache.get(GIF.VALUE);
            case "tiff":
                return cache.get(TIFF.VALUE);
            case "bmp":
                return cache.get(BMP.VALUE);
            case "mp4":
                return cache.get(MP4_VIDEO.VALUE);
            case "m4v":
                return cache.get(MPEG_VIDEO.VALUE);
            case "mov":
                return cache.get(MPEG_VIDEO.VALUE);
            case "wmv":
                return cache.get(WMV.VALUE);
        }
        return null;
    }

}
