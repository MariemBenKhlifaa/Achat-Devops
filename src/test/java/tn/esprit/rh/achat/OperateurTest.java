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
import tn.esprit.rh.achat.entities.Facture;
import tn.esprit.rh.achat.entities.Operateur;
import tn.esprit.rh.achat.entities.Produit;
import tn.esprit.rh.achat.repositories.OperateurRepository;

import tn.esprit.rh.achat.services.OperateurServiceImpl;
import tn.esprit.rh.achat.services.ProduitServiceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
@ContextConfiguration(classes = {OperateurServiceImpl.class})
@ExtendWith(SpringExtension.class)
public class OperateurTest {

    @MockBean
    private OperateurRepository operateurRepository;
    @Autowired
    private OperateurServiceImpl operateurService;

    Operateur o = Operateur.builder().nom("mariem").prenom("benkhlifa").password("12345678").build();


    @Test
    @Order(1)
    void retrieveAllOperateurs() {
        ArrayList<Operateur> operateurList = new ArrayList<>();
        when(operateurRepository.findAll()).thenReturn(operateurList);
        List<Operateur> actualRetrieveAllOperateurResult = operateurService.retrieveAllOperateurs();
        assertSame(operateurList, actualRetrieveAllOperateurResult);
        assertTrue(actualRetrieveAllOperateurResult.isEmpty());
        verify(operateurRepository).findAll();
    }

    @Test
    @Order(0)
    void addOperateur() {
        //Set<Facture> factures = new HashSet<>();

        when(operateurRepository.save(Mockito.any(Operateur.class))).then(invocation -> {
            Operateur model = invocation.getArgument(0);
            model.setIdOperateur(2L);
            return model;
        });

        operateurService = new OperateurServiceImpl();

        log.info("Avant ==> " + o.toString());
        Operateur subscription = operateurService.addOperateur(o);

        assertEquals(subscription, o);
        log.info("Après ==> " + o.toString());
    }
}
