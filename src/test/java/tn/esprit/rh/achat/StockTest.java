package tn.esprit.rh.achat;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.rh.achat.entities.Stock;
import tn.esprit.rh.achat.repositories.StockRepository;
import tn.esprit.rh.achat.services.StockServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class StockTest {

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private StockServiceImpl stockService;

    @Test
    void testRetrieveAllStocks() {
        // Créez une liste de stocks simulés pour simuler la réponse de la base de données
        List<Stock> mockStockList = new ArrayList<>();
        mockStockList.add(new Stock(/* initialiser avec des données appropriées */));
        // configurez le comportement simulé du stockRepository
        when(stockRepository.findAll()).thenReturn(mockStockList);

        // Appelez la méthode du service
        List<Stock> result = stockService.retrieveAllStocks();

        // Vérifiez les résultats
        assertNotNull(result);
        assertEquals(1, result.size());
        // Vous pouvez effectuer d'autres vérifications en fonction de la structure de votre projet.
    }

    @Test
    void testAddStock() {
        // Créez un stock simulé pour simuler l'ajout
        Stock mockStock = new Stock(/* initialiser avec des données appropriées */);
        // configurez le comportement simulé du stockRepository
        when(stockRepository.save(any())).thenReturn(mockStock);

        // Appelez la méthode du service
        Stock result = stockService.addStock(mockStock);

        // Vérifiez les résultats
        assertNotNull(result);
        // Vous pouvez effectuer d'autres vérifications en fonction de la structure de votre projet.
    }

    @Test
    void testDeleteStock() {
        // Appelez la méthode du service
        stockService.deleteStock(1L);

        // Vérifiez que la méthode du repository a été appelée avec le bon argument
        verify(stockRepository, times(1)).deleteById(1L);
    }

    @Test
    void testUpdateStock() {
        // Create a stock simulated for updating
        Stock mockStock = new Stock(/* initialize with appropriate data */);

        // Configure the simulated behavior of the stockRepository.save
        when(stockRepository.save(any(Stock.class))).thenReturn(mockStock);

        // Call the service method
        Stock result = stockService.updateStock(mockStock);

        // Verify that the save method was called with the correct stock object
        verify(stockRepository, times(1)).save(eq(mockStock));

        // Verify the result
        assertNotNull(result);
        // You can perform additional verifications based on your project's structure.
    }


    @Test
    void testRetrieveStatusStock() {
        // Créez une liste de stocks simulés pour simuler la réponse de la base de données
        List<Stock> mockStockList = new ArrayList<>();
        mockStockList.add(new Stock(/* initialiser avec des données appropriées */));
        // configurez le comportement simulé du stockRepository
        when(stockRepository.retrieveStatusStock()).thenReturn(mockStockList);

        // Appelez la méthode du service
        String result = stockService.retrieveStatusStock();

        // Vérifiez les résultats
        assertNotNull(result);
        // Vous pouvez effectuer d'autres vérifications en fonction de la structure de votre projet.
    }
}