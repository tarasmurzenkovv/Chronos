package com.syngenta.digital.lab.kyiv.chronos.controllers;

import com.syngenta.digital.lab.kyiv.chronos.model.dto.TagDto;
import com.syngenta.digital.lab.kyiv.chronos.model.response.GeneralResponse;
import com.syngenta.digital.lab.kyiv.chronos.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v0")
@RequiredArgsConstructor
public class TagsController {
    private final TagService tagService;

    @PostMapping("/tag")
    public ResponseEntity<GeneralResponse<TagDto>> save(@RequestBody TagDto tagDto) {
        log.info("About to register the following tag '{}'", tagDto);
        return GeneralResponse.from(tagService.register(tagDto), HttpStatus.CREATED);
    }

    @GetMapping("/tag/{id}")
    public ResponseEntity<GeneralResponse<TagDto>> find(@PathVariable("id") long id) {
        log.info("About to find the tag by id '{}'", id);
        return GeneralResponse.from(tagService.find(id), HttpStatus.FOUND);
    }

    @GetMapping("/tag")
    public ResponseEntity<GeneralResponse<List<TagDto>>> find() {
        log.info("About to find the tags ");
        return GeneralResponse.from(tagService.find(), HttpStatus.FOUND);
    }

    @GetMapping("/tag/matching")
    public ResponseEntity<GeneralResponse<List<TagDto>>> find(@RequestParam("regex") String tag) {
        log.info("About to find the tags ");
        return GeneralResponse.from(tagService.findAllTagsMatching(tag), HttpStatus.FOUND);
    }

    @PutMapping("/tag")
    public ResponseEntity<GeneralResponse<TagDto>> update(@RequestBody TagDto tagDto) {
        log.info("About to update the following tag '{}'", tagDto);
        return GeneralResponse.from(tagService.register(tagDto), HttpStatus.OK);
    }

    @DeleteMapping("/tag/{id}")
    public ResponseEntity<GeneralResponse<TagDto>> delete(@PathVariable("id") long id) {
        log.info("About to delete tag for id '{}'", id);
        tagService.delete(id);
        return GeneralResponse.from(HttpStatus.OK);
    }
}
