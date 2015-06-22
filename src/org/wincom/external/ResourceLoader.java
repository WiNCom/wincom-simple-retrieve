package org.wincom.external;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class ResourceLoader {

    public ResourceLoader() {

    }

    public static String getResource(String filename) {
        String resourcePath = "resources/" + filename;
        return decodeString(ResourceLoader.class.getClassLoader().getResource(resourcePath).getPath());
    }

    public static InputStream getResourceAsStream(String filename) {
        String resourcePath = "resources/" + filename;
        return ResourceLoader.class.getClassLoader().getResourceAsStream(resourcePath);
    }

    private static final String decodeString(String encodedString) {
        String decodedString = "";

        try {
            decodedString = URLDecoder.decode(encodedString, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return decodedString;
    }
}
