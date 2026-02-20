package com.example.favourite_composer;

import com.example.favourite_composer.composer.controller.ComposerController;
import com.example.favourite_composer.composer.domain.Composer;
import com.example.favourite_composer.composer.repository.ComposerRepository;
import com.example.favourite_composer.composer.service.ComposerService;
import com.example.favourite_composer.composer.service.FileStorageService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ComposerController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class ComposerControllerTest {

    @MockBean
    private ComposerService composerService;

    @MockBean
    private FileStorageService fileStorageService;

    @MockBean
    private ComposerRepository composerRepository;

    @Autowired
    private MockMvc mockMvc;

    /* ===================== CREATE ===================== */

    @Test
    void shouldCreateComposer() throws Exception {

        Composer composer = new Composer();
        composer.setId(1L);
        composer.setName("Mozart");

        when(composerService.save(org.mockito.ArgumentMatchers.any()))
                .thenReturn(composer);

        mockMvc.perform(post("/composer/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "Mozart"
                        }
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Mozart"));
    }

    /* ===================== READ ALL ===================== */

    @Test
    void shouldReturnListOfComposers() throws Exception {

        Composer composer = new Composer();
        composer.setId(1L);
        composer.setName("Bach");

        when(composerService.findAll())
                .thenReturn(List.of(composer));

        mockMvc.perform(get("/composer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Bach"));
    }

    /* ===================== READ ONE ===================== */

    @Test
    void shouldReturnComposerById() throws Exception {

        Composer composer = new Composer();
        composer.setId(1L);
        composer.setName("Beethoven");

        when(composerService.findById(1L))
                .thenReturn(composer);

        mockMvc.perform(get("/composer/read/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Beethoven"));
    }

    /* ===================== UPDATE ===================== */

    @Test
    void shouldUpdateComposer() throws Exception {

        Composer composer = new Composer();
        composer.setId(1L);
        composer.setName("Chopin");

        when(composerService.findById(1L))
                .thenReturn(composer);

        when(composerService.save(org.mockito.ArgumentMatchers.any()))
                .thenReturn(composer);

        mockMvc.perform(put("/composer/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "Chopin"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Chopin"));
    }

    /* ===================== DELETE ===================== */

    @Test
    void shouldDeleteComposer() throws Exception {

        Composer composer = new Composer();
        composer.setId(1L);
        composer.setName("Vivaldi");

        when(composerService.findById(1L))
                .thenReturn(composer);

        mockMvc.perform(delete("/composer/delete/1"))
                .andExpect(status().isNoContent());
    }
}