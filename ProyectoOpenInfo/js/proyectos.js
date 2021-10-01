const DefaultGrade = 'Encargado Proyecto'
const data = {
  cont: document.getElementById('resultados'),
  fairProjects: [],
  contestProjects: [],
  isFetchedFair: false,
  isFetchedContest: false,
}
const getFairProjects = async () => {
  data.cont.innerHTML = '<div class="loader">Cargando...</div>'
  const respFetch = await fetch(ip + urlProyectoFeria)
  const resp = await respFetch.json()
  data.fairProjects = resp.proyectosFeria
  data.isFetchedFair = true
}
const getContestProjects = async () => {
  data.cont.innerHTML = '<div class="loader">Cargando...</div>'
  const respFetch = await fetch(ip + urlProyectosConcurso)
  const resp = await respFetch.json()
  data.contestProjects = resp.proyectosConcurso
  data.isFetchedContest = true
}
const getHeaderContent = (banner, nombre) => `
<div data-aos="zoom-in" class="container">
  <div class="row">
    <div class="col proyectoContenedor">
      <div class="container">
        <div class="proyecto fondoproyecto" style="background-image: url(${ip}${urlImagenBanner}${banner});">
          <div class="contentDelantera">
            <p class="text-center proyectoTitulo">${nombre}</p>
            <div class="container">
              <div class="row">
`;

const getContent = (participantes) => participantes.map((participante, i) => `
<div class="col-6">
  <div class="mb-3">
    <div class="row no-gutters">
      <div class="col-4">
        <img src="./img/perfil1.jpg" class="card-img" alt="...">
      </div>
      <div class="col-8">
        <div class="card-body cardparticipante">
          <p class="card-text participante">Participante:</p>
          <p class="card-text participante">${participante.usuario.nombre}</p>
          <p class="card-text participante"><small>${participante.gradoAcademico || DefaultGrade}</small></p>
        </div>
      </div>
    </div>
  </div>
</div>${i % 2 !== 0 ? '<div class="w-100"></div>' : ''}
`).join('');

const getContentFoot = (proyecto) => `
              </div>
            </div>
          </div>
          <div class="content">
            <p class="proyectoTituloAtras">${proyecto.nombre}</p>
            <p class="proyectoDescripcion">
              ${proyecto.descripcion && proyecto.descripcion != 'null' ? `<a href="${proyecto.descripcion}" target="_blank">Ver Tríptico</a>` : ''}
            </p>
            <p class="proyectoNivel">Nombre de Equipo: ${proyecto.equipo.nombre}</p>
              <p class="proyectoNivel">Encargado: ${proyecto.equipo.encargado.nombre}</p>
              <p class="proyectoNivel">Tipo de Equipo: ${proyecto.equipo.tipoEquipo.nombre}</p>
              <p class="proyectoNivel">Tipo de Proyecto: ${proyecto.tipoProyecto ? proyecto.tipoProyecto.nombre : 'Sin Categoria'}</p>
              <!-- Button trigger modal -->
              <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modal" onclick="contarvista('${proyecto.id}', '${proyecto.linkOficial}');">
              Ver video
              </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>`;

const shuffle = (array) => {
  for (let i = array.length - 1; i > 0; i--) {
    let j = Math.floor(Math.random() * (i + 1));
    [array[i], array[j]] = [array[j], array[i]];
  }
}

async function mostrarProyecto(cod) {
  if (!data.isFetchedFair) {
    await getFairProjects()
  }
  const projects = data.fairProjects.filter((project) => project.tipoProyecto && project.tipoProyecto.id == cod)
  shuffle(projects)
  let content = projects.map((pr) => `
    ${getHeaderContent(pr.banner, pr.nombre)}
    ${getContent(pr.participantes)}
    ${getContentFoot(pr)}
  `).join('')
  data.cont.innerHTML = `<h6>Proyectos encontrados en esta categoria: ${projects.length}</h6>${content}`
}

async function mostrarProyectoConcurso(cod) {
  if (!data.isFetchedContest) {
    await getContestProjects()
  }
  const projects = data.contestProjects.filter((project) => project.tipoProyecto && project.tipoProyecto.id == cod)
  shuffle(projects)
  let content = projects.map((pr) => `
    ${getHeaderContent(pr.banner, pr.nombre)}
    ${getContent(pr.participantes)}
    ${getContentFoot(pr)}
  `).join('')
  data.cont.innerHTML = `<h6>Proyectos encontrados en esta categoria: ${projects.length}</h6>${content}`
}



function contarvista(id, video) {
  $('.myFrame').attr('src', video);
  const titleCont = document.getElementById('staticBackdropLabel')
  let pr = data.contestProjects.find((pr) => pr.id == id)
  if (!pr) {
    pr = data.fairProjects.find((pr) => pr.id == id)
  }
  if (pr) titleCont.innerHTML = pr.nombre
  $.ajax({
    url: ip + urlView,
    type: "Post",
    data: JSON.stringify({
      id: id,
      tipo: 'proyecto',
    }),
    contentType: 'application/json; charset=utf-8',
    success: function (data) {
    },
    failure: function (data) {
      console.log(data.responseText);
    },
    error: function (data) {
      console.log(data.responseText);
    }
  });
}
function pausar() {
  $('.myFrame').removeAttr('src');
}
