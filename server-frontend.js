// Simple Node.js static server for the frontend
// Ejecuta: node server-frontend.js

const http = require('http');
const fs = require('fs');
const path = require('path');

const PORT = 8081;
const base = path.join(__dirname, 'frontend');

const mimeTypes = {
  '.html': 'text/html',
  '.css': 'text/css',
  '.js': 'application/javascript',
  '.json': 'application/json',
  '.png': 'image/png',
  '.jpg': 'image/jpeg',
  '.gif': 'image/gif',
  '.svg': 'image/svg+xml',
};

http.createServer((req, res) => {
  // Si la ruta es '/', servir login.html en vez de index.html
  let filePath = path.join(base, req.url === '/' ? '/login.html' : req.url);
  fs.readFile(filePath, (err, data) => {
    if (err) {
      res.writeHead(404, {'Content-Type': 'text/plain'});
      res.end('404 Not Found');
    } else {
      const ext = path.extname(filePath);
      res.writeHead(200, {'Content-Type': mimeTypes[ext] || 'application/octet-stream'});
      res.end(data);
    }
  });
}).listen(PORT, () => {
  console.log(`Servidor frontend corriendo en http://localhost:${PORT}`);
});
