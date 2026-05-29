package com.medellinpainting.medellingpaintingapi.servicesinterfaces;

import com.medellinpainting.medellingpaintingapi.entities.Message;

import java.util.List;
import java.util.Optional;

public interface IMessageService {
    List<Message> list();
    List<Message> listUnread();
    Optional<Message> listId(Long id);
    Message insert(Message m);
    void markAsRead(Long id);
    void delete(Long id);
}
