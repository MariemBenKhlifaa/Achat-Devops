package tn.esprit.rh.achat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import tn.esprit.rh.achat.entities.Facture;
import tn.esprit.rh.achat.repositories.FactureRepository;
import tn.esprit.rh.achat.services.FactureServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FactureTest {

    @InjectMocks
    private FactureServiceImpl factureService;

    @Mock
    private FactureRepository factureRepository;

    @Test
    public void testRetrieveAllFactures() {
        // Arrange
        List<Facture> factures = Arrays.asList(new Facture(), new Facture());
        Mockito.when(factureRepository.findAll()).thenReturn(factures);

        // Act
        List<Facture> result = factureService.retrieveAllFactures();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testAddFacture() {
        // Arrange
        Facture facture = new Facture();
        Mockito.when(factureRepository.save(Mockito.any(Facture.class))).thenReturn(facture);

        // Act
        Facture result = factureService.addFacture(facture);

        // Assert
        assertNotNull(result);
        verify(factureRepository, Mockito.times(1)).save(facture);
    }

  /*  @Test
    public void testCancelFacture() {
        // Arrange
        Long factureId = 1L;
        Facture facture = new Facture();
        Mockito.when(factureRepository.findById(factureId)).thenReturn(Optional.of(facture));

        // Act
        factureService.cancelFacture(factureId);

        // Assert
        assertTrue(facture.isArchivee());
        verify(factureRepository, Mockito.times(1)).save(facture);
    }*/

    @Test
    public void testRetrieveFacture() {
        // Arrange
        Long factureId = 1L;
        Facture facture = new Facture();
        Mockito.when(factureRepository.findById(factureId)).thenReturn(Optional.of(facture));

        // Act
        Facture result = factureService.retrieveFacture(factureId);

        // Assert
        assertNotNull(result);
        verify(factureRepository, Mockito.times(1)).findById(factureId);
    }
}




