
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




'use strict'
var dataSet=[]
  const url =ip+urlMinitalks;
  fetch(url)
  .then(res=>res.json())
  .then(data=>{
    for(var i in data.miniTalks){
      var objeto =[
        `<a href="#modExpositor${data.miniTalks[i].id}" data-toggle="modal" onclick="contarvista('${data.miniTalks[i].id}', '${data.miniTalks[i].nombre}','${data.miniTalks[i].linkOficial}');">Ver</a>
        <div class="modal fade" id="modExpositor${data.miniTalks[i].id}" data-backdrop="static" data-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
          <div class="modal-dialog modal-lg">
            <div class="modal-content">
              <div class="modal-header">
                <h5 class="modal-title" id="staticBackdropLabel">${data.miniTalks[i].nombre}</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span>
                </button>
              </div>
              <div class="modal-body">
                <div class="embed-responsive embed-responsive-16by9">
                <iframe id="myframe" class="embed-responsive-item myFrame" src="${data.miniTalks[i].linkOficial}" allowfullscreen></iframe>
                <div id="myDIV${data.miniTalks[i].id}" class="alert alert-info">EL VIDEO SE REPRODUCIRA EL 3 DE DICIEMBRE 2020</div>
                </div>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="pausar();">Salir</button>
              </div>
            </div>
          </div>
        </div>`,
        data.miniTalks[i].expositor.nombre,
        data.miniTalks[i].expositor.correo,
        data.miniTalks[i].gradoAcademico,
        data.miniTalks[i].nombre,

        ]
        dataSet.push(objeto);
    }
    $(document).ready(function() {
      $('#example').DataTable( {
        language: {
          "decimal": "",
          "emptyTable": "No hay información",
          "info": "Mostrando _START_ a _END_ de _TOTAL_ Entradas",
          "infoEmpty": "Mostrando 0 to 0 of 0 Entradas",
          "infoFiltered": "(Filtrado de _MAX_ total entradas)",
          "infoPostFix": "",
          "thousands": ",",
          "lengthMenu": "Mostrar _MENU_ Entradas",
          "loadingRecords": "Cargando...",
          "processing": "Procesando...",
          "search": "Buscar:",
          "zeroRecords": "Sin resultados encontrados",
          "paginate": {
              "first": "Primero",
              "last": "Ultimo",
              "next": "Siguiente",
              "previous": "Anterior"
          }
      },
          data: dataSet,
          columns: [
            { title: "accion" },
              { title: "Expositor" },
              { title: "Correo" },
              { title: "Grado Academico" },
              { title: "MiniTalks" },

          ]
      } );
  } );

  }).catch(err=>console.log(err))
