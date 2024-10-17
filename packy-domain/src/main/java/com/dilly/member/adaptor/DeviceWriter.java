package com.dilly.member.adaptor;

import com.dilly.member.dao.DeviceRepository;
import com.dilly.member.domain.Device;
import com.dilly.member.domain.Member;
import com.dilly.member.domain.Platform;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeviceWriter {

    private final DeviceRepository deviceRepository;

    public Device save(Device device) {
        return deviceRepository.save(device);
    }

    public Device save(String deviceId, String fcmToken, Platform platform, Member member) {
        return deviceRepository.save(Device.builder()
            .deviceId(deviceId)
            .fcmToken(fcmToken)
            .platform(platform)
            .member(member)
            .build());
    }

    public void delete(Device device) {
        deviceRepository.delete(device);
    }

    public void deleteAll() {
        deviceRepository.deleteAll();
    }
}
