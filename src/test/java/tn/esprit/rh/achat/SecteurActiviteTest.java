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
import tn.esprit.rh.achat.entities.SecteurActivite;
import tn.esprit.rh.achat.repositories.SecteurActiviteRepository;
import tn.esprit.rh.achat.services.SecteurActiviteServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
@ContextConfiguration(classes = {SecteurActiviteServiceImpl.class})
@ExtendWith(SpringExtension.class)
public class SecteurActiviteTest {

    @MockBean
    private SecteurActiviteRepository secteurActiviteRepository;

    @Autowired
    private SecteurActiviteServiceImpl secteurActiviteService;

    // Sample data for tests
    SecteurActivite sa = new SecteurActivite(); // Initialize with sample data if necessary

    @Test
    @Order(1)
    void retrieveAllSecteurActivite() {
        List<SecteurActivite> secteurActiviteList = new ArrayList<>();
        when(secteurActiviteRepository.findAll()).thenReturn(secteurActiviteList);
        List<SecteurActivite> actualSecteurActiviteList = secteurActiviteService.retrieveAllSecteurActivite();
        assertSame(secteurActiviteList, actualSecteurActiviteList);
        assertTrue(actualSecteurActiviteList.isEmpty());
        verify(secteurActiviteRepository).findAll();
    }

    @Test
    @Order(2)
    void addSecteurActivite() {
        // Mocking the save method to capture the SecteurActivite object
        Mockito.when(secteurActiviteRepository.save(Mockito.any(SecteurActivite.class))).then(invocation -> {
            SecteurActivite sa = invocation.getArgument(0);
            sa.setIdSecteurActivite(1L); // Assuming a setter method for ID exists
            return sa;
        });

        log.info("Avant ==> " + sa.toString());
        SecteurActivite addedSecteurActivite = secteurActiviteService.addSecteurActivite(sa);

        // Asserting the object
        assertEquals(addedSecteurActivite, sa);

        // Verifying the save method was called
        verify(secteurActiviteRepository).save(sa);

        log.info("Après ==> " + sa.toString());
    }

    // Repeat this pattern for the other methods in your service class
}
