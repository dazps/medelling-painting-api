package com.medellinpainting.medellingpaintingapi.repositories;

import com.medellinpainting.medellingpaintingapi.entities.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ISettingRepository extends JpaRepository<Setting, Long> {

    Optional<Setting> findByKey(String key);
}
