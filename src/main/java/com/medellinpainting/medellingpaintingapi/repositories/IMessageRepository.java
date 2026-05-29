package com.medellinpainting.medellingpaintingapi.repositories;

import com.medellinpainting.medellingpaintingapi.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByRead(Boolean read);
}
