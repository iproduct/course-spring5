package org.iproduct.spring.webmvcsericejpa.initializer;

import lombok.extern.slf4j.Slf4j;
import org.iproduct.spring.webmvcsericejpa.dao.ArticleDao;
import org.iproduct.spring.webmvcsericejpa.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ArticlesInitializer implements CommandLineRunner {

    @Autowired
    private ArticleDao repository;

    public void run(String[] args) {
        // save a couple of customers
        repository.save(new Article("Welcome to Spring 5", "Spring 5 is great beacuse it is ..."));
        repository.save(new Article("Dependency Injection", "Should I use DI or lookup ..."));
        repository.save(new Article("Spring Beans and Wireing", "There are several ways to configure Spring beans."));

        // fetch all customers
        log.info("Customers found with findAll():");
        log.info("-------------------------------");
        for (Article customer : repository.findAll()) {
            log.info(customer.toString());
        }
}
}
