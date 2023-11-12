package tn.esprit.rh.achat;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.rh.achat.entities.Stock;
import tn.esprit.rh.achat.repositories.StockRepository;
import tn.esprit.rh.achat.services.StockServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
@ContextConfiguration(classes = {StockServiceImpl.class})
@ExtendWith(SpringExtension.class)
public class StockTest {

    @MockBean
    private StockRepository stockRepository;

    @Autowired
    private StockServiceImpl stockService;

    // Sample data for tests
    Stock stock = Stock.builder().idStock(1L).libelleStock("SampleStock").qte(100).qteMin(10).build();

    @Test
    @Order(1)
    void retrieveAllStocks() {
        List<Stock> stockList = new ArrayList<>();
        when(stockRepository.findAll()).thenReturn(stockList);

        List<Stock> actualStockList = stockService.retrieveAllStocks();

        assertSame(stockList, actualStockList);
        assertTrue(actualStockList.isEmpty());
        verify(stockRepository).findAll();
    }

    @Test
    @Order(2)
    void addStock() {
        Mockito.when(stockRepository.save(Mockito.any(Stock.class))).then(invocation -> {
            Stock s = invocation.getArgument(0);
            s.setIdStock(1L); // Assuming a setter method for ID exists
            return s;
        });

        Stock addedStock = stockService.addStock(stock);

        assertEquals(addedStock, stock);
        verify(stockRepository).save(stock);
    }

    @Test
    @Order(3)
    void deleteStock() {
        Long stockId = 1L;
        stockService.deleteStock(stockId);
        verify(stockRepository).deleteById(stockId);
    }

    @Test
    @Order(4)
    void updateStock() {
        Stock updatedStock = Stock.builder().idStock(1L).libelleStock("UpdatedStock").qte(150).qteMin(20).build();
        when(stockRepository.save(Mockito.any(Stock.class))).thenReturn(updatedStock);

        Stock returnedStock = stockService.updateStock(updatedStock);

        assertEquals(returnedStock, updatedStock);
        verify(stockRepository).save(updatedStock);
    }

    @Test
    @Order(5)
    void retrieveStock() {
        Long stockId = 1L;
        when(stockRepository.findById(stockId)).thenReturn(Optional.of(stock));

        Stock foundStock = stockService.retrieveStock(stockId);

        assertSame(foundStock, stock);
        verify(stockRepository).findById(stockId);
    }

    @Test
    @Order(6)
    void retrieveStatusStock() {
        // Add necessary setup for the test data

        // Mock the repository method if needed

        // Act
        String result = stockService.retrieveStatusStock();

        // Assert
        // Add assertions based on the expected result
    }

    // Add more tests as needed for other methods

}
