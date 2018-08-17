package org.iproduct.spring.comments;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CommentsDemoAnnotationDI {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext("org.iproduct.spring.comments");
        CommentsService supplier = ctx.getBean(CommentsService.class);
        for(int i = 0; i < 6; i++)
        supplier.addComment(ctx.getBean(Comment.class));
//        supplier.addComment(Comment.builder().text("Comment2").email("george@abv.bg").build());
//        supplier.addComment(Comment.builder().text("Comment3").email("john@abv.bg").build());
//        supplier.addComment(Comment.builder().text("Comment4").email("john@abv.bg").build());
//        supplier.addComment(Comment.builder().text("Comment5").email("george@abv.bg").build());
//        supplier.addComment(Comment.builder().text("Comment6").email("john@abv.bg").build());

        CommentsLoggerService logger = ctx.getBean(CommentsLoggerService.class);
        logger.dumpAllComments();

        System.out.println("\nComments of john@abv.bg:");
        logger.dumpCommentsByAuthor("john@abv.bg");
    }
}
