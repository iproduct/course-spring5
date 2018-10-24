package org.iproduct.spring.comments;

public interface CommentsLoggerService {
    void dumpAllComments();
    void dumpCommentsByAuthor(String authorEmail);
//    CommentsService getCommentsService();
//    void setCommentsService(CommentsService supplier);
}
