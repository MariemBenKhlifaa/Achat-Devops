package tn.esprit.rh.achat;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import tn.esprit.rh.achat.entities.Facture;
import tn.esprit.rh.achat.entities.Operateur;
import tn.esprit.rh.achat.services.OperateurServiceImpl;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest
@ContextConfiguration(classes = {OperateurServiceImpl.class})
@ExtendWith(SpringExtension.class)
@Slf4j
public class JunitTest
{
    @Autowired
    private OperateurServiceImpl operateurService;
    @Test
    void contextLoads() {
    }

    Set<Facture> factures = new HashSet<>();
    Operateur o = Operateur.builder().nom("mariem").prenom("benkhlifa").password("12345678").factures(factures).build();
    @Test
    @Order(0)
    public void ajouterOperateurTest() {
        o = operateurService.addOperateur(o);
        log.info(o.toString());
        Assertions.assertNotNull(o.getNom());
    }


    @Test
    @Order(1)
    public void modifierSubscriptionTest() {
        o.setNom("mariem2");
        o = operateurService.updateOperateur(o);
        log.info(o.toString());
        Assertions.assertNotEquals(o.getNom(), "mariem2");
    }

}
