package com.dilly.logging;

import com.slack.api.Slack;
import com.slack.api.model.Attachment;
import com.slack.api.model.Field;
import com.slack.api.webhook.Payload;
import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SlackService {

    private final Slack slackClient = Slack.getInstance();

    public void sendMessage(String url, String title, HashMap<String, String> data) {
        try {
            slackClient.send(url, Payload.builder()
                    .text(title)
                    .attachments(List.of(
                        Attachment.builder()
                            .color(Color.GREEN.toString())
                            .fields(data.keySet()
                                .stream()
                                .map(key -> generateSlackField(key, data.get(key)))
                                .collect(Collectors.toList())
                            )
                            .build()
                    ))
                .build()
            );
        } catch (Exception e) {
            log.error("Slack 메시지 전송에 실패했습니다.", e);
        }
    }

    private Field generateSlackField(String title, String value) {
        return Field.builder()
            .title(title)
            .value(value)
            .valueShortEnough(false)
            .build();
    }
}
