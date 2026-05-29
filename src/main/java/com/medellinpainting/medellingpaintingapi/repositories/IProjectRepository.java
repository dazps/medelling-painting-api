package com.medellinpainting.medellingpaintingapi.repositories;

import com.medellinpainting.medellingpaintingapi.entities.Project;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByCategoryIgnoreCase(String category);

    List<Project> findByPublished(Boolean published);

    @Modifying
    @Transactional
    @Query("UPDATE Project p SET p.imageUrl = null WHERE p.imageUrl = :url")
    void clearImageUrl(@Param("url") String url);

    @Query("SELECT p FROM Project p WHERE p.videoUrl IS NOT NULL AND p.videoUrl LIKE %:url%")
    List<Project> findByVideoUrlContaining(@Param("url") String url);
}
