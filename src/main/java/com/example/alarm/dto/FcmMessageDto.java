package com.example.alarm.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class FcmMessageDto {
    private String title;
    private String body;
    private String token;

    @Builder
    public FcmMessageDto(String title, String body, String token) {
        this.title = title;
        this.body = body;
        this.token = token;
    }
}
