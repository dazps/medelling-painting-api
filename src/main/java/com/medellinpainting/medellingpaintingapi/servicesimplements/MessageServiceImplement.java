package com.medellinpainting.medellingpaintingapi.servicesimplements;

import com.medellinpainting.medellingpaintingapi.entities.Message;
import com.medellinpainting.medellingpaintingapi.repositories.IMessageRepository;
import com.medellinpainting.medellingpaintingapi.servicesinterfaces.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImplement implements IMessageService {

    @Autowired
    private IMessageRepository mR;

    @Override
    public List<Message> list() {
        return mR.findAll();
    }

    @Override
    public List<Message> listUnread() {
        return mR.findByRead(false);
    }

    @Override
    public Optional<Message> listId(Long id) {
        return mR.findById(id);
    }

    @Override
    public Message insert(Message m) {
        return mR.save(m);
    }

    @Override
    public void markAsRead(Long id) {
        mR.findById(id).ifPresent(m -> {
            m.setRead(true);
            mR.save(m);
        });
    }

    @Override
    public void delete(Long id) {
        mR.deleteById(id);
    }
}
