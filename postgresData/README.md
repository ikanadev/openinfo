### Postgres Data

Debido a que la base de datos esta corriendo dentro de un contenedor Docker, la información se perderá una vez que dicho contenedor se vaya a detener,
por esta razón, dentro de Docker se tiene el concepto de volúmenes, que básicamente sirva para guardar algún tipo de información en la máquina host y está será utilizada y compartida con cuantos contenedores se desee y la información no se perderá a través de reinicios.

**Este directorio no debería ser borrado, a menos que se requiera un reset total del sistema, previo a eso se debería hacer un backup**