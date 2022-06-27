package ro.gshmedia.service;

import ro.gshmedia.data.dao.ProductDao;
import ro.gshmedia.data.dao.impl.ProductDaoImpl;
import ro.gshmedia.xml.model.Stock;
import ro.gshmedia.xml.parser.StockParser;

import java.util.List;

public class ProductService {


    private ProductDao productDao = new ProductDaoImpl();
    private StockParser stockParser = new StockParser();

    public void initProductStocks(String sourceFilename) {
        //prerequisites: in db avem deja toate produsele cu id-urile din fisierul xml
        String source = getClass().getResource("/stock_new").getPath();


        List<Stock> stocks = stockParser.readStocks(getClass().getClassLoader().getResourceAsStream(source + "/" + sourceFilename));

        String destination = getClass().getResource("/stock_processed").getPath();
        stockParser.moveToProcessedFolder(source, destination, sourceFilename);

        stocks.stream().forEach(e -> productDao.updateProductById(e.getProductId(), e.getQuantity()));



    }


}
