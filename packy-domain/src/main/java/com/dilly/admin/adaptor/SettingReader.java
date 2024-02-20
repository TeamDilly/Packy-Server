package com.dilly.admin.adaptor;

import com.dilly.admin.dao.SettingRepository;
import com.dilly.admin.domain.setting.Setting;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SettingReader {

    private final SettingRepository settingRepository;

    public List<Setting> findAll() {
        return settingRepository.findAll();
    }
}
