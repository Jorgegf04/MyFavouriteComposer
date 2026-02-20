package com.example.favourite_composer.musicalpiece.controller;

import com.example.favourite_composer.musicalpiece.domain.MusicalPiece;
import com.example.favourite_composer.musicalpiece.dto.MusicalPieceDTO;
import com.example.favourite_composer.musicalpiece.repository.MusicalPieceRepository;
import com.example.favourite_composer.musicalpiece.service.MusicalPieceService;
import com.example.favourite_composer.composer.controller.ComposerController;
import com.example.favourite_composer.composer.domain.Composer;
import com.example.favourite_composer.composer.service.ComposerService;

import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller responsible for handling musical-piece-related web requests.
 */
// @Controller
@RestController
@Slf4j
public class MusicalPieceController {

    @Autowired
    private MusicalPieceService musicalPieceService;

    @Autowired
    private ComposerService composerService;

    @Autowired
    private MusicalPieceRepository musicalPieceRepository;

    /* ===================== CREATE ===================== */

    @PostMapping("/piece/create")
    public ResponseEntity<?> newElement(@Valid @RequestBody MusicalPiece newPiece) {
        // @Valid si no se cumple la validacion devuelve BAD_REQUEST.cod 404
        MusicalPiece musicalPiece = musicalPieceService.create(newPiece);
        return ResponseEntity.status(HttpStatus.CREATED).body(musicalPiece); // Codigo 201

    }

    /* ===================== READ ===================== */

    @GetMapping("/piece")
    public ResponseEntity<?> getList() {
        List<MusicalPiece> piecesList = musicalPieceService.findAll();
        if (piecesList.isEmpty()) {
            return ResponseEntity.notFound().build(); // Codigo 404
        } else {
            return ResponseEntity.ok(piecesList); // Codigo 200
        }
    }

    @GetMapping("/piece/read/{id}")
    public ResponseEntity<?> getOneElement(@PathVariable Long id) {
        MusicalPiece piece = musicalPieceService.findById(id);
        Composer composer = piece.getComposer();
        if (piece != null) {
            Link link = WebMvcLinkBuilder.linkTo(MusicalPieceController.class).slash("piece").slash(piece.getId())
                    .withSelfRel();
            piece.add(link);
            Link link2 = WebMvcLinkBuilder.linkTo(ComposerController.class).slash("composer").slash(composer.getId())
                    .withRel("composer");
            piece.add(link2);
            Link link3 = WebMvcLinkBuilder.linkTo(MusicalPieceController.class)
                    .slash("piece").withRel("all");
            piece.add(link3);
            return ResponseEntity.ok(piece); // cod 200
        } else {
            return ResponseEntity.notFound().build(); // Codigo 200
        }
    }

    /* ===================== UPDATE ===================== */
    @PutMapping("/piece/update/{id}")
    public ResponseEntity<?> updatePiece(
            @Valid @RequestBody MusicalPiece editPiece,
            @PathVariable Long id) {

        MusicalPiece updated = musicalPieceService.update(id, editPiece);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    /* ===================== DELETE ===================== */
    @DeleteMapping("/piece/delete/{id}")
    public ResponseEntity<?> deleteElement(@PathVariable Long id) {

        MusicalPiece piece = musicalPieceService.findById(id);
        if (piece == null) {
            return ResponseEntity.notFound().build();
        }

        musicalPieceService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // /* ===================== CREATE ===================== */

    // /**
    // * Shows the form to create a new musical piece.
    // */
    // @GetMapping("/add/music-piece")
    // public String showAddMusicPieceForm(Model model) {
    // log.info("Request received: add music piece form");
    // model.addAttribute("musicalPiece", new MusicalPieceDTO());
    // model.addAttribute("composers", composerService.findAll());
    // return "musicalpiece/musicalpiece-form";
    // }

    // /**
    // * Saves a new musical piece.
    // */
    // @PostMapping("/add/music-piece")
    // public String saveMusicPiece(
    // @Valid @ModelAttribute("musicalPiece") MusicalPieceDTO musicalPieceDto,
    // BindingResult bindingResult,
    // Model model) {

    // if (bindingResult.hasErrors()) {
    // log.warn("Validation errors while saving musical piece");
    // model.addAttribute("composers", composerService.findAll());
    // return "musicalpiece/musicalpiece-form";
    // }

    // musicalPieceService.saveDto(musicalPieceDto);
    // return "redirect:/show/music-piece";
    // }

    // /* ===================== READ ===================== */

    // /**
    // * Shows all musical pieces.
    // */
    // @GetMapping("/show/music-piece")
    // public String showAllMusicPieces(Model model) {
    // log.info("Request received: show all musical pieces");
    // model.addAttribute("musicalPieces", musicalPieceService.findAllDto());
    // return "musicalpiece/musicalpiece-list";
    // }

    // /**
    // * Shows the form to search musical pieces.
    // */
    // @GetMapping("/search/music-piece")
    // public String searchMusicPieceForm(Model model) {
    // log.info("Request received: search music piece form");
    // model.addAttribute("title", "");
    // return "musicalpiece/musicalpiece-search";
    // }

    // /**
    // * Shows the result of searching musical pieces.
    // */
    // @PostMapping("/search/music-piece/result")
    // public String searchMusicPieceResult(
    // @RequestParam String title,
    // Model model) {

    // log.info("Searching musical pieces by title {}", title);
    // model.addAttribute(
    // "musicalPieces",
    // musicalPieceService.findByTitleDto(title));

    // model.addAttribute("title", title);
    // return "musicalpiece/musicalpiece-search";
    // }

    // /* ===================== UPDATE ===================== */

    // /**
    // * Shows the form to edit a musical piece.
    // */
    // @GetMapping("/edit/music-piece/{id}")
    // public String showEditMusicPieceForm(
    // @PathVariable Long id,
    // Model model) {

    // log.info("Request received: edit musical piece {}", id);

    // return musicalPieceService.findDtoById(id)
    // .map(dto -> {
    // model.addAttribute("musicalPiece", dto);
    // model.addAttribute("composers", composerService.findAll());
    // return "musicalpiece/musicalpiece-form";
    // })
    // .orElse("redirect:/show/music-piece");
    // }

    // /**
    // * Updates an existing musical piece.
    // */
    // @PostMapping("/edit/music-piece/{id}")
    // public String updateMusicPiece(
    // @PathVariable Long id,
    // @Valid @ModelAttribute("musicalPiece") MusicalPieceDTO musicalPieceDto,
    // BindingResult bindingResult,
    // Model model) {

    // if (bindingResult.hasErrors()) {
    // log.warn("Validation errors while updating musical piece {}", id);
    // model.addAttribute("composers", composerService.findAll());
    // return "musicalpiece/musicalpiece-form";
    // }

    // musicalPieceDto.setId(id);
    // musicalPieceService.saveDto(musicalPieceDto);
    // return "redirect:/show/music-piece";
    // }

    // /* ===================== DELETE ===================== */

    // /**
    // * Deletes a musical piece by id.
    // */
    // @GetMapping("/delete/music-piece/{id}")
    // public String deleteMusicPiece(@PathVariable Long id) {
    // log.info("Request received: delete musical piece {}", id);
    // musicalPieceService.deleteById(id);
    // return "redirect:/show/music-piece";
    // }
}
