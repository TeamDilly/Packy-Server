package com.dilly.admin.application;

import com.dilly.admin.adaptor.SettingReader;
import com.dilly.admin.domain.setting.Setting;
import com.dilly.admin.dto.response.SettingV2Response;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AdminV2Service {

    private final SettingReader settingReader;

    public List<SettingV2Response> getSettingUrls() {
        List<Setting> settingUrls = settingReader.findAll();

        return settingUrls.stream().map(SettingV2Response::from).toList();
    }
}
