package org.iproduct.spring.webmvc.model;

import java.util.ArrayList;
import java.util.Collection;

public class ArticlesSelection {
    private Collection<Long> selectedArticleIds = new ArrayList<>();

    public Collection<Long> getArticleIds() {
        return selectedArticleIds;
    }

    public void setArticleIds(Collection<Long> selectedArticleIds) {
        this.selectedArticleIds = selectedArticleIds;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(selectedArticleIds);
        return sb.toString();
    }
}
