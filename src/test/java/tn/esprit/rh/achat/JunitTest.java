package tn.esprit.rh.achat;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.rh.achat.entities.Facture;
import tn.esprit.rh.achat.entities.Operateur;
import tn.esprit.rh.achat.services.OperateurServiceImpl;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class JunitTest {
    @Test
    void contextLoads() {
    }
    @Autowired
    private OperateurServiceImpl operateurService;

    @Test
    @Order(0)
    public void ajouterOperateurTest() {
        Set<Facture> factures = new HashSet<>();
        Operateur o = Operateur.builder().nom("mariem").prenom("benkhlifa").password("12345678").factures(factures).build();

        operateurService.addOperateur(o);
        log.info(o.toString());
        Assertions.assertNotNull(o.getIdOperateur()); // Assuming getId() is the identifier for Operateur
    }

    @Test
    @Order(1)
    public void modifierOperateurTest() {
        // Assuming that the addOperateur method is working correctly, and the Operateur is persisted in the database.
        Operateur o = operateurService.retrieveOperateur(1L); // Replace 1L with the actual Operateur ID
        Assertions.assertNotNull(o, "Operateur not found");

        String newNom = "mariem2";
        o.setNom(newNom);
        Operateur updatedOperateur = operateurService.updateOperateur(o);
        log.info(updatedOperateur.toString());
        Assertions.assertEquals(newNom, updatedOperateur.getNom());
    }
}
