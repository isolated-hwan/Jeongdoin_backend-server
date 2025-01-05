package com.kosa.jungdoin.media.controller;

import com.kosa.jungdoin.media.dto.MediaDTO;
import com.kosa.jungdoin.media.service.MediaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class MediaController {

    private final MediaService mediaService;

    @PostMapping
    public ResponseEntity<MediaDTO> upload(@RequestParam("file") MultipartFile file,
                                           @RequestParam("mediaTypeCode") String mediaTypeCode,
                                           @RequestParam("resourceId") Long resourceId) {
        try {
            MediaDTO createdMedia = mediaService.store(file, mediaTypeCode, resourceId);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdMedia);
        } catch (GitAPIException e) {
            log.error("Error occurred while uploading media", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<MediaDTO>> getMediaByTypeAndResource(@RequestParam("mediaTypeCode") String mediaTypeCode,
                                                                    @RequestParam("resourceId") Long resourceId) {
        List<MediaDTO> mediaList = mediaService.findByMediaTypeCodeAndResourceId(mediaTypeCode, resourceId);
        return ResponseEntity.ok(mediaList);
    }
}
