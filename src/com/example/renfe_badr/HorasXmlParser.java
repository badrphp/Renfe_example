package com.example.renfe_badr;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;
//Parseamos una lista de horas recibida por el servidor

public class HorasXmlParser {
	
	 private static final String ns = null;
	 
	 public List<Hora> parse(InputStream in) throws XmlPullParserException, IOException {
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

	    private List<Hora> readList(XmlPullParser parser) throws XmlPullParserException, IOException {
	        List<Hora> entries = new ArrayList<Hora>();

	        parser.require(XmlPullParser.START_TAG, ns, "list");
	        while (parser.next() != XmlPullParser.END_TAG) {
	            if (parser.getEventType() != XmlPullParser.START_TAG) {
	                continue;
	            }
	            String name = parser.getName();
	            System.out.println("El nombre del parser es: "+name);
	            // Starts by looking for the entry tag4
	            if (name.equals("string")) {
	            	 System.out.println("El nombre del parser es igual ");
	                entries.add(readHora(parser));
	            } else {
	                skip(parser);
	            }
	        }
	        return entries;
	    }
	    
	    

	    // This class represents a single user in the XML users.
	    // It includes the data train "type," "departure," line "departure hour."
	    public static class Hora {
	    	
	        public final String horaSalida;
			public final String horaLlegada;
	    	
	    	

	        private Hora(String horaSalida, String horaLlegada) {
	            
	            this.horaSalida = horaSalida;
	            this.horaLlegada = horaLlegada;
	            
	 
	        }
	    }

	    // Parses the contents of an models.User. If it encounters a email, password, or fullname, hands them
	    // off
	    // to their respective &quot;read&quot; methods for processing. Otherwise, skips the tag.
	    private Hora readHora(XmlPullParser parser) throws XmlPullParserException, IOException {
	        
			        String horaS = null;
			        String horaL = null;
			        String horaT= null;
			       
	       
	        	 System.out.println("Despues del while parser next");
	        	
	        		horaT = readhora(parser);
	                System.out.println("Hora total:"+horaT);
	                String aux[]=horaT.split("/");
		            
		            horaS=aux[0];
		            horaL=aux[1];
	           
	        
	        return new Hora(horaS,horaL);
	    }
	    private String readhora(XmlPullParser parser) throws IOException, XmlPullParserException {
	        parser.require(XmlPullParser.START_TAG, ns, "string");
	        String hora = readText(parser);
	        System.out.println(hora);
	        parser.require(XmlPullParser.END_TAG, ns, "string");
	        return hora;
	    }

	    
	    // For the tags tipo, 9 and platform, extracts their text values.
	    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
	        String result = "";
	        if (parser.next() == XmlPullParser.TEXT) {
	        	 System.out.println("Después del  parser next");
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
