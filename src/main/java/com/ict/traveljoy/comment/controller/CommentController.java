package com.ict.traveljoy.comment.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ict.traveljoy.comment.service.CommentDTO;
import com.ict.traveljoy.comment.service.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // 댓글 작성
    @PostMapping("/create")
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO) {
        CommentDTO createdComment = commentService.createComment(commentDTO);
        return ResponseEntity.ok(createdComment);
    }

    // 댓글 ID로 조회
    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Long id) {
        Optional<CommentDTO> commentDTO = commentService.getCommentById(id);
        if (commentDTO.isPresent()) {
            return ResponseEntity.ok(commentDTO.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 특정 대상(targetId)으로 댓글 조회
    @GetMapping("/target/{targetId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByTargetId(@PathVariable Long targetId) {
        List<CommentDTO> comments = commentService.getCommentsByTargetId(targetId);
        return ResponseEntity.ok(comments);
    }

    // 댓글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    // 댓글 업데이트
    @PutMapping("/{id}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long id, @RequestBody CommentDTO commentDTO) {
        CommentDTO updatedComment = commentService.updateComment(id, commentDTO);
        if (updatedComment != null) {
            return ResponseEntity.ok(updatedComment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
