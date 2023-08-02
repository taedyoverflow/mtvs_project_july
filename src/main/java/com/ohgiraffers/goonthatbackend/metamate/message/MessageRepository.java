package com.ohgiraffers.goonthatbackend.metamate.message;

import com.ohgiraffers.goonthatbackend.metamate.domain.user.MetaUser;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByReceiver(MetaUser user);
    List<Message> findAllBySender(MetaUser user);
}
