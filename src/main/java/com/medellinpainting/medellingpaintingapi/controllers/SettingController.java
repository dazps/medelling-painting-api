package com.medellinpainting.medellingpaintingapi.controllers;

import com.medellinpainting.medellingpaintingapi.dtos.SettingDTO;
import com.medellinpainting.medellingpaintingapi.entities.Setting;
import com.medellinpainting.medellingpaintingapi.servicesinterfaces.ISettingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/setting")
public class SettingController {

    @Autowired
    private ISettingService sS;

    @GetMapping("/lista")
    public ResponseEntity<List<SettingDTO>> listar() {
        ModelMapper m = new ModelMapper();
        List<SettingDTO> lista = sS.list()
                .stream()
                .map(s -> m.map(s, SettingDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/social")
    public ResponseEntity<List<SettingDTO>> listarSocial() {
        ModelMapper m = new ModelMapper();
        List<SettingDTO> lista = sS.list()
                .stream()
                .filter(s -> s.getKey() != null && s.getKey().startsWith("social."))
                .map(s -> m.map(s, SettingDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        ModelMapper m = new ModelMapper();
        Optional<Setting> s = sS.listId(id);
        if (s.isPresent()) {
            return ResponseEntity.ok(m.map(s.get(), SettingDTO.class));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Setting not found");
    }

    @GetMapping("/clave")
    public ResponseEntity<?> buscarPorClave(@RequestParam("key") String key) {
        ModelMapper m = new ModelMapper();
        Optional<Setting> s = sS.findByKey(key);
        if (s.isPresent()) {
            return ResponseEntity.ok(m.map(s.get(), SettingDTO.class));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Setting not found");
    }

    @PostMapping("/nuevo")
    public ResponseEntity<?> registrar(@RequestBody SettingDTO dto) {
        ModelMapper m = new ModelMapper();
        Setting s = m.map(dto, Setting.class);
        Setting saved = sS.insert(s);
        return ResponseEntity.status(HttpStatus.CREATED).body(m.map(saved, SettingDTO.class));
    }

    @PutMapping("/actualiza")
    public ResponseEntity<String> actualizar(@RequestBody SettingDTO dto) {
        Optional<Setting> existente = sS.listId(dto.getId());
        if (existente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Setting not found");
        }
        Setting s = existente.get();
        s.setKey(dto.getKey());
        s.setValue(dto.getValue());
        sS.update(s);
        return ResponseEntity.ok("Setting updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        if (sS.listId(id).isPresent()) {
            sS.delete(id);
            return ResponseEntity.ok("Setting deleted");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Setting not found");
    }
}
