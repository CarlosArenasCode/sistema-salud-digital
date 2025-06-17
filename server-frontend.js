// ===== IMPORTACIONES =====
// Importación de módulos necesarios para el servidor
const http = require('http');
const fs = require('fs');
const path = require('path');

// ===== CONFIGURACIÓN =====
// Puerto en el que se ejecutará el servidor
const port = process.env.PORT || 8081;
// Directorio donde se encuentran los archivos del frontend
const publicDir = path.join(__dirname, 'frontend');

// ===== TIPOS MIME =====
// Definición de los tipos MIME para los diferentes archivos que servirá el servidor
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

// ===== CREACIÓN DEL SERVIDOR =====
http.createServer((req, res) => {
  // Registro de solicitudes recibidas
  console.log(`${req.method} ${req.url}`);
  
  // ===== MANEJO DE RUTAS =====
  // Extracción de la ruta solicitada sin parámetros de consulta
  let requestedPath = req.url.split('?')[0];
  // Si se solicita la ruta raíz, se sirve login.html, de lo contrario la ruta solicitada
  let filePath = path.join(publicDir, requestedPath === '/' ? 'login.html' : requestedPath);

  // ===== VERIFICACIÓN DE ARCHIVOS =====
  // Comprueba si el archivo solicitado existe
  fs.exists(filePath, exists => {
    if (!exists) {
      // Si el archivo no existe, devuelve un error 404
      res.writeHead(404, {'Content-Type': 'text/plain'});
      res.end('404 Not Found');
      return;
    }
    
    // ===== ENTREGA DE CONTENIDO =====
    // Determina el tipo de contenido según la extensión del archivo
    let ext = path.extname(filePath);
    let contentType = mimeTypes[ext] || 'application/octet-stream';
    
    // Lee y envía el archivo al cliente
    fs.readFile(filePath, (err, data) => {
      if (err) {
        // Si hay un error al leer el archivo, devuelve un error 500
        res.writeHead(500, {'Content-Type': 'text/plain'});
        res.end('500 Internal Server Error');
        return;
      }
      // Envía el archivo al cliente con el tipo de contenido adecuado
      res.writeHead(200, {'Content-Type': contentType});
      res.end(data);
    });
  });
}).listen(port, () => {
  // ===== INICIALIZACIÓN DEL SERVIDOR =====
  // Mensaje que se muestra cuando el servidor está en funcionamiento
  console.log(`Frontend server running at http://localhost:${port}`);
});
