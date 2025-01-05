package com.kosa.jungdoin.media.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MediaDTO {
    private Long mediaSeq;
    private String mediaTypeCode;
    private Long resourceId;
    private String url;
}