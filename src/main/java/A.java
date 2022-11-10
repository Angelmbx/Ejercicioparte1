import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

public class A {

    package es.teis.dataXML;

import es.teis.data.exceptions.LecturaException;
import es.teis.model.Partido;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

    public class DOMXMLService implements IXMLService {

        private static final String PARTIDO_TAG = "partido";

        Long id = 0l;
        String nombre = ""; int votos = 0; float porcentaje = 0;

        @Override
        public ArrayList<Partido> leerPartidos(String ruta, float umbral) throws LecturaException {

            try {
                File inputFile = new File(ruta);

                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(inputFile);

                doc.getDocumentElement().normalize();

                NodeList nList = doc.getElementsByTagName(PARTIDO_TAG);

                for (int i = 0; i < nList.getLength(); i++) {
                    Node nNode = nList.item(i);

                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;

                        porcentaje = Float.parseFloat(eElement.getElementsByTagName(PARTIDO_VOTOS_PORC_TAG).item(0).getTextContent());
                        if (porcentaje>umbral) {
                            id = Long.valueOf(eElement.getAttribute(PARTIDO_ATT_ID));
                            nombre = eElement.getElementsByTagName(PARTIDO_NOMBRE_TAG).item(0).getTextContent();
                            votos = Integer.parseInt(eElement.getElementsByTagName(PARTIDO_VOTOS_NUM_TAG).item(0).getTextContent());


                            Partido partido = new Partido(id, nombre, votos, porcentaje);


                            leerPartidos(ruta, umbral).add(partido);
                        }
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            return leerPartidos(ruta, umbral);
        }
    }


}
