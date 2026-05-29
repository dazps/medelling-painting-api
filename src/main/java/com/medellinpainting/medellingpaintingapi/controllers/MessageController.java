package com.medellinpainting.medellingpaintingapi.controllers;

import com.medellinpainting.medellingpaintingapi.dtos.MessageDTO;
import com.medellinpainting.medellingpaintingapi.entities.Message;
import com.medellinpainting.medellingpaintingapi.servicesinterfaces.IMessageService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    private IMessageService mS;

    @GetMapping("/lista")
    public ResponseEntity<List<MessageDTO>> listar() {
        ModelMapper m = new ModelMapper();
        List<MessageDTO> lista = mS.list()
                .stream()
                .map(msg -> m.map(msg, MessageDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/no-leidos")
    public ResponseEntity<List<MessageDTO>> listarNoLeidos() {
        ModelMapper m = new ModelMapper();
        List<MessageDTO> lista = mS.listUnread()
                .stream()
                .map(msg -> m.map(msg, MessageDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        ModelMapper m = new ModelMapper();
        Optional<Message> msg = mS.listId(id);
        if (msg.isPresent()) {
            return ResponseEntity.ok(m.map(msg.get(), MessageDTO.class));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Message not found");
    }

    @PostMapping("/nuevo")
    public ResponseEntity<?> registrar(@Valid @RequestBody MessageDTO dto) {
        ModelMapper m = new ModelMapper();
        Message msg = m.map(dto, Message.class);
        msg.setRead(false);
        Message saved = mS.insert(msg);
        return ResponseEntity.status(HttpStatus.CREATED).body(m.map(saved, MessageDTO.class));
    }

    @PatchMapping("/{id}/leido")
    public ResponseEntity<String> marcarLeido(@PathVariable Long id) {
        if (mS.listId(id).isPresent()) {
            mS.markAsRead(id);
            return ResponseEntity.ok("Message marked as read");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Message not found");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        if (mS.listId(id).isPresent()) {
            mS.delete(id);
            return ResponseEntity.ok("Message deleted");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Message not found");
    }
}
