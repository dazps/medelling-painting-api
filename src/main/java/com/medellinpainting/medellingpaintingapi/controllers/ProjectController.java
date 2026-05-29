package com.medellinpainting.medellingpaintingapi.controllers;

import com.medellinpainting.medellingpaintingapi.dtos.ProjectDTO;
import com.medellinpainting.medellingpaintingapi.entities.Project;
import com.medellinpainting.medellingpaintingapi.servicesinterfaces.IProjectService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private IProjectService pS;

    @GetMapping("/lista")
    public ResponseEntity<?> listar(@PageableDefault(size = 20, sort = "id") Pageable pageable) {
        ModelMapper m = new ModelMapper();
        Page<ProjectDTO> page = pS.listPaged(pageable)
                .map(p -> m.map(p, ProjectDTO.class));
        return ResponseEntity.ok(page);
    }

    @GetMapping("/publicados")
    public ResponseEntity<List<ProjectDTO>> listarPublicados() {
        ModelMapper m = new ModelMapper();
        List<ProjectDTO> lista = pS.listPublished()
                .stream()
                .map(p -> m.map(p, ProjectDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/categoria")
    public ResponseEntity<List<ProjectDTO>> listarPorCategoria(@RequestParam("cat") String categoria) {
        ModelMapper m = new ModelMapper();
        List<ProjectDTO> lista = pS.listByCategory(categoria)
                .stream()
                .map(p -> m.map(p, ProjectDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        ModelMapper m = new ModelMapper();
        Optional<Project> project = pS.listId(id);
        if (project.isPresent()) {
            return ResponseEntity.ok(m.map(project.get(), ProjectDTO.class));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found");
    }

    @PostMapping("/nuevo")
    public ResponseEntity<?> registrar(@Valid @RequestBody ProjectDTO dto) {
        ModelMapper m = new ModelMapper();
        Project p = m.map(dto, Project.class);
        Project saved = pS.insert(p);
        return ResponseEntity.status(HttpStatus.CREATED).body(m.map(saved, ProjectDTO.class));
    }

    @PutMapping("/actualiza")
    public ResponseEntity<String> actualizar(@Valid @RequestBody ProjectDTO dto) {
        Optional<Project> existente = pS.listId(dto.getId());
        if (existente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found");
        }
        Project p = existente.get();
        p.setTitle(dto.getTitle());
        p.setCategory(dto.getCategory());
        p.setDescription(dto.getDescription());
        p.setDate(dto.getDate());
        p.setLocation(dto.getLocation());
        p.setImageUrl(dto.getImageUrl());
        p.setVideoUrl(dto.getVideoUrl());
        p.setPublished(dto.getPublished());
        p.setFeatured(dto.getFeatured());
        p.setSurface(dto.getSurface());
        p.setDuration(dto.getDuration());
        pS.update(p);
        return ResponseEntity.ok("Project updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        if (pS.listId(id).isPresent()) {
            pS.delete(id);
            return ResponseEntity.ok("Project deleted");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found");
    }
}
