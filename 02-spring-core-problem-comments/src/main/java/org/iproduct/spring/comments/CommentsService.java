package org.iproduct.spring.comments;

import java.util.List;

public interface CommentsService {
    void addComment(Comment comment);
    List<Comment> getAllComments();
    List<Comment> getCommentsByEmail(String email);
}
