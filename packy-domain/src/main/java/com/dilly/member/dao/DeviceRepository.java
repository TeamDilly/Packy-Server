package com.dilly.member.dao;

import com.dilly.member.domain.Device;
import com.dilly.member.domain.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    List<Device> findByMember(Member member);

    Optional<Device> findByDeviceId(String deviceId);
    
    Long countByMember(Member member);

    Long countByDeviceId(String deviceId);
}
