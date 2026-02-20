package com.example.favourite_composer.musicalpiece.service;

import com.example.favourite_composer.musicalpiece.domain.MusicalPiece;
import com.example.favourite_composer.musicalpiece.dto.MusicalPieceDTO;
import com.example.favourite_composer.musicalpiece.repository.MusicalPieceRepository;
import com.example.favourite_composer.composer.domain.Composer;
import com.example.favourite_composer.composer.repository.ComposerRepository;
import com.example.favourite_composer.exceptions.EmptyMusicalPiecesException;
import com.example.favourite_composer.exceptions.MusicalPieceNotFoundException;

import lombok.extern.slf4j.Slf4j;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MusicalPieceService {

    @Autowired
    private MusicalPieceRepository musicalPieceRepository;

    @Autowired
    private ComposerRepository composerRepository;

    @Autowired
    private ModelMapper modelMapper;

    /* ===================== ENTITY ===================== */

    public List<MusicalPiece> findAll() {
        List<MusicalPiece> pieces = musicalPieceRepository.findAll();
        if (pieces.isEmpty()) {
            throw new EmptyMusicalPiecesException();
        }

        return pieces;
    }

    public MusicalPiece findById(Long id) {
        return musicalPieceRepository.findById(id).orElseThrow(() -> new MusicalPieceNotFoundException(id));
    }

    public MusicalPiece create(MusicalPiece musicalPiece) {
        log.info("CREATE musical piece");
        return musicalPieceRepository.save(musicalPiece);
    }

    public MusicalPiece update(Long id, MusicalPiece editPiece) {

        MusicalPiece existing = musicalPieceRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }

        existing.setTitle(editPiece.getTitle());
        existing.setAlternativeTitle(editPiece.getAlternativeTitle());
        existing.setInstrumentation(editPiece.getInstrumentation());
        existing.setPremiere(editPiece.getPremiere());
        existing.setLink(editPiece.getLink());

        Long composerId = editPiece.getComposer().getId();
        Composer composer = composerRepository.findById(composerId).orElse(null);
        if (composer == null) {
            throw new IllegalArgumentException("Composer not found");
        }
        existing.setComposer(composer);

        log.info("UPDATE musical piece with id {}", id);
        return musicalPieceRepository.save(existing);
    }

    public boolean deleteById(Long id) {
        try {
            log.info("Deleting musical piece with id {}", id);
            musicalPieceRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("Error deleting musical piece with id {}", id, e);
            return false;
        }
    }

    public List<MusicalPiece> findByTitle(String title) {
        try {
            log.info("Searching musical pieces by title '{}'", title);
            return musicalPieceRepository.findByTitleContainingIgnoreCase(title);
        } catch (Exception e) {
            log.error("Error searching musical pieces by title {}", title, e);
            return List.of();
        }
    }

    /* ===================== DTO ===================== */

    public MusicalPiece dtoToEntity(MusicalPieceDTO dto) {
        try {
            log.info("Mapping MusicalPieceDTO to entity '{}'", dto);

            MusicalPiece mp = modelMapper.map(dto, MusicalPiece.class);

            Composer composer = composerRepository
                    .findById(dto.getComposerId())
                    .orElseThrow(() -> new RuntimeException("Composer not found"));

            mp.setComposer(composer);

            return mp;

        } catch (Exception e) {
            log.error("Error parsing MusicalPieceDTO to entity: {}", dto, e);
            return null;
        }
    }

    public MusicalPieceDTO entityToDto(MusicalPiece musicalPiece) {
        try {
            MusicalPieceDTO dto = modelMapper.map(musicalPiece, MusicalPieceDTO.class);

            if (musicalPiece.getComposer() != null) {
                dto.setComposerId(musicalPiece.getComposer().getId());
                dto.setComposerName(musicalPiece.getComposer().getName());
            }

            return dto;

        } catch (Exception e) {
            log.error("Error parsing MusicalPiece entity to DTO", e);
            return null;
        }
    }

    public List<MusicalPieceDTO> findAllDto() {
        try {
            log.info("Fetching all MusicalPiece as DTOs");
            return musicalPieceRepository.findAll()
                    .stream()
                    .map(this::entityToDto)
                    .toList();
        } catch (Exception e) {
            log.error("Error fetching musicalpiece as DTOs", e);
            return List.of();
        }
    }

    public Optional<MusicalPieceDTO> findDtoById(Long id) {
        try {
            log.info("Finding musicalpiece DTO with id {}", id);
            return musicalPieceRepository.findById(id).map(this::entityToDto);
        } catch (Exception e) {
            log.error("Error finding musicalpieceDTO with id {}", id, e);
            return Optional.empty();
        }
    }

    public List<MusicalPieceDTO> findByTitleDto(String title) {
        try {
            log.info("Searching musicalpieceDTO by title '{}'", title);
            return musicalPieceRepository.findByTitleContainingIgnoreCase(title)
                    .stream()
                    .map(this::entityToDto)
                    .toList();
        } catch (Exception e) {
            log.error("Error searching musicalpieceDTO by title {}", title, e);
            return List.of();
        }
    }

    public MusicalPiece saveDto(MusicalPieceDTO dto) {
        try {
            log.info("Saving musicalPiece from DTO");
            MusicalPiece musicalPiece = dtoToEntity(dto);
            return musicalPieceRepository.save(musicalPiece);
        } catch (Exception e) {
            log.error("Error saving musicalpiece from DTO: {}", dto, e);
            return null;
        }
    }
}
