package com.dilly.application;

import com.dilly.dto.request.FCMNotificationRequest;
import com.dilly.exception.EntityNotFoundException;
import com.dilly.exception.ErrorCode;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FCMNotificationService {

    private final FirebaseMessaging firebaseMessaging;

    public void sendNotificationByToken(List<String> tokens, FCMNotificationRequest fcmNotificationRequest) {
        if (tokens.isEmpty()) {
            throw new EntityNotFoundException(ErrorCode.FCM_TOKEN_NOT_FOUND);
        }

        Notification notification = Notification.builder()
            .setTitle(fcmNotificationRequest.title())
            .setBody(fcmNotificationRequest.body())
            .build();

        List<CompletableFuture<Void>> futures = tokens.stream()
            .map(token -> CompletableFuture.runAsync(() -> {
                Message message = Message.builder()
                    .setToken(token)
                    .setNotification(notification)
                    .build();

                try {
                    firebaseMessaging.send(message);
                } catch (FirebaseMessagingException e) {
//                    throw new InternalServerException(ErrorCode.FCM_SERVER_ERROR);
                    log.error("푸시 알림 전송 실패: " + token, e);
                }
            }))
            .toList();

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }
}
