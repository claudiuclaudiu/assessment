package ro.gshmedia.xml.parser;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ro.gshmedia.xml.model.Stock;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class StockParser {

    private static final String PRODUCT_ID_TAG = "product_id";
    private static final String STOCK_TAG = "stock";
    private static final String QUANTITY_TAG = "quantity";

    public List<Stock> readStocks(InputStream file) {


        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {

            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();


            NodeList list = doc.getElementsByTagName(STOCK_TAG);


            List<Stock> stockList = new ArrayList<>();


            for (int temp = 0; temp < list.getLength(); temp++) {

                Node node = list.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) node;

                    int productId = Integer.parseInt(element.getElementsByTagName(PRODUCT_ID_TAG).item(0).getTextContent());
                    int quantity = Integer.parseInt(element.getElementsByTagName(QUANTITY_TAG).item(0).getTextContent());


                    Stock stock1 = new Stock(productId, quantity);
                    stockList.add(stock1);


                }

            }
            return stockList;

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }


    public void moveToProcessedFolder(String sourcePath, String destinationPath, String filename) {

        File sourceFile = new File(sourcePath + "/" + filename);
        File destinationFile = new File(destinationPath + "/" + filename);
        try {
            FileUtils.moveFile(sourceFile, destinationFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
