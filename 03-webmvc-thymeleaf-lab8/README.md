# course-spring5
Demos and examples from course Introduction to Spring 5 provided by IPT - Intellectual Products &amp; Technologies
For Spring 5 Course in FMI see the Wiki of this repo: https://github.com/iproduct/course-spring5/wiki

## Details

### Strating MongoDB
mongod --dbpath=c:\mongo-data

### Starting Mongo command line client
mongo 127.0.0.1:27017

### Use DB articles
use articles

### Finding all articles
db.articles.find()

### Add new article
db.articles.insertOne({title:"Spring New Article", content:"New Content ..."})

### curl POST Article
curl -X POST "http://localhost:8080/api/articles" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"content\": \"Content 1 ...\", \"title\": \"Title 1\"}"
