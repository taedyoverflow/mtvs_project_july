package com.ohgiraffers.goonthatbackend.metamate.message;

import com.ohgiraffers.goonthatbackend.metamate.domain.user.MetaUser;
import com.ohgiraffers.goonthatbackend.metamate.domain.user.MetaUserRepository;
import com.ohgiraffers.goonthatbackend.metamate.exception.CustomException;
import com.ohgiraffers.goonthatbackend.metamate.exception.ErrorCode;
import com.ohgiraffers.goonthatbackend.metamate.web.dto.user.SessionMetaUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final MetaUserRepository metaUserRepository;

    @Transactional
    public MessageDto write(MessageDto messageDto) {
        MetaUser receiver = metaUserRepository.findByNickname(messageDto.getReceiverNickname());
        MetaUser sender = metaUserRepository.findByNickname(messageDto.getSenderNickname());

        Message message = new Message();
        message.setReceiver(receiver);
        message.setSender(sender);

        message.setTitle(messageDto.getTitle());
        message.setContent(messageDto.getContent());
        message.setDeletedByReceiver(false);
        message.setDeletedBySender(false);
        messageRepository.save(message);

        return MessageDto.toDto(message);
    }


    @Transactional(readOnly = true)
    public List<MessageDto> receivedMessage(MetaUser user) {
        List<Message> messages = messageRepository.findAllByReceiver(user);
        List<MessageDto> messageDtos = new ArrayList<>();

        for(Message message : messages) {
            // message 에서 받은 편지함에서 삭제하지 않았으면 보낼 때 추가해서 보내줌
            if(!message.isDeletedByReceiver()) {
                messageDtos.add(MessageDto.toDto(message));
            }
        }
        return messageDtos;
    }


    @Transactional(readOnly = true)
    public List<MessageDto> sentMessage(MetaUser user) {
        List<Message> messages = messageRepository.findAllBySender(user);
        List<MessageDto> messageDtos = new ArrayList<>();

        for(Message message : messages) {
            // message 에서 받은 편지함에서 삭제하지 않았으면 보낼 때 추가해서 보내줌
            if(!message.isDeletedBySender()) {
                messageDtos.add(MessageDto.toDto(message));
            }
        }
        return messageDtos;
    }

    // 받은 편지 삭제
    @Transactional
    public Long deleteMessageByReceiver(Long id, SessionMetaUser loginUser) {
        Message message = messageRepository.findById(id).orElseThrow(() -> {
            throw new CustomException(ErrorCode.MESSAGE_NOT_FOUND);
        });

        MetaUser metaUser = metaUserRepository.findById(loginUser.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if(metaUser.getId().equals(message.getReceiver().getId())) {
            message.deleteByReceiver();
            if (message.isDeleted()) {
                // 받은사람과 보낸 사람 모두 삭제했으면, 데이터베이스에서 삭제요청
                messageRepository.delete(message);
            }
        } else {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        return message.getId();
    }

    // 보낸 편지 삭제
    @Transactional
    public Long deleteMessageBySender(Long id, SessionMetaUser loginUser) {
        Message message = messageRepository.findById(id).orElseThrow(() -> {
            throw new CustomException(ErrorCode.MESSAGE_NOT_FOUND);
        });

        MetaUser metaUser = metaUserRepository.findById(loginUser.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if(metaUser.getId().equals(message.getSender().getId())) {
            message.deleteBySender();
            if (message.isDeleted()) {
                // 받은사람과 보낸 사람 모두 삭제했으면, 데이터베이스에서 삭제요청
                messageRepository.delete(message);
            }
        } else {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        return message.getId();
    }
}
