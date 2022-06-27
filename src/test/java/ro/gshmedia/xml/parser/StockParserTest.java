package ro.gshmedia.xml.parser;

import org.junit.jupiter.api.Test;
import ro.gshmedia.xml.model.Stock;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StockParserTest {

    private static final String TEST_FILE_1 = "stock_new/stock_test_1.xml";

    private static final String TEST_FILE_01 = "stock_new/stock_1.xml";
    private static final String TEST_FILE_02 = "stock_new/stock_2.xml";
    private static final String TEST_FILE_03 = "stock_new/stock_3.xml";
    private final StockParser stockParser = new StockParser();


    @Test
    void testReadFromFile() {

        List<Stock> stockList = getStockListFromFile(TEST_FILE_1);
        assertThat(stockList, hasSize(3));
    }

    @Test
    void testReadFromFile_InvalidFormat() {
        List<Stock> stockList = getStockListFromFile(TEST_FILE_01);
        assertThat(stockList, hasSize(0));
    }

    @Test
    void testReadFromFile_NoStock() {

        List<Stock> stockList = getStockListFromFile(TEST_FILE_02);
        assertThat(stockList, hasSize(0));
    }

    @Test
    void testReadFromFile_stockIncorrectFormat() {
        assertThrows(NullPointerException.class,
                () -> getStockListFromFile(TEST_FILE_03));

    }


    //test pentru a verifica daca un fisier este mutat sau nu
    @Test
    void moveToProcessedFolder() throws IOException {

        // cream un fisier in resources/stock_new
        String source = getClass().getResource("/stock_new").getPath();
        File file = new File( source+ "/test_stock.xml");
        file.createNewFile();

        // verificam sa se creaza
        assertTrue(file.exists());


        // aici este salvat dupa ce e procesat
        String destination = getClass().getResource("/stock_processed").getPath();

        // apelam metoda de procesare
        stockParser.moveToProcessedFolder(source, destination, "test_stock.xml");
        File f = new File(destination + "/test_stock.xml");

        // verificam ca este mutat
        assertTrue(f.exists());

        // il stergem
        assertTrue(f.delete());
    }

    private List<Stock> getStockListFromFile(String testFile01) {
        return stockParser.readStocks(getClass().getClassLoader().getResourceAsStream(testFile01));
    }

}
