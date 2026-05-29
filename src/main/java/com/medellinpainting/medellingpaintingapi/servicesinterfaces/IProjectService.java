package com.medellinpainting.medellingpaintingapi.servicesinterfaces;

import com.medellinpainting.medellingpaintingapi.entities.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IProjectService {
    List<Project> list();
    Page<Project> listPaged(Pageable pageable);
    List<Project> listPublished();
    List<Project> listByCategory(String category);
    Optional<Project> listId(Long id);
    Project insert(Project p);
    void update(Project p);
    void delete(Long id);
    void clearImageUrl(String url);
    void removeUrlFromGallery(String url);
}
