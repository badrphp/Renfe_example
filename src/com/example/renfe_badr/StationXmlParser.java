package com.example.renfe_badr;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

//Aqui es donde parseamos las estaciones dado un fixeor xml del servidor
public class StationXmlParser {
	private static final String ns = null;

    // We don't use namespaces

    public List<Station> parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readList(parser);
        } finally {
            in.close();
        }
    }

    private List<Station> readList(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<Station> entries = new ArrayList<Station>();

        parser.require(XmlPullParser.START_TAG, ns, "list");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            System.out.println("El nombre del parser es: "+name);
            // Starts by looking for the entry tag
            if (name.equals("models.Station")) {
            	 System.out.println("El nombre del parser es igual ");
                entries.add(readStation(parser));
            } else {
                skip(parser);
            }
        }
        return entries;
    }

    // This class represents a single user in the XML users.
    // It includes the data train "type," "departure," line "departure hour."
    public static class Station {
    	
        public final String estacion;
        
		
    	

        private Station(String estacion) {
            
            this.estacion = estacion;

        }
    }

    // Parses the contents of an models.User. If it encounters a email, password, or fullname, hands them
    // off
    // to their respective &quot;read&quot; methods for processing. Otherwise, skips the tag.
    private Station readStation(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "models.Station");
        String estacion = null;
       
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("station")) {
                estacion = readestacio(parser);
                System.out.println("Estacion:  "+estacion);
           
            } else {
                skip(parser);
            }
        }
        return new Station(estacion);
    }

    // Processes tipo tags in the feed.
    private String readestacio(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "station");
        String estacion = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "station");
        return estacion;
    }

    

    // For the tags tipo, 9 and platform, extracts their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    // Skips tags the parser isn't interested in. Uses depth to handle nested tags. i.e.,
    // if the next tag after a START_TAG isn't a matching END_TAG, it keeps going until it
    // finds the matching END_TAG (as indicated by the value of "depth" being 0).
    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
            case XmlPullParser.END_TAG:
                    depth--;
                    break;
            case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

}
