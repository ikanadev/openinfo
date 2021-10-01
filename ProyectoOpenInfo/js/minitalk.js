obtenerDatos();

function obtenerDatos(){
  const url=ip+urlMinitalks;
  const api=new XMLHttpRequest();
  api.open('GET',url,true);
  api.send();
  api.onreadystatechange=function(){
    if(this.status==200 && this.readyState==4){

      let datos=JSON.parse(this.responseText);
      let resultado=document.querySelector('#resultados');
      resultado.innerHTML='';

      for(let item of datos.miniTalks){
        resultado.innerHTML +=`
        <div class="col">
          <div class="container">
            <div class="tarjeta fondotarjeta" style="background-image: url(`+ip+`/openInfo/img/${item.banner});">
              <div class="contentDelantera">
                <p class="text-center tarjetaTitulo">${item.nombre}</p>
                <p class="tarjetaSubtitulo">Expositor: ${item.expositor.nombre}</p>
                <p class="tarjetaSubtitulo">Correo: ${item.expositor.correo}</p>
              </div>
              <img src="`+ip+`/openInfo/img/${item.foto}" alt="">
              <div class="content">
                <p class="tarjetaTituloAtras">${item.nombre}</p>
                <p class="tarjetaNivel">Grado Academico: ${item.gradoAcademico}</p>
                <a href="#modExpositor${item.id}" data-toggle="modal" onclick="contarvista('${item.id}', '${item.nombre}','${item.linkOficial}');">Ver Video</a>
              </div>
            </div>
          </div>
        </div>
        <div class="modal fade" id="modExpositor${item.id}" data-backdrop="static" data-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
          <div class="modal-dialog modal-lg">
            <div class="modal-content">
              <div class="modal-header">
                <h5 class="modal-title" id="staticBackdropLabel">${item.nombre}</h5>
                <!--<button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span>-->
                </button>
              </div>
              <div class="modal-body">
                <div class="embed-responsive embed-responsive-16by9">
                  <div id="myDIV${item.id}" class="alert alert-info">EL VIDEO SE REPRODUCIRA EL 3 DE DICIEMBRE 2020</div>
                  <iframe id="myFrame" class="myFrame" width="560" height="315" src="" frameborder="0" allow="accelerometer;  clipboard-write; encrypted-media; gyroscope; picture-in-picture" ></iframe>
                </div>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="pausar();">Salir</button>
              </div>
            </div>
          </div>
        </div>
        `;
      }
    }
  }
}
  buscador.addEventListener("input", () => {
    const url=ip+urlMinitalks;
    const api=new XMLHttpRequest();
    api.open('GET',url,true);
    api.send();
    //para más eficiencia crea un nuevo fragmento
    let fragment = document.createDocumentFragment();
    //recuoera el valor del input y guardalo en una variable
    let elValor = buscador.value;
    api.onreadystatechange=function(){
      if(this.status==200 && this.readyState==4){
        let datos=JSON.parse(this.responseText);
        let resultado=document.querySelector('#resultados');
        resultado.innerHTML='';
        if (elValor.length > 0) {
        for(let item of datos.miniTalks){
          var nombre = item.nombre.toUpperCase();
              if (nombre.includes(elValor.toUpperCase())) {

              resultado.innerHTML +=`
              <div class="col">
              <div class="container">
                <div class="tarjeta fondotarjeta" style="background-image: url(`+ip+`/openInfo/img/${item.banner});">
                  <div class="contentDelantera">
                    <p class="text-center tarjetaTitulo">${item.nombre}</p>
                    <p class="tarjetaSubtitulo">Expositor: ${item.expositor.nombre}</p>
                    <p class="tarjetaSubtitulo">Correo: ${item.expositor.correo}</p>
                  </div>
                  <img src="`+ip+`/openInfo/img/${item.foto}" alt="">
                  <div class="content">
                    <p class="tarjetaTituloAtras">${item.nombre}</p>
                    <p class="tarjetaNivel">Grado Academico: ${item.gradoAcademico}</p>
                    <a href="#modExpositor${item.id}" data-toggle="modal" onclick="contarvista('${item.id}', '${item.nombre}','${item.linkOficial}');">Ver Video</a>
                  </div>
                </div>
              </div>
            </div>
            <div class="modal fade" id="modExpositor${item.id}" data-backdrop="static" data-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
              <div class="modal-dialog modal-lg">
                <div class="modal-content">
                  <div class="modal-header">
                    <h5 class="modal-title" id="staticBackdropLabel">${item.nombre}</h5>
                    <!--<button type="button" class="close" data-dismiss="modal" aria-label="Close">
                      <span aria-hidden="true">&times;</span>-->
                    </button>
                  </div>
                  <div class="modal-body">
                    <div class="embed-responsive embed-responsive-16by9">
                      <div id="myDIV${item.id}" class="alert alert-info">EL VIDEO SE REPRODUCIRA EL 3 DE DICIEMBRE 2020</div>
                      <iframe id="myFrame" class="myFrame" width="560" height="315" src="" frameborder="0" allow="accelerometer;  clipboard-write; encrypted-media; gyroscope; picture-in-picture" ></iframe>
                    </div>
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="pausar();">Salir</button>
                  </div>
                </div>
              </div>
            </div>
                `;
              }
            resultados.appendChild(fragment);
       }
     }else{
       obtenerDatos();
     }
     }
    }
  });


function contarvista(id,nombre,video){

  console.log(id);
  console.log(nombre);
  var idtalk = id;
  var nombretalk = nombre;
  var tipotalk = 'talk';

  var hoy, otrodia, texto;
  hoy = new Date();
  otrodia = new Date();
  // estamos asignando la fecha de 3 de diciembre de 2020 a la varable otro dia el 11 significa diciembre en javascript
  otrodia.setFullYear(2020, 11, 03);
  hoy.setHours(0,0,0,0);
  otrodia.setHours(0,0,0,0);
  console.log(otrodia);
  console.log(hoy);
  var x = document.getElementById("myDIV"+id);

  if (otrodia > hoy) {
    x.style.display = "block";
    $('.myFrame').removeAttr('src');
  } else {
      x.style.display = "none";
      $('.myFrame').attr('src', video);
  }

  $.ajax({
    url: ip+urlView,
    type: "Post",
    data: JSON.stringify({
     id: idtalk,
     nombre: nombretalk,
     tipo: tipotalk,
    }),
    contentType: 'application/json; charset=utf-8',
   success: function (data) {
   },
   failure: function (data)
   {
    alert(data.responseText);
   },
   error: function (data)
   {
    alert(data.responseText);
   }
  });
}

function pausar(){
    console.log("Pausa del video");
  $('.myFrame').removeAttr('src');
}
