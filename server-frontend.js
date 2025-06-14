const http = require('http');
const fs = require('fs');
const path = require('path');

const port = process.env.PORT || 8081;
const publicDir = path.join(__dirname, 'frontend');

const mimeTypes = {
  '.html': 'text/html',
  '.css': 'text/css',
  '.js': 'application/javascript',
  '.json': 'application/json',
  '.png': 'image/png',
  '.jpg': 'image/jpeg',
  '.jpeg': 'image/jpeg',
  '.svg': 'image/svg+xml',
  '.ico': 'image/x-icon'
};

http.createServer((req, res) => {
  console.log(`${req.method} ${req.url}`);  let requestedPath = req.url.split('?')[0];
  let filePath = path.join(publicDir, requestedPath === '/' ? 'login.html' : requestedPath);

  fs.exists(filePath, exists => {
    if (!exists) {
      res.writeHead(404, {'Content-Type': 'text/plain'});
      res.end('404 Not Found');
      return;
    }
    let ext = path.extname(filePath);
    let contentType = mimeTypes[ext] || 'application/octet-stream';
    fs.readFile(filePath, (err, data) => {
      if (err) {
        res.writeHead(500, {'Content-Type': 'text/plain'});
        res.end('500 Internal Server Error');
        return;
      }
      res.writeHead(200, {'Content-Type': contentType});
      res.end(data);
    });
  });
}).listen(port, () => {
  console.log(`Frontend server running at http://localhost:${port}`);
});
