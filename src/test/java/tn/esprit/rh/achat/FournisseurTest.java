package tn.esprit.rh.achat;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.rh.achat.entities.DetailFournisseur;
import tn.esprit.rh.achat.entities.Fournisseur;
import tn.esprit.rh.achat.entities.SecteurActivite;
import tn.esprit.rh.achat.repositories.DetailFournisseurRepository;
import tn.esprit.rh.achat.repositories.FournisseurRepository;
import tn.esprit.rh.achat.repositories.ProduitRepository;
import tn.esprit.rh.achat.repositories.SecteurActiviteRepository;
import tn.esprit.rh.achat.services.FournisseurServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class FournisseurTest {

    @Mock
    private FournisseurRepository fournisseurRepository;

    @Mock
    private DetailFournisseurRepository detailFournisseurRepository;

    @Mock
    private SecteurActiviteRepository secteurActiviteRepository;

    @InjectMocks
    private FournisseurServiceImpl fournisseurService;

    @Test
    public void testRetrieveAllFournisseurs() {
        // Mocking the repository
        List<Fournisseur> fournisseurList = new ArrayList<>();
        when(fournisseurRepository.findAll()).thenReturn(fournisseurList);

        // Calling the service method
        List<Fournisseur> result = fournisseurService.retrieveAllFournisseurs();

        // Verifying the results
        assertEquals(fournisseurList, result);
        verify(fournisseurRepository).findAll();
    }

    @Test
    public void testAddFournisseur() {
        // Mocking the repository
        Fournisseur fournisseur = new Fournisseur();
        when(fournisseurRepository.save(any(Fournisseur.class))).thenReturn(fournisseur);

        // Calling the service method
        Fournisseur result = fournisseurService.addFournisseur(new Fournisseur());

        // Verifying the results
        assertEquals(fournisseur, result);
        verify(fournisseurRepository).save(any(Fournisseur.class));
    }
    
    @Test
    public void testDeleteFournisseur() {
        // Calling the service method
        fournisseurService.deleteFournisseur(1L);

        // Verifying the results
        verify(fournisseurRepository).deleteById(1L);
    }


}
