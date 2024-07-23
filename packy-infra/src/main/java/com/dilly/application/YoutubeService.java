package com.dilly.application;

import com.dilly.exception.ErrorCode;
import com.dilly.exception.internalserver.InternalServerException;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class YoutubeService {

    @Value("${youtube.api.key}")
    private String apiKey;
    private final JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

    public Boolean validateYoutubeUrl(String url) {
        boolean isYoutubeUrlValid = true;

        // videoId 추출 불가능
        String videoId = extractVideoId(url);
        if (videoId == null) {
            isYoutubeUrlValid = false;
        }

        // video 정보 접근 불가능
        Optional<Video> video = getVideoInfo(videoId);
        if (video.isEmpty()) {
            isYoutubeUrlValid = false;
        }

        // 임베딩 불가능
        if (Boolean.FALSE.equals(video.get().getStatus().getEmbeddable())) {
            isYoutubeUrlValid = false;
        }

        // 공개되지 않은 영상
        if (video.get().getStatus().getPrivacyStatus().equals("private")) {
            isYoutubeUrlValid = false;
        }

        return isYoutubeUrlValid;
    }

    private String extractVideoId(String url) {
        String[] patterns = getPatterns();

        for (String pattern : patterns) {
            Pattern compiledPattern = Pattern.compile(pattern);
            Matcher matcher = compiledPattern.matcher(url);

            if (matcher.find()) {
                return matcher.group(1);
            }
        }

        return null;
    }

    private Optional<Video> getVideoInfo(String videoId) {
        try {
            YouTube youtubeService = getYouTubeService();
            YouTube.Videos.List videoList = youtubeService.videos().list(
                Collections.singletonList("status")
            );

            videoList.setKey(apiKey);
            videoList.setId(Collections.singletonList(videoId));

            VideoListResponse videoListResponse = videoList.execute();

            return Optional.ofNullable(videoListResponse.getItems().get(0));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private YouTube getYouTubeService() {
        try {
            return new YouTube.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                jsonFactory,
                null
            )
                .setApplicationName("Packy")
                .build();
        } catch (GeneralSecurityException | IOException e) {
            throw new InternalServerException(ErrorCode.YOUTUBE_SERVER_ERROR);
        }
    }

    private static String[] getPatterns() {
        String originalUrl = "(?:https?:\\/\\/)?(?:www\\.)?youtube\\.com\\/watch\\?v=([a-zA-Z0-9_-]+)";
        String shortUrl = "(?:https?:\\/\\/)?(?:www\\.)?youtu\\.be\\/([a-zA-Z0-9_-]+)";
        String shortUrlWithTime = "(?:https?:\\/\\/)?(?:www\\.)?youtu\\.be\\/([a-zA-Z0-9_-]+)\\?t=\\d+";
        String originalUrlFromPlaylist = "(?:https?:\\/\\/)?(?:www\\.)?youtube\\.com\\/watch\\?v=([a-zA-Z0-9_-]+)&list=([a-zA-Z0-9_-]+)";

        return new String[]{
            originalUrl,
            shortUrl,
            shortUrlWithTime,
            originalUrlFromPlaylist
        };
    }
}
