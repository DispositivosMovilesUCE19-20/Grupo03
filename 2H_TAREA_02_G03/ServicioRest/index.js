const fs = require('fs');
const archivo = require('body-parser');
const archivoParse = require('js2xmlparser');
const express = require('express');
const app = express();
var router = express.Router();

/*SERVICIO PARA MENSAJES*/

router.get('/mensajeGrupo03', function(req, res, next) {
  res.json({ "servicio":[{"mensaje": "SERVICIO GRUPO 03"}] });
});

router.get('/editar', function(req, res, next) {
  res.json({ "servicio":[{"mensaje": "El estudiante se ha editado correctamente..!!"}] });
});

router.get('/eliminar', function(req, res, next) {
  res.json({ "servicio":[{"mensaje": "El estudiante se ha eliminado correctamente..!!"}] });
});

router.get('/error', function(req, res, next) {
  res.json({ "servicio":[{"mensaje": "Se produjo un problema en el Edición/Eliminación..!!"}] });
});
router.get('/mensajeExamen', function(req, res, next) {
  res.json({ "servicio":[{"mensaje": "EXAMEN OPTATIVA III"}] });
});

/*SERVICIO PARA ARCHIVO XML*/

app.use(archivo.urlencoded({ extended: false }));
app.use(archivo.json());
app.post('/crearArchivo', function (req, res) {
  var json = req.body;
  var xml = archivoParse.parse("servicio", json);
  console.log(json)
  console.log(xml)
  fs.writeFile('estudiantes.xml', xml, (error => {
    if (error) {
      throw error;
    }
    console.log('NO SE HA PODIDO CREAR EL ARCHIVO..');
    res.send(200)
    res.end()
  }))
})

app.listen(3000, () => {
  console.log('Server started!')
});

module.exports = router;