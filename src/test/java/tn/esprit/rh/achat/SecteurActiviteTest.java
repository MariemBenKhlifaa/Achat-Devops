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
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    SecteurActivite sa = SecteurActivite.builder().codeSecteurActivite("32324").libelleSecteurActivite("eyaaActivité").build();

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

        assertEquals(addedSecteurActivite, sa);

        verify(secteurActiviteRepository).save(sa);

        log.info("Après ==> " + sa.toString());
    }

    @Test
    @Order(3)
    void deleteSecteurActivite() {
        Long id = 1L;
        secteurActiviteService.deleteSecteurActivite(id);
        verify(secteurActiviteRepository).deleteById(id);
    }

    @Test
    @Order(4)
    void updateSecteurActivite() {
        SecteurActivite updatedSa = SecteurActivite.builder().idSecteurActivite(1L).codeSecteurActivite("32325").libelleSecteurActivite("eyaaActivitéUpdated").build();
        when(secteurActiviteRepository.save(any(SecteurActivite.class))).thenReturn(updatedSa);
        SecteurActivite returnedSa = secteurActiviteService.updateSecteurActivite(updatedSa);
        assertEquals(returnedSa, updatedSa);
        verify(secteurActiviteRepository).save(updatedSa);
    }
    @Test
    @Order(5)
    void retrieveSecteurActivite() {
        Long id = 1L;
        when(secteurActiviteRepository.findById(id)).thenReturn(Optional.of(sa));
        SecteurActivite foundSa = secteurActiviteService.retrieveSecteurActivite(id);
        assertSame(foundSa, sa);
        verify(secteurActiviteRepository).findById(id);
    }

}
