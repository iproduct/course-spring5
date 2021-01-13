/**
 * THIS HEADER SHOULD BE KEPT INTACT IN ALL CODE DERIVATIVES AND MODIFICATIONS.
 * 
 * This file provided by IPT is for non-commercial testing and evaluation
 * purposes only. IPT reserves all rights not expressly granted.
 *  
 * The security implementation provided is DEMO only and is NOT intended for production purposes.
 * It is exclusively your responsisbility to seek advice from security professionals 
 * in order to secure the REST API implementation properly.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * IPT BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

var fs = require('fs');
var path = require('path');
var express = require('express');
var bodyParser = require('body-parser');
var app = express();

var POSTS_FILE = path.join(__dirname, 'posts.json');

app.set('port', (process.env.PORT || 9001));

app.use('/', express.static(path.join(__dirname, 'public')));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));

// Additional middleware which will set headers that we need on each request.
app.use(function(req, res, next) {
    // Set permissive CORS header - this allows this server to be used only as
    // an API server in conjunction with something like webpack-dev-server.
    res.setHeader('Access-Control-Allow-Origin', '*');
    res.setHeader('Access-Control-Allow-Headers', '*');
    res.setHeader(`Access-Control-Allow-Methods`, `GET, POST, PUT, DELETE, OPTIONS`);
    res.setHeader('Access-Control-Max-Age', 3600 ); // 1 hour
    // Disable caching so we'll always get the latest posts.
    res.setHeader('Cache-Control', 'no-cache');
    next();
});

app.get('/api/posts', function(req, res) {
  fs.readFile(POSTS_FILE, function(err, data) {
    if (err) {
      console.error(err);
      process.exit(1);
    }
    res.json(JSON.parse(data));
  });
});

app.get('/api/posts/:postId', function(req, res) {
  fs.readFile(POSTS_FILE, function(err, data) {
    if (err) {
      console.error(err);
      process.exit(1);
    }
    let posts = JSON.parse(data);
    // NOTE: In a real implementation, we would likely rely on a database or
    // some other approach (e.g. UUIDs) to ensure a globally unique id. We'll
    // treat Date.now() as unique-enough for our purposes.
    const postId = req.params.postId;
    const index = posts.findIndex(c => c.id === postId);
    if(index < 0) {
      res.status(404).json({code: 404, message: `Post with ID=${postId} not found.`});
      return;
    }
    const found = posts[index];
    res.json(found); //200 OK with deleted post in the body
  });
});


app.post('/api/posts', function(req, res) {
  fs.readFile(POSTS_FILE, function(err, data) {
    if (err) {
      console.error(err);
      process.exit(1);
    }
    const posts = JSON.parse(data);
    const newPost = req.body;
    // NOTE: In a real implementation, we would likely rely on a database or
    // some other approach (e.g. UUIDs) to ensure a globally unique id. We'll
    // treat Date.now() as unique-enough for our purposes.
    newPost.id = Date.now() + '';
    posts.push(newPost);
    fs.writeFile(POSTS_FILE, JSON.stringify(posts, null, 4), function(err) {
      if (err) {
        console.error(err);
        process.exit(1);
      }
      res.status(201).location(`/api/posts/${newPost.id}`).json(newPost);
    });
  });
});

app.put('/api/posts/:id', function(req, res) {
  fs.readFile(POSTS_FILE, function(err, data) {
    if (err) {
      console.error(err);
      process.exit(1);
    }
    var posts = JSON.parse(data);
    // NOTE: In a real implementation, we would likely rely on a database or
    // some other approach (e.g. UUIDs) to ensure a globally unique id. We'll
    // treat Date.now() as unique-enough for our purposes.
    const postId = req.params.id;
    const post = req.body;
    if(postId !== post.id) {
      res.status(400).json({code: 400, message: `IDs in the URL and message body are different.`});
      return;
    }
    const index = posts.findIndex(c => c.id === postId);
    if(index < 0) {
      res.status(404).json({code: 404, message: `Post with ID=${postId} not found.`});
      return;
    }
    posts[index] = post;
    fs.writeFile(POSTS_FILE, JSON.stringify(posts, null, 4), function(err) {
      if (err) {
        console.error(err);
        process.exit(1);
      }
      res.json(post); //200 OK with post in the body
    });
  });
});

app.delete('/api/posts/:id', function(req, res) {
  fs.readFile(POSTS_FILE, function(err, data) {
    if (err) {
      console.error(err);
      process.exit(1);
    }
    let posts = JSON.parse(data);
    // NOTE: In a real implementation, we would likely rely on a database or
    // some other approach (e.g. UUIDs) to ensure a globally unique id. We'll
    // treat Date.now() as unique-enough for our purposes.
    const postId = req.params.id;
    const index = posts.findIndex(c => c.id === postId);
    if(index < 0) {
      res.status(404).json({code: 404, message: `Post with ID=${postId} not found.`});
      return;
    }
    const deleted = posts[index]
    posts.splice(index, 1);
    fs.writeFile(POSTS_FILE, JSON.stringify(posts, null, 4), function(err) {
      if (err) {
        console.error(err);
        process.exit(1);
      }
      res.json(deleted); //200 OK with deleted post in the body
    });
  });
});


app.listen(app.get('port'), function() {
  console.log('Server started: http://localhost:' + app.get('port') + '/');
});
