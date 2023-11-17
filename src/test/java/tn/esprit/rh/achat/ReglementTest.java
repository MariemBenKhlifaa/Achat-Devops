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
import tn.esprit.rh.achat.entities.Reglement;
import tn.esprit.rh.achat.repositories.FactureRepository;
import tn.esprit.rh.achat.repositories.ReglementRepository;
import tn.esprit.rh.achat.services.ReglementServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
@ContextConfiguration(classes = {ReglementServiceImpl.class})
@ExtendWith(SpringExtension.class)
public class ReglementTest {

    @MockBean
    private FactureRepository factureRepository;

    @MockBean
    private ReglementRepository reglementRepository;

    @Autowired
    private ReglementServiceImpl reglementService;

    // Sample data for tests
    Reglement reglement = new Reglement();

    @Test
    @Order(1)
    void retrieveAllReglements() {
        List<Reglement> reglementList = new ArrayList<>();
        when(reglementRepository.findAll()).thenReturn(reglementList);
        List<Reglement> actualReglementList = reglementService.retrieveAllReglements();
        assertSame(reglementList, actualReglementList);
        assertTrue(actualReglementList.isEmpty());
        verify(reglementRepository).findAll();
        log.info("ret ==> " + reglement.toString());
    }

    @Test
    @Order(2)
    void addReglement() {
        // Mocking the save method to capture the Reglement object
        Mockito.when(reglementRepository.save(Mockito.any(Reglement.class))).then(invocation -> {
            Reglement reg = invocation.getArgument(0);
            reg.setIdReglement(1L); // Assuming a setter method for ID exists
            return reg;
        });

        log.info("Avant ==> " + reglement.toString());
        Reglement addedReglement = reglementService.addReglement(reglement);

        assertEquals(addedReglement, reglement);

        verify(reglementRepository).save(reglement);

        log.info("Après ==> " + reglement.toString());
    }

    @Test
    @Order(3)
    void retrieveReglement() {
        Long id = 1L;
        when(reglementRepository.findById(id)).thenReturn(java.util.Optional.ofNullable(reglement));
        Reglement foundReglement = reglementService.retrieveReglement(id);
        assertSame(foundReglement, reglement);
        verify(reglementRepository).findById(id);
        log.info("ret ==> " + reglement.toString());
    }

    // Add tests for other methods like deleteReglement, retrieveReglementByFacture, getChiffreAffaireEntreDeuxDate, etc.

}
