const express = require('express');
const cors = require('cors');
const app = express();
const request = require('request');

app.use(cors());

app.get('/mensajeGrupo03', (request, response) => {
    response.json({ "servicio":[{"mensaje": "SERVICIO GRUPO 03"}] });
});

app.listen('3000', () => {
    console.log('corriendo')
})
