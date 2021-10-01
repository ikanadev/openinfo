'use strict'
  const url ='http://localhost:9200/openInfo/actividades/proyectosFeria'
  fetch(url)
  .then(res=>res.json())
  .then(data=>{
    for(var i in data.proyectosFeria){
      if(data.proyectosFeria[i].tipoProyecto.id==8){
        let elementos = document.getElementById('resultados')
        elementos.innerHTML+=`
        <div data-aos="zoom-in" class="container">
        <div class="row">
          <div class="col">
            <div class="container">
              <div class="proyecto fondoproyecto" style="background-image: url(http://localhost:9200/openInfo/img/${data.proyectosFeria[i].banner});">
                <div class="contentDelantera">
                  <p class="text-center proyectoTitulo">${data.proyectosFeria[i].nombre}</p>
                  <div class="container">
                    <div class="row">
                      <div class="col">
                        <div class="mb-3">
                          <div class="row no-gutters">
                            <div class="col-4">
                              <img src="./img/perfil1.jpg" class="card-img" alt="...">
                            </div>
                            <div class="col-8">
                              <div class="card-body cardparticipante">
                                <p class="card-text participante">Participante:</p>
                                <p class="card-text participante">Univ. Mijael Romualdo Mercado calcina</p>
                                <p class="card-text"><small>Representante del Grupo</small></p>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div class="col">
                        <div class="mb-3">
                          <div class="row no-gutters">
                            <div class="col-4">
                              <img src="./img/perfil1.jpg" class="card-img" alt="...">
                            </div>
                            <div class="col-8">
                              <div class="card-body cardparticipante">
                                <p class="card-text participante">Participante:</p>
                                <p class="card-text participante">Univ. Mijael Romualdo Mercado calcina</p>
                                <p class="card-text"><small>Representante del Grupo</small></p>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div class="w-100"></div>
                      <div class="col">
                        <div class="mb-3">
                          <div class="row no-gutters">
                            <div class="col-4">
                              <img src="./img/perfil1.jpg" class="card-img" alt="...">
                            </div>
                            <div class="col-8">
                              <div class="card-body cardparticipante">
                                <p class="card-text participante">Participante:</p>
                                <p class="card-text participante">Univ. Mijael Romualdo Mercado calcina</p>
                                <p class="card-text"><small>Representante del Grupo</small></p>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div class="col">
                        <div class="mb-3">
                          <div class="row no-gutters">
                            <div class="col-4">
                              <img src="./img/perfil1.jpg" class="card-img" alt="...">
                            </div>
                            <div class="col-8">
                              <div class="card-body cardparticipante">
                                <p class="card-text participante">Participante:</p>
                                <p class="card-text participante">Univ. Mijael Romualdo Mercado calcina</p>
                                <p class="card-text"><small>Representante del Grupo</small></p>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="content">
                  <p class="proyectoTituloAtras">${data.proyectosFeria[i].nombre}</p>
                  <p class="proyectoDescripcion">${data.proyectosFeria[i].descripcion}</p>
                  <p class="proyectoNivel">Nombre de Equipo: ${data.proyectosFeria[i].equipo.nombre}</p>
                  <p class="proyectoNivel">Encargado: ${data.proyectosFeria[i].equipo.encargado.nombre}</p>
                  <p class="proyectoNivel">Tipo de Equipo: ${data.proyectosFeria[i].equipo.tipoEquipo.nombre}</p>
                  <!-- Button trigger modal -->
                  <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#mod${data.proyectosFeria[i].id}">
                  Ver video
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      
        <div class="modal fade" id="mod${data.proyectosFeria[i].id}" data-backdrop="static" data-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
          <div class="modal-dialog modal-lg">
            <div class="modal-content">
              <div class="modal-header">
                <h5 class="modal-title" id="staticBackdropLabel">${data.proyectosFeria[i].nombre}</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span>
                </button>
              </div>
              <div class="modal-body">
              <div class="embed-responsive embed-responsive-16by9">
              <iframe class="embed-responsive-item" src="${data.proyectosFeria[i].linkVideo}" allowfullscreen></iframe>
            </div>
              </div>
              <div class="modal-footer">

                <button type="button" class="btn btn-primary" data-dismiss="modal">Salir</button>
              </div>
            </div>
          </div>
        </div>
        `;
        console.log(data.proyectosFeria[i].nombre);
      }

    }
  }).catch(err=>console.log(err))
