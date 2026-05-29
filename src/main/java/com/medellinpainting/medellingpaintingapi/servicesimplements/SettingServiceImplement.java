package com.medellinpainting.medellingpaintingapi.servicesimplements;

import com.medellinpainting.medellingpaintingapi.entities.Setting;
import com.medellinpainting.medellingpaintingapi.repositories.ISettingRepository;
import com.medellinpainting.medellingpaintingapi.servicesinterfaces.ISettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SettingServiceImplement implements ISettingService {

    @Autowired
    private ISettingRepository sR;

    @Override
    public List<Setting> list() {
        return sR.findAll();
    }

    @Override
    public Optional<Setting> listId(Long id) {
        return sR.findById(id);
    }

    @Override
    public Optional<Setting> findByKey(String key) {
        return sR.findByKey(key);
    }

    @Override
    public Setting insert(Setting s) {
        return sR.save(s);
    }

    @Override
    public void update(Setting s) {
        sR.save(s);
    }

    @Override
    public void delete(Long id) {
        sR.deleteById(id);
    }
}
