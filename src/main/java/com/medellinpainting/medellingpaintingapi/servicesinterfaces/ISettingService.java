package com.medellinpainting.medellingpaintingapi.servicesinterfaces;

import com.medellinpainting.medellingpaintingapi.entities.Setting;

import java.util.List;
import java.util.Optional;

public interface ISettingService {
    List<Setting> list();
    Optional<Setting> listId(Long id);
    Optional<Setting> findByKey(String key);
    Setting insert(Setting s);
    void update(Setting s);
    void delete(Long id);
}
