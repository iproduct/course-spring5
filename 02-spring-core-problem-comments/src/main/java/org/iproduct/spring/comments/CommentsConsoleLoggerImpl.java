package org.iproduct.spring.comments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("logger")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class CommentsConsoleLoggerImpl implements CommentsLoggerService {
    private CommentsService supplier;

    @Override
    public void dumpAllComments() {
        supplier.getAllComments().forEach(System.out::println);
    }

    @Override
    public void dumpCommentsByAuthor(String authorEmail) {
        supplier.getCommentsByEmail(authorEmail).forEach(System.out::println);
    }

    public CommentsService getCommentsService() {
        return supplier;
    }

    @Autowired
    protected void setCommentsService(CommentsService supplier) {
        this.supplier = supplier;
    }
}
