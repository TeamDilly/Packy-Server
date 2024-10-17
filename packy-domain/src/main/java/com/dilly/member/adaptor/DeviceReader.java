package com.dilly.member.adaptor;

import com.dilly.member.dao.DeviceRepository;
import com.dilly.member.domain.Device;
import com.dilly.member.domain.Member;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeviceReader {

    private final DeviceRepository deviceRepository;

    public List<Device> findByMember(Member member) {
        return deviceRepository.findByMember(member);
    }

    public Optional<Device> findByDeviceId(String deviceId) {
        return deviceRepository.findByDeviceId(deviceId);
    }

    public Long countByMember(Member member) {
        return deviceRepository.countByMember(member);
    }

    public Long countByDeviceId(String deviceId) {
        return deviceRepository.countByDeviceId(deviceId);
    }
}
