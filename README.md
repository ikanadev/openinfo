# Sofware OpenInfo
Esta es una guía básica del proceso de deploy de sistema desarrollado para la feria virtual OpenInfo
Se decidió utilizar un enfoque similar a micro-servicios componiendo el sistema en sub-sistemas que interactuan entre si.
Previniendo de esta manera tener un sistema gigante que se encargue de todo.

OpenInfo se compone de 4 sistemas.

- **Base de datos** directorio `postgresData`
- **Backend (JAVA)** directorio `ApiUsuarios`
- **Front-end** directorio `ProyectoOpenInfo`
- **Panel de administrador** directorio `openinfo-admin`

El manejo de dichos sistemas, se realiza utilizando la tecnología Docker, si el administrador de turno tiene experiencia con tal tecnología, la adminstración del mismo no debería suponer mucho problema.

Caso contrario, se ha incluido una serie de Makefile scripts para facilitar el proceso de iniciar, terminar y contruir dichos sub-sistemas.

### Setup inicial
El sistema esta diseñado para correr en cualquier Sistema Operativo basado en Linux, el cual sólo debe tener intalado: `Docker` y `Git`

1. *Verifica que docker pueda correr sin problemas, siguiendo esta [guía](https://docs.docker.com/get-started/#start-the-tutorial), sólo la sección **Start the tutorial***
2. *Clona este repositorio en un directorio de fácil acceso dentro de tu servidor:
  `git clone https://github.com/vmkevv/openinfo.git`.
3. *Todos los comandos deben ser ejecutados desde el directorio `openinfo`.*

**Es importante tener en cuenta que tanto `open-admin` y `ProyectoOpenInfo` dependen de `ApiUsuarios` y esta última depende de `postgresData`** por tanto se recomienta seguir los siguientes pasos en orden. 

### Manejo el sistema
##### Preparación previa
`make setup` crea una red por la cual se puedan comunicar los distintos sub-sistemas, sólo es necesario ejecutarlo una única vez en el servidor.

##### Base de datos
`make run-db` Inicia el servicio de base de datos.

`make stop-db` Detiene el servicio de base de datos.

##### Backend (Java)
`make build-api` Inicia el proceso de build del backend (necesario si se hiceron cambios en el código fuente). El servicio debe estar detenido antes de hacer el build.

`make run-api` Inicia el servicio de backend, usualmente usado cuando se está haciendo el proceso de deploy por primera vez, cuando el servicio se detuvo por una razón desconocida o luego de hacer un `make build-api`.

`make stop-api` Detiene el servicio de backend.

*Los comandos similares en los siguientes servicios tienen las **mismas advertencias, o recomendaciones***

##### Front-end
`make build-front` Inicia el proceso de build del front-end.

`make run-front` Inicia el servicio de front.

`make stop-front` Detiene el servicio de front.

##### Panel de administrador
`make build-admin` Inicia el proceso de build del panel de admin.

`make run-admin` Inicia el servicio del panel de admin.

`make stop-admin` Detiene el servicio del panel de admin.

### Cambios en el sistema
Si se require hacer cambios al sistema, el proceso es muy similar para cualquiera de los 3 sub-sistemas, por ejemplo, para hacer un cambio dentro del *Front-end*, lo que tendríamos que hacer es:

1. Clonar el repositorio para realizar los cambios: `git clone https://github.com/vmkevv/openinfo.git`
2. Realizar los cambios correspondientes, dado que son cambios en el *Front-end*, los cambios en este caso sólo deberían ser dentro del directorio `ProyectoOpenInfo`
3. Una vez realizados los cambios, subirlos al repositorio usando git.
    - `git add .`
    - `git commit -m "una descripción de los cambios realizados"`
    - `git push origin main`
4. Conectarse al servidor usando `ssh`
5. Dentro del directorio `openinfo` en el servidor, actualizar el còdigo:
    - `git pull`
6. Debido a que acabamos de actualizar el código en *Front-end*, necesitamos **detener, hacerl el build e iniciar** el correspondiente servicio.
    - `make stop-front`
    - `make build-front`
    - `make run-front`

De manera similar, si se requieren hacer cambios en algún otro servicio, los pasos son los mismos, pero los comandos y directorios deberían ser correspondientes a dicho servicio.
