package server;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import java.io.InputStream;

public final class GloabResource {
    private static final String SERVER = "server.xml";
    public static Document serverDocument; //server.xml

    static {
        try {
            InputStream resourceAsStream = GloabResource.class.getClassLoader().getResourceAsStream(SERVER);
            SAXReader saxReader = new SAXReader();
            serverDocument = saxReader.read(resourceAsStream);
        } catch (Exception e) {
            e.printStackTrace();
            serverDocument = null;
        }
    }

}
