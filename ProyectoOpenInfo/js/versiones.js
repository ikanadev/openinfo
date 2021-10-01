document.addEventListener('DOMContentLoaded',()=>{
  fechData()
})
var nroMini
var nroMiniVista
var nroExposicion
var nroExposicionVista
var nroConcurso
var nroConcursoVista
var nroVisto
//buscar gestiones
const fechData =async()=>{
  try {
    const res=await fetch('http://localhost:9200/openInfo/gestionesHabilitadas')
    const data = await res.json()
    mostrarGestion(data)
  } catch (error) {
    console.log(error)
  }
}
const mostrarGestion = (datos)=>{
  let version= document.getElementById('version')
  for (let item of datos.gestiones) {
    version.innerHTML+=`<li><a href="#!" onclick="mostrarVersiones(${item.id},${item.gestion})">${item.gestion}</a></li>`
  }    
}

//mostrar por versiones de gestion
function mostrarVersiones(cod,gestion){
  document.getElementById('gestion').innerHTML = '';
  document.getElementById('gestion').innerHTML = `OpenInfo ${gestion}`;
    fechDataExpositores(cod)
    fechDataConcursos(cod)
    fechDataMinitalks(cod)
    fechDataResultados(cod)
    setTimeout(function() {
      mostrarIndicadores(cod)
    }, 500);
}
//minitalks
const fechDataMinitalks=async(id)=>{
  try {
    const res = await fetch(`http://localhost:9200/openInfo/actividades/miniTalk?gestionId=${id}`)
    const data = await res.json()
    mostrarMinitalks(data)
  } catch (error) {
    console.log(error)
  }
}
const mostrarMinitalks = (datos)=>{
  nroMini=0
  nroMiniVista=0
  document.getElementById('resultadoMinitalks').innerHTML = '';
  let datosMinitalks=" "
  datosMinitalks = document.getElementById('resultadoMinitalks')
  for(let item of datos.miniTalks){
    nroMini++;
    datosMinitalks.innerHTML+=`
    <li class="list-group-item">
      <p><strong>${item.gradoAcademico} ${item.expositor.nombre}</strong> - <a> ${item.nombre}</a><em class="blockquote-footer"> ${item.descripcion}</em><small> (${item.institucion} - ${item.fecha}).</small></p>  
      <a class="btn btn-danger" style="padding: 0px 13px;" href="${item.linkOficial}" target="_blank">ver</a>
    </li>`
    nroMiniVista=nroMiniVista+item.vistas
  }
}
//expositores
const fechDataExpositores=async(id)=>{
  try {
    const res = await fetch(`http://localhost:9200/openInfo/actividades/proyectosFeria?gestionId=${id}`)
    const data = await res.json()
    mostrarExpositores(data)
  } catch (error) {
    console.log(error)
  }
}
const mostrarExpositores = (datos)=>{
  nroExposicion=0
  nroExposicionVista=0
  nroExposicion=datos.proyectosFeria.length
  document.getElementById('resultadoExpositores').innerHTML = '';
  let datosExpositores = document.getElementById('resultadoExpositores')
  for(let item of datos.proyectosFeria){
    integrantes=""
    console.log(integrantes)
    for(item1 of item.participantes)
    {
      integrantes+=item1.usuario.nombre+" - "
    }
    console.log(integrantes)
    datosExpositores.innerHTML+=`
    <li class="list-group-item">
      <p><strong>${item.equipo.nombre} | ${item.tipoProyecto.nombre}</strong> - <a> ${item.nombre}</a>
      <br>
      <strong>Participantes: </strong> - ${integrantes}
      <em class="blockquote-footer"> ${item.descripcion}</em><small> (${item.area} - ${item.equipo.gestion.gestion}).</small></p>  
      <a class="btn btn-danger" style="padding: 0px 13px;" href="${item.linkOficial}" target="_blank">ver</a>  
      <a class="btn btn-primary" style="padding: 0px 13px;" href="${item.linkOficial}" target="_blank">pdf</a>
    </li>`
    nroExposicionVista=nroExposicionVista+item.vistas
  }
  
}
//concursos
const fechDataConcursos=async(id)=>{
  try {
    const res = await fetch(`http://localhost:9200/openInfo/actividades/proyectosConcurso?gestionId=${id}`)
    const data = await res.json()
    mostrarConcursos(data)
  } catch (error) {
    console.log(error)
  }
}
const mostrarConcursos = (datos)=>{
  nroConcurso=0
  nroConcursoVista=0
  document.getElementById('resultadoConcursos').innerHTML = '';
  let datosConcursos = document.getElementById('resultadoConcursos')
  for(let item of datos.proyectosConcurso){
    integrantes=""
    console.log(integrantes)
    for(item1 of item.participantes)
    {
      integrantes+=item1.usuario.nombre+" - "
    }
    datosConcursos.innerHTML+=`
    <li class="list-group-item">
      <p><strong>${item.equipo.nombre} | ${item.tipoProyecto.nombre}</strong> - <a> ${item.nombre}</a>
      <br>
      <strong>Participantes: </strong> - ${integrantes}
      <em class="blockquote-footer"> ${item.descripcion}</em><small> (${item.area} - ${item.equipo.gestion.gestion}).</small></p>  
      <a class="btn btn-danger" style="padding: 0px 13px;" href="${item.linkOficial}" target="_blank">ver</a>  
      <a class="btn btn-primary" style="padding: 0px 13px;" href="${item.linkOficial}" target="_blank">pdf</a>
    </li>`
    nroConcursoVista=nroConcursoVista+item.vistas
  }
  nroConcurso=datos.proyectosConcurso.length

}

//resultados 

const fechDataResultados=async(id)=>{
  try {
    const res = await fetch(`http://localhost:9200/openInfo/ranking?gestionId=${id}`)
    const data = await res.json()
    mostrarResultados(data)
  } catch (error) {
    console.log(error)
  }
}
const mostrarResultados = (datos)=>{
  document.getElementById('resultadoResultados').innerHTML = '';
  let datosResultados = document.getElementById('resultadoResultados')
  datosResultados.innerHTML=`<p>Minitalks</p>`
  for(let item of datos.talks_mas_vistos){
    
    datosResultados.innerHTML+=`
    <li class="list-group-item">
      <p><strong>${item.nombre}</strong> - <em> vistas: ${item.vistas} veces </em> - <a href="${item.linkOficial}" target="_blank" > ver </a>.</p>  
    </li>`
  }
  datosResultados.innerHTML+=`<br><p>Exposiciones</p>`
  for(let item of datos.mas_vistos_feria){

    datosResultados.innerHTML+=`
    <li class="list-group-item">
      <p><strong>${item.nombre}</strong> - <em> vistas: ${item.vistas} veces </em> - <a href="${item.linkOficial}" target="_blank" > ver </a>.</p>  
    </li>`
  }
  datosResultados.innerHTML+=`<br><p>Ganadores del Concurso</p>`
  for(let item of datos.mejor_puntuados){

    datosResultados.innerHTML+=`
    <li class="list-group-item">
      <p><strong>${item.nombre}</strong> - <em> Calificación: ${item.calificacion} pts. </em> - <a href="${item.linkOficial}" target="_blank" > ver </a>.</p>  
    </li>`
  }
}
//Indicadores
const mostrarIndicadores = ()=>{
  document.getElementById('resultadoIndicadores').innerHTML = `
  <tbody>
    <tr>
      <td>Total de Minitalks:</td>
      <td>${nroMini}</td>
    </tr>
    <tr>
      <td>Total de Vistas Minitalks:</td>
      <td>${nroMiniVista}</td>
    </tr>
    <tr>
      <td>Total de Exposiciones:</td>
      <td>${nroExposicion}</td>
    </tr>
    <tr>
      <td>Total de Vistas Exposiciones:</td>
      <td>${nroExposicionVista}</td>
    </tr>
    <tr>
      <td>Total de Concursos:</td>
      <td>${nroConcurso}</td>
    </tr>
    <tr>
      <td>Total de Vistas Concurso:</td>
      <td>${nroConcursoVista}</td>
    </tr>
    <tr>
    <td>Total de grupos participantes:</td>
    <td>${nroMini+nroExposicion+nroConcurso}</td>
  </tr>
    <tr>
      <td>Total General de Vistas:</td>
      <td>${nroMiniVista+nroExposicionVista+nroConcursoVista}</td>
    </tr>
  </tbody>`
}


