package com.medellinpainting.medellingpaintingapi.servicesimplements;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.medellinpainting.medellingpaintingapi.entities.Project;
import com.medellinpainting.medellingpaintingapi.repositories.IProjectRepository;
import com.medellinpainting.medellingpaintingapi.servicesinterfaces.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProjectServiceImplement implements IProjectService {

    @Autowired
    private IProjectRepository pR;

    @Override
    public List<Project> list() {
        return pR.findAll();
    }

    @Override
    public Page<Project> listPaged(Pageable pageable) {
        return pR.findAll(pageable);
    }

    @Override
    public List<Project> listPublished() {
        return pR.findByPublished(true);
    }

    @Override
    public List<Project> listByCategory(String category) {
        return pR.findByCategoryIgnoreCase(category);
    }

    @Override
    public Optional<Project> listId(Long id) {
        return pR.findById(id);
    }

    @Override
    public Project insert(Project p) {
        return pR.save(p);
    }

    @Override
    public void update(Project p) {
        pR.save(p);
    }

    @Override
    public void delete(Long id) {
        pR.deleteById(id);
    }

    @Override
    public void clearImageUrl(String url) {
        pR.clearImageUrl(url);
    }

    @Override
    public void removeUrlFromGallery(String url) {
        ObjectMapper mapper = new ObjectMapper();
        for (Project p : pR.findByVideoUrlContaining(url)) {
            try {
                List<Map<String, String>> items = mapper.readValue(p.getVideoUrl(), new TypeReference<>() {});
                items.removeIf(item -> url.equals(item.get("url")));
                p.setVideoUrl(items.isEmpty() ? null : mapper.writeValueAsString(items));
                pR.save(p);
            } catch (Exception ignored) {}
        }
    }
}
