package com.ict.traveljoy.comment.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.ict.traveljoy.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.traveljoy.comment.repository.Comment;
import com.ict.traveljoy.comment.repository.CommentRepository;
import com.ict.traveljoy.users.repository.Users;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;

    // 댓글 작성
    public CommentDTO createComment(CommentDTO commentDTO) {

        Comment comment = commentDTO.toEntity();
        Comment savedComment = commentRepository.save(comment);
        return CommentDTO.toDTO(savedComment);
    }

    // 댓글 조회
    public Optional<CommentDTO> getCommentById(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        return comment.map(CommentDTO::toDTO);
    }

    // 특정 대상(targetId)으로 댓글 조회
    public List<CommentDTO> getCommentsByTargetId(Long targetId) {
        List<Comment> comments = commentRepository.findAll(); // 필요하다면 custom query로 교체
        return comments.stream()
                .filter(comment -> comment.getTargetId().equals(targetId))
                .map(CommentDTO::toDTO)
                .toList();
    }

    // 댓글 삭제 (isDelete 플래그 사용)
    public void deleteComment(Long id) {
        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();
            comment.setIsDelete(1);  // isDelete 플래그를 1로 설정
            comment.setDeleteDate(LocalDateTime.now());
            commentRepository.save(comment);
        }
    }

    // 댓글 업데이트
    public CommentDTO updateComment(Long id, CommentDTO commentDTO) {
        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();
            comment.setContents(commentDTO.getContents());
            comment.setIsActive(commentDTO.getIsActive() ? 1 : 0);
            commentRepository.save(comment);
            return CommentDTO.toDTO(comment);
        }
        return null; // 댓글이 없을 경우
    }
}
