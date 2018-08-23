# course-spring5
Demos and examples from course Introduction to Spring 5 provided by IPT - Intellectual Products &amp; Technologies

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
