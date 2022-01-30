import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.File;
import java.io.IOException;

import java.math.BigDecimal;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * XML file parser for Currency Converter app.
 * <p>Main task of this class is to take currencies and their rates from given .xml file.</p>
 * <p>Requested file should match the <a href="https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml">template</a> requirements.</p>
 *
 * @author Mateusz Prill
 * @version 1.0
 */
public class XMLParser {

    /**
     * Static method for retrieving currencies and rates from xml file.
     * <p>It can make application to quit when there are problems with xml file.</p>
     * <p>It's intentional action as Calculator application cannot work without this file</p>
     *
     * @param filePath String with path to xml file
     * @return Sorted Map of currencies as String keys and rates as their value in BigDecimal format
     */
    public static SortedMap<String, BigDecimal> getCurrencies (String filePath) {
        SortedMap<String, BigDecimal> currencyRateMap = new TreeMap<>();

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        try{
            // process XML securely, avoid attacks like XML External Entities (XXE)
            documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File(filePath));

            //Get all element with tag Cube
            NodeList nodeList = document.getElementsByTagName("Cube");

            for (int i = 0; i < nodeList.getLength(); i++) {

                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    //Already checked if node is Element, casting is safe
                    Element element = (Element) node;

                    String currency = element.getAttribute("currency");

                    //There can be Cube elements without currency attribute - omit them
                    if(!currency.equals("")){
                        BigDecimal rate = new BigDecimal(element.getAttribute("rate"));

                        currencyRateMap.put(currency, rate);
                    }

                }

            }
        } catch (ParserConfigurationException e) {
            System.out.println("There was a problem with parser configuration.");
            e.printStackTrace();
            System.exit(2);
        } catch (SAXException e) {
            System.out.println("There was a problem during parsing xml file. Check XML file.");
            e.printStackTrace();
            System.exit(3);
        } catch (IOException e) {
            System.out.println("There was a problem with a file. Check if XML file exist.");
            e.printStackTrace();
            System.exit(4);
        } catch (NumberFormatException e) {
            System.out.println("Error during parsing data from xml file. Is it formatted properly?");
            e.printStackTrace();
            System.exit(5);
        }

        return currencyRateMap;
    }
}
