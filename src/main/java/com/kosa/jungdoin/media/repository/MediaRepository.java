package com.kosa.jungdoin.media.repository;

import com.kosa.jungdoin.entity.Media;
import com.kosa.jungdoin.entity.MediaType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MediaRepository extends JpaRepository<Media, Long> {
    List<Media> findByMediaTypeCodeAndResourceId(MediaType mediaType, Long resourceId);
}
