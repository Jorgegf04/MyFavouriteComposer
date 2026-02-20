package com.example.favourite_composer.composer.service;

import com.example.favourite_composer.composer.domain.Composer;
import com.example.favourite_composer.composer.dto.ComposerDTO;
import com.example.favourite_composer.composer.repository.ComposerRepository;
import com.example.favourite_composer.exceptions.ComposerNotFoundException;
import com.example.favourite_composer.musicalpiece.repository.MusicalPieceRepository;

import lombok.extern.slf4j.Slf4j;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service for Composer entity.
 * Contains business logic related to composers.
 */
@Service
@Slf4j
public class ComposerService {

    @Autowired
    private ComposerRepository composerRepository;

    @Autowired
    private MusicalPieceRepository musicalPieceRepository;

    @Autowired
    private ModelMapper modelMapper;

    private final Integer pageSize = 10;

    /**
     * CREATE / READ (all)
     * Returns all composers.
     */
    public List<Composer> findAll() {
        try {
            log.info("Fetching all composers");
            return composerRepository.findAll();
        } catch (Exception e) {
            log.error("Error fetching composers", e);
            return List.of();
        }
    }

    /**
     * READ (by id)
     * Finds a composer by id.
     */
    public Composer findById(Long id) {
       Composer composer = composerRepository.findById(id).orElseThrow(() -> new ComposerNotFoundException(id));
       
       return composer; 
    }

    /**
     * CREATE / UPDATE
     * Saves a composer (new or existing).
     */
    public Composer save(Composer composer) {
        try {

            if (composer.getId() == null) {
                log.info("CREATE composer");
            } else {
                log.info("UPDATE composer with id {}", composer.getId());
            }

            return composerRepository.save(composer);
        } catch (Exception e) {
            log.error("Error saving composer", e);
            return null;
        }
    }

    /**
     * DELETE
     * Deletes a composer only if it has no associated musical pieces.
     * Option 3 from the practice.
     */
    public boolean deleteComposer(Long id) {
        try {
            long pieces = musicalPieceRepository.countByComposerId(id);

            if (pieces == 0) {
                log.info("Deleting composer with id {}", id);
                composerRepository.deleteById(id);
                return true;
            } else {
                log.warn(
                        "Cannot delete composer {}: it has {} musical pieces",
                        id, pieces);
                return false;
            }
        } catch (Exception e) {
            log.error("Error deleting composer with id " + id, e);
            return false;
        }
    }

    /**
     * SEARCH
     * Finds composers by name (case-insensitive).
     */
    public List<Composer> findByName(String name) {
        try {
            log.info("Searching composers by name containing '{}'", name);
            return composerRepository.findByNameContainingIgnoreCase(name);
        } catch (Exception e) {
            log.error("Error searching composers by name: " + name, e);
            return List.of();
        }
    }

    // ---------------------------------------
    // ------DTO - Practica sobre DTO---------
    // ---------------------------------------
    public Composer dtoToEntity(ComposerDTO dto) {
        try {
            log.info("Composer DTO to Composer entity '{}'", dto);

            return modelMapper.map(dto, Composer.class);

        } catch (Exception e) {
            log.error("Error parsing Composer DTO to Composer Entity: " + dto, e);
            return null;
        }

    }

    public ComposerDTO entityToDto(Composer composer) {
        try {
            log.info("Composer DTO to Composer entity '{}'", composer);

            return modelMapper.map(composer, ComposerDTO.class);

        } catch (Exception e) {
            log.error("Error parsing Composer DTO to Composer Entity: " + composer, e);
            return null;
        }

    }

    /**
     * Returns all composers as DTOs.
     *
     */
    public List<ComposerDTO> findAllDto() {
        try {
            log.info("Fetching all composers as DTOs");

            return composerRepository.findAll()
                    .stream()
                    .map(this::entityToDto)
                    .toList();

        } catch (Exception e) {
            log.error("Error fetching composers as DTOs", e);
            return List.of();
        }
    }

    /**
     * Finds a composer by id and returns it as a DTO.
     *
     */
    public Optional<ComposerDTO> findDtoById(Long id) {
        try {
            log.info("Finding composer DTO with id {}", id);

            return composerRepository.findById(id)
                    .map(this::entityToDto);

        } catch (Exception e) {
            log.error("Error finding composer DTO with id {}", id, e);
            return Optional.empty();
        }
    }

    /**
     * Finds composers whose name contains the given text (case-insensitive)
     * and returns them as DTOs.
     *
     */
    public List<ComposerDTO> findByNameDto(String name) {
        try {
            log.info("Searching composers DTO by name containing '{}'", name);

            return composerRepository.findByNameContainingIgnoreCase(name)
                    .stream()
                    .map(this::entityToDto)
                    .toList();

        } catch (Exception e) {
            log.error("Error searching composers DTO by name: {}", name, e);
            return List.of();
        }
    }

    /**
     * Saves a composer using a DTO.
     * Can be used for both creating and updating a composer.
     *
     */
    public Composer saveDto(ComposerDTO dto) {
        try {
            log.info("Saving composer from DTO");

            Composer composer = dtoToEntity(dto);
            return composerRepository.save(composer);

        } catch (Exception e) {
            log.error("Error saving composer from DTO: {}", dto, e);
            return null;
        }
    }

    // ------------------------------------------------------------
    // ------PAGINATION - Practica sobre JPA more concepts---------
    // ------------------------------------------------------------

    public List<Composer> getComposerPagination(Integer pageNum) {
        Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by("name"));
        Page<Composer> pagedResult = composerRepository.findAll(paging);

        if (pagedResult.hasContent())
            return pagedResult.getContent();
        else
            return null;

    }

    public int getTotalPages() {
        Pageable paging = PageRequest.of(0, pageSize, Sort.by("name"));
        Page<Composer> pagedResult = composerRepository.findAll(paging);
        return pagedResult.getTotalPages();

    }

}
