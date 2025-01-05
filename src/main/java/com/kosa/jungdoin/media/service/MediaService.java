package com.kosa.jungdoin.media.service;

import com.kosa.jungdoin.entity.Media;
import com.kosa.jungdoin.entity.MediaType;
import com.kosa.jungdoin.media.dto.MediaDTO;
import com.kosa.jungdoin.media.repository.MediaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MediaService {

    private final MediaRepository mediaRepository;
    private final GitService gitService;
    
    public MediaDTO store(MultipartFile file, String mediaTypeCode, Long resourceId) throws GitAPIException {
        String imageUrl = gitService.commitAndPushFile(file);

        Media media = Media.builder()
                .mediaTypeCode(MediaType.builder().mediaTypeCode(mediaTypeCode).build())
                .resourceId(resourceId)
                .url(imageUrl)
                .build();

        Media savedMedia = mediaRepository.save(media);
        return convertToDTO(savedMedia);
    }

    public List<MediaDTO> findByMediaTypeCodeAndResourceId(String mediaTypeCode, Long resourceId) {
        MediaType mediaType = MediaType.builder().mediaTypeCode(mediaTypeCode).build();
        List<Media> mediaList = mediaRepository.findByMediaTypeCodeAndResourceId(mediaType, resourceId);
        return mediaList.stream()
                .map(this::convertToDTO)
                .toList();
    }
    
    private MediaDTO convertToDTO(Media media) {
        return MediaDTO.builder()
                .mediaSeq(media.getMediaSeq())
                .mediaTypeCode(media.getMediaTypeCode().getMediaTypeCode())
                .resourceId(media.getResourceId())
                .url(media.getUrl())
                .build();
    }
}
