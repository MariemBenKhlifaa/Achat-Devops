package tn.esprit.rh.achat;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import tn.esprit.rh.achat.entities.Facture;
import tn.esprit.rh.achat.entities.Operateur;
import tn.esprit.rh.achat.repositories.OperateurRepository;

import tn.esprit.rh.achat.services.OperateurServiceImpl;
import tn.esprit.rh.achat.services.ProduitServiceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
@ContextConfiguration(classes = {OperateurServiceImpl.class})

@ExtendWith(MockitoExtension.class)
public class OperateurTest {

    @MockBean
    private OperateurRepository operateurRepository;
     @Autowired
    private OperateurServiceImpl operateurService;

    Set<Facture> factures = new HashSet<>();


    @Test
    void retrieveAllOperateurs() {
        ArrayList<Operateur> operateurs = new ArrayList<>();

        when(operateurRepository.findAll()).thenReturn(operateurs);
        List<Operateur> actualRetrieveAllOperateurslist = operateurService.retrieveAllOperateurs();

        assertSame(operateurs, actualRetrieveAllOperateurslist);
        assertTrue(actualRetrieveAllOperateurslist.isEmpty());
        verify(operateurRepository).findAll();
    }

    @Test
    void addOperateur() {
        Facture f = new Facture(2L);
        factures.add(f);
        Operateur o = Operateur.builder().nom("mariem").prenom("benkhlifa").password("12345678").factures(factures).build();

        when(operateurRepository.save(Mockito.any(Operateur.class))).then(invocation -> {
            Operateur model = invocation.getArgument(0);
            model.setIdOperateur(2L);
            return model;
        });

        log.info("Avant ==> " + o.toString());
        Operateur subscription = operateurService.addOperateur(o);

        assertSame(subscription, o);
        log.info("Après ==> " + o.toString());
    }
}
