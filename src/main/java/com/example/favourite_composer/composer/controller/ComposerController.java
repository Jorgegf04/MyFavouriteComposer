package com.example.favourite_composer.composer.controller;

import com.example.favourite_composer.composer.domain.Composer;
import com.example.favourite_composer.composer.dto.ComposerDTO;
import com.example.favourite_composer.composer.repository.ComposerRepository;
import com.example.favourite_composer.composer.service.ComposerService;
import com.example.favourite_composer.composer.service.FileStorageService;
import com.example.favourite_composer.exceptions.ComposerNotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * Controller responsible for handling composer-related web requests.
 */
// @Controller
@Tag(name = "Composer Class", description = "Organization employess")
@RestController
@Slf4j
public class ComposerController {

    @Autowired
    private ComposerService composerService;

    @Autowired
    private ComposerRepository composerRepository;

    @Autowired
    private FileStorageService fileStorageService;

    /* ===================== CREATE ===================== */

    // @PostMapping(value = "/composer/create", consumes =
    // MediaType.MULTIPART_FORM_DATA_VALUE)
    // public ResponseEntity<?> newElement(@Valid @RequestPart("data") Composer
    // newComposer,
    // @RequestPart("file") MultipartFile file) {

    // if (!file.isEmpty()) {
    // try {
    // newComposer.setNameImage(fileStorageService.store(file));
    // } catch (Exception e) {
    // newComposer.setNameImage(null);
    // }
    // } else {
    // newComposer.setNameImage(null);
    // }

    // // @Valid si no se cumple la validacion devuelve BAD_REQUEST.cod 404
    // Composer composer = composerService.save(newComposer);
    // return ResponseEntity.status(HttpStatus.CREATED).body(composer); // Codigo
    // 201

    // }

    @PostMapping("/composer/create")
    public ResponseEntity<?> newElement(
            @Valid @RequestBody Composer newComposer) {

        Composer composer = composerService.save(newComposer);
        return ResponseEntity.status(HttpStatus.CREATED).body(composer);
    }

    /* ===================== READ ===================== */

    @Operation(summary = "Get composer by ID", description = "Returns a composer by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Composer.class))),
            @ApiResponse(responseCode = "404", description = "Composer not found")
    })
    @GetMapping("/composer")
    public ResponseEntity<?> getList() {
        List<Composer> composerList = composerService.findAll();
        if (composerList.isEmpty()) {
            return ResponseEntity.notFound().build(); // Codigo 404
        } else {
            return ResponseEntity.ok(composerList); // Codigo 200
        }
    }

    @Operation(summary = "Get all composers", description = "Returns a list of all composers")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Composer.class))),
            @ApiResponse(responseCode = "404", description = "No composers found")
    })
    @GetMapping("/composer/read/{id}")
    public Composer getOneElement(@PathVariable Long id) {
        return composerService.findById(id);
    }

    /* ===================== UPDATE ===================== */

    // @PutMapping(value = "/composer/update/{id}", consumes =
    // MediaType.MULTIPART_FORM_DATA_VALUE)
    // public ResponseEntity<?> editElement(
    // @Valid @RequestPart("data") Composer editComposer,
    // @PathVariable Long id) {
    // // (@RequestPart("file") MultipartFile file ---> quito esto si no da error en
    // el
    // // front
    // Composer composer = composerService.findById(id);
    // if (composer == null) {

    // return ResponseEntity.notFound().build();

    // // fileStorageService.delete(composer.getNameImage());

    // } else {
    // editComposer.setNameImage(null);
    // editComposer.setId(id);
    // composer = composerService.save(editComposer);

    // return ResponseEntity.ok(composer);
    // }

    // // if (!file.isEmpty()) {
    // // try {
    // // editComposer.setNameImage(fileStorageService.store(file));
    // // } catch (Exception e) {
    // // editComposer.setNameImage(null);
    // // }
    // }

    @PutMapping("/composer/update/{id}")
    public ResponseEntity<?> editElement(
            @Valid @RequestBody Composer editComposer,
            @PathVariable Long id) {

        Composer composer = composerService.findById(id);

        if (composer == null) {
            return ResponseEntity.notFound().build();
        }

        editComposer.setId(id);
        composer = composerService.save(editComposer);

        return ResponseEntity.ok(composer);
    }

    /* ===================== DELETE ===================== */
    @DeleteMapping("/composer/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteElement(@PathVariable Long id) {
        composerService.findById(id); // lanza excepción si no existe
        composerService.deleteComposer(id);
    }

    /* ===================== Enviar ficheros ===================== */
    @GetMapping(value = "/files/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename, HttpServletRequest request) {
        Resource file = fileStorageService.loadAsResource(filename);
        String contentType = null;

        try {
            contentType = request.getServletContext().getMimeType(file.getFile().getAbsolutePath());
        } catch (IOException ex) {
            System.err.println("No se puede determinar el tipo de archivo");
        }

        if (contentType == null)
            contentType = "application/octet-stream";

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(file);
    }

    /**
     * Shows the form to create a new composer.
     */
    // @GetMapping("/add/composer")
    // public String showAddComposerForm(Model model) {

    // log.info("Request received: add composer form");

    // model.addAttribute("composer", new ComposerDTO());

    // return "composer/composer-form";
    // }

    /**
     * Saves a new composer.
     */
    // @PostMapping("/add/composer")
    // public String saveComposer(
    // @Valid @ModelAttribute("composer") ComposerDTO composerDto,
    // BindingResult bindingResult) {

    // if (bindingResult.hasErrors()) {
    // log.warn("Validation errors while saving composer");
    // return "composer/composer-form";
    // }

    // composerService.saveDto(composerDto);
    // return "redirect:/show/composer";
    // }

    /**
     * Shows all composers.
     */
    // @GetMapping("/show/composer")
    // public String showAllComposers(Model model) {

    // log.info("Request received: show all composers");
    // model.addAttribute("composers", composerService.findAllDto());

    // return "composer/composer-list";
    // }

    /**
     * Shows the form to search composers.
     */
    // @GetMapping("/search/composer")
    // public String searchComposerForm(Model model) {

    // log.info("Request received: search composer form");
    // model.addAttribute("name", "");

    // return "composer/composer-search";
    // }

    /**
     * Shows the result of searching composers.
     */
    // @PostMapping("/search/composer/result")
    // public String searchComposerResult(
    // @RequestParam String name,
    // Model model) {

    // log.info("Searching composers by name '{}'", name);

    // model.addAttribute("composers", composerService.findByNameDto(name));
    // model.addAttribute("name", name);

    // return "composer/composer-search";
    // }

    /* ===================== UPDATE ===================== */

    /**
     * Shows the form to edit a composer.
     */
    // @GetMapping("/edit/composer/{id}")
    // public String showEditComposerForm(
    // @PathVariable Long id,
    // Model model) {

    // log.info("Request received: edit composer {}", id);

    // return composerService.findDtoById(id)
    // .map(dto -> {
    // model.addAttribute("composer", dto);
    // return "composer/composer-form";
    // })
    // .orElse("redirect:/show/composer");
    // }

    /**
     * Updates an existing composer.
     */
    // @PostMapping("/edit/composer/{id}")
    // public String updateComposer(
    // @PathVariable Long id,
    // @Valid @ModelAttribute("composer") ComposerDTO composerDto,
    // BindingResult bindingResult) {

    // if (bindingResult.hasErrors()) {
    // log.warn("Validation errors while updating composer {}", id);
    // return "composer/composer-form";
    // }

    // // IMPORTANT: id comes ONLY from URL
    // composerDto.setId(id);
    // composerService.saveDto(composerDto);

    // return "redirect:/show/composer";
    // }

    /* ===================== DELETE ===================== */

    /**
     * Deletes a composer by id.
     */
    // @GetMapping("/delete/composer/{id}")
    // public String deleteComposer(@PathVariable Long id) {

    // log.info("Request received: delete composer {}", id);

    // composerService.deleteComposer(id);
    // return "redirect:/show/composer";
    // }

    /* ===================== Composer with pagination ===================== */
    // @GetMapping("/composer-pagination-list")
    // public String showComposerPaginatio(@RequestParam(required = false) Integer
    // pag, Model model) {
    // int ultPag = composerService.getTotalPages() - 1;
    // if (pag == null || pag < 0 || pag > ultPag)
    // pag = 0;
    // Integer pagSig = ultPag > pag ? pag + 1 : ultPag;
    // Integer pagAnt = pag > 0 ? pag - 1 : 0;

    // model.addAttribute("composerList",
    // composerService.getComposerPagination(pag));
    // model.addAttribute("pagSig", pagSig);
    // model.addAttribute("pagAnt", pagAnt);
    // model.addAttribute("pagFinal", ultPag);

    // return "composer/composer-pagination-list";
    // }
}
