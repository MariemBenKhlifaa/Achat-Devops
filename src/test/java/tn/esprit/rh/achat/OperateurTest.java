package tn.esprit.rh.achat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import tn.esprit.rh.achat.entities.Facture;
import tn.esprit.rh.achat.entities.Operateur;
import tn.esprit.rh.achat.repositories.OperateurRepository;
import tn.esprit.rh.achat.services.OperateurServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OperateurTest {

    @Mock
    private OperateurRepository operateurRepository;

    @InjectMocks
    private OperateurServiceImpl operateurService;

    @Test
    void retrieveAllOperateurs() {
        // Préparation de données fictives
        List<Operateur> operateurs = new ArrayList<>();
        when(operateurRepository.findAll()).thenReturn(operateurs);

        List<Operateur> actualRetrieveAllOperateurslist = operateurService.retrieveAllOperateurs();

        assertEquals(operateurs, actualRetrieveAllOperateurslist);
        assertEquals(0, actualRetrieveAllOperateurslist.size());
        verify(operateurRepository).findAll();
    }

    @Test
    void addOperateur() {
        // Données fictives pour le nouvel opérateur
        Operateur o = new Operateur();
        o.setNom("mariem");
        o.setPrenom("benkhlifa");
        o.setPassword("12345678");

        Facture f = new Facture();
        Set<Facture> factures = o.getFactures();
        factures.add(f);

        when(operateurRepository.save(o)).thenReturn(o);

        Operateur addedOperateur = operateurService.addOperateur(o);

        assertEquals(o, addedOperateur);
        verify(operateurRepository).save(o);
    }
}
