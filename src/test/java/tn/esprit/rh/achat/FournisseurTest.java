package tn.esprit.rh.achat;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.rh.achat.entities.Fournisseur;
import tn.esprit.rh.achat.repositories.FournisseurRepository;
import tn.esprit.rh.achat.services.FournisseurServiceImpl;
import tn.esprit.rh.achat.entities.CategorieFournisseur;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@ContextConfiguration(classes = {FournisseurServiceImpl.class})
@ExtendWith(SpringExtension.class)
public class FournisseurTest {

    @MockBean
    private FournisseurRepository fournisseurRepository;

    @Autowired
    private FournisseurServiceImpl fournisseurService;

    // Sample data for tests
    Fournisseur fournisseur = Fournisseur.builder()
            .idFournisseur(1L)
            .code("12345")
            .libelle("Example Fournisseur")
            .categorieFournisseur(CategorieFournisseur.ORDINAIRE)
            .factures(new HashSet<>())
            .secteurActivites(new HashSet<>())
            .detailFournisseur(null)
            .build();

    @Test
    @Order(1)
    void retrieveAllFournisseurs() {
        List<Fournisseur> fournisseurList = new ArrayList<>();
        when(fournisseurRepository.findAll()).thenReturn(fournisseurList);
        List<Fournisseur> actualFournisseurList = fournisseurService.retrieveAllFournisseurs();
        assertSame(fournisseurList, actualFournisseurList);
        assertTrue(actualFournisseurList.isEmpty());
        verify(fournisseurRepository).findAll();
        log.info("ret ==> " + fournisseur.toString());
    }

    @Test
    @Order(2)
    void addFournisseur() {
        // Mocking the save method to capture the Fournisseur object
        Mockito.when(fournisseurRepository.save(Mockito.any(Fournisseur.class))).then(invocation -> {
            Fournisseur fournisseur = invocation.getArgument(0);
            fournisseur.setIdFournisseur(1L); // Assuming a setter method for ID exists
            return fournisseur;
        });

        log.info("Avant ==> " + fournisseur.toString());
        Fournisseur addedFournisseur = fournisseurService.addFournisseur(fournisseur);

        assertEquals(addedFournisseur, fournisseur);

        verify(fournisseurRepository).save(fournisseur);

        log.info("Après ==> " + fournisseur.toString());
    }

    @Test
    @Order(3)
    void deleteFournisseur() {
        Long id = 1L;
        fournisseurService.deleteFournisseur(id);
        verify(fournisseurRepository).deleteById(id);
    }

    @Test
    @Order(4)
    void updateFournisseur() {
        Fournisseur updatedFournisseur = Fournisseur.builder()
                .idFournisseur(1L)
                .code("54321")
                .libelle("Updated Fournisseur")
                .categorieFournisseur(CategorieFournisseur.ORDINAIRE)
                .factures(new HashSet<>())
                .secteurActivites(new HashSet<>())
                .detailFournisseur(null)
                .build();

        when(fournisseurRepository.save(any(Fournisseur.class))).thenReturn(updatedFournisseur);
        Fournisseur returnedFournisseur = fournisseurService.updateFournisseur(updatedFournisseur);
        assertEquals(returnedFournisseur, updatedFournisseur);
        verify(fournisseurRepository).save(updatedFournisseur);
    }

    @Test
    @Order(5)
    void retrieveFournisseur() {
        Long id = 1L;
        when(fournisseurRepository.findById(id)).thenReturn(Optional.of(fournisseur));
        Fournisseur foundFournisseur = fournisseurService.retrieveFournisseur(id);
        assertSame(foundFournisseur, fournisseur);
        verify(fournisseurRepository).findById(id);
        log.info("ret ==> " + fournisseur.toString());
    }
}
