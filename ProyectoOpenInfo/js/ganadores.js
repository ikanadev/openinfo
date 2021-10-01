obtenerDatos();

function obtenerDatos(){

  lugar1=1;
  lugar2=1;
  lugar3=1;
  let url=ip+urlRanking;
  const api=new XMLHttpRequest();
  api.open('GET',url,true);
  api.send();
  api.onreadystatechange=function(){
    if(this.status==200 && this.readyState==4){

      let datos=JSON.parse(this.responseText);

      let resultado2=document.querySelector('#table2');
          resultado2.innerHTML=`<thead>
                <tr>
                  <th scope="col">Lugar</th>
                  <th scope="col">MiniTalks</th>
                  <th scope="col">Acción</th>

                </tr>
              </thead>`;
      for(let item of datos.talks_mas_vistos){
        if(lugar2==1){
          resultado2.innerHTML +=`
        <tr>
          <th scope="row">`+lugar2+`&nbsp;&nbsp;&nbsp;&nbsp<i class="fa fa-trophy" aria-hidden="true" style="color:#efb810;font-size:18px "></i></th>
          <td>${item.nombre}</td>
          <td> <a href="#masVistos${item.id}" data-toggle="modal" onclick="vervideo('${item.linkOficial}');">Ver</a></td>

        </tr>
        <div class="modal fade" id="masVistos${item.id}" data-backdrop="static" data-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
          <div class="modal-dialog modal-lg">
            <div class="modal-content">
              <div class="modal-header">
                <h5 class="modal-title" id="staticBackdropLabel">Exposición : ${item.nombre}</h5>
                <!--<button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span>
                </button>-->
              </div>
              <div class="modal-body">
                <div class="embed-responsive embed-responsive-16by9">
                  <iframe id="myframe" class="myFrame" width="560" height="315" src="" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
                </div>
              </div>
              <div class="modal-footer">

                <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="pausar()">Salir</button>
              </div>
            </div>
          </div>
        </div>
        `;
        }else{
          if(lugar2==2){
            resultado2.innerHTML +=`
              <tr>
                <th scope="row">`+lugar2+`&nbsp;&nbsp;&nbsp;<i class="fa fa-trophy" aria-hidden="true" style="color:#485767;font-size:16px"></i></th>
                <td>${item.nombre}</td>
                <td> <a href="#masVistos${item.id}" data-toggle="modal" onclick="vervideo('${item.linkOficial}');">Ver</a></td>

              </tr>
              <div class="modal fade" id="masVistos${item.id}" data-backdrop="static" data-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                  <div class="modal-content">
                    <div class="modal-header">
                      <h5 class="modal-title" id="staticBackdropLabel">Exposición : ${item.nombre}</h5>
                      <!--<button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                      </button>-->
                    </div>
                    <div class="modal-body">
                      <div class="embed-responsive embed-responsive-16by9">
                        <iframe id="myframe" class="myFrame" width="560" height="315" src="" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
                      </div>
                    </div>
                    <div class="modal-footer">

                      <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="pausar()">Salir</button>
                    </div>
                  </div>
                </div>
              </div>
              `;
          }else{
            if(lugar2==3){
              resultado2.innerHTML +=`
              <tr>
                <th scope="row">`+lugar2+`&nbsp;&nbsp;&nbsp;&nbsp;<i class="fa fa-trophy" aria-hidden="true" style="color:#CD7F32;font-size:16px"></i></th>
                <td>${item.nombre}</td>
                <td> <a href="#masVistos${item.id}" data-toggle="modal" onclick="vervideo('${item.linkOficial}');">Ver</a></td>

              </tr>
              <div class="modal fade" id="masVistos${item.id}" data-backdrop="static" data-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                  <div class="modal-content">
                    <div class="modal-header">
                      <h5 class="modal-title" id="staticBackdropLabel">Exposición : ${item.nombre}</h5>
                      <!--<button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                      </button>-->
                    </div>
                    <div class="modal-body">
                      <div class="embed-responsive embed-responsive-16by9">
                        <iframe id="myframe" class="myFrame" width="560" height="315" src="" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
                      </div>
                    </div>
                    <div class="modal-footer">

                      <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="pausar()">Salir</button>
                    </div>
                  </div>
                </div>
              </div>
              `;
            }else{
              resultado2.innerHTML +=`
                <tr>
                  <th scope="row">`+lugar2+`<img src="" alt=""></th>
                  <td>${item.nombre}</td>
                  <td> <a href="#masVistos${item.id}" data-toggle="modal" onclick="vervideo('${item.linkOficial}');">Ver</a></td>

                </tr>
                <div class="modal fade" id="masVistos${item.id}" data-backdrop="static" data-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                  <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                      <div class="modal-header">
                        <h5 class="modal-title" id="staticBackdropLabel">Exposición : ${item.nombre}</h5>
                        <!--<button type="button" class="close" data-dismiss="modal" aria-label="Close">
                          <span aria-hidden="true">&times;</span>
                        </button>-->
                      </div>
                      <div class="modal-body">
                        <div class="embed-responsive embed-responsive-16by9">
                          <iframe id="myframe" class="myFrame" width="560" height="315" src="" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
                        </div>
                      </div>
                      <div class="modal-footer">

                        <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="pausar()">Salir</button>
                      </div>
                    </div>
                  </div>
                </div>
                `;
            }
          }
        }

        lugar2++;
      }

      let resultado3=document.querySelector('#table3');
      resultado3.innerHTML=`<thead>
            <tr>
              <th scope="col">Lugar</th>
              <th scope="col">Proyecto</th>
              <th scope="col">Acción</th>

            </tr>
          </thead>`;
      for(let item of datos.mas_vistos_feria){
        if(lugar3==1){
          resultado3.innerHTML +=`
          <tr>
            <th scope="row">`+lugar3+`&nbsp;&nbsp;&nbsp;&nbsp;<i class="fa fa-trophy" aria-hidden="true" style="color:#efb810;font-size:18px "></i></th>
            <td>${item.nombre}</td>
            <td><a href="#masVistosFeria${item.idProyecto}" data-toggle="modal" onclick="vervideo('${item.linkOficial}');">Ver</a></td>

          </tr>
          <div class="modal fade" id="masVistosFeria${item.idProyecto}" data-backdrop="static" data-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg">
              <div class="modal-content">
                <div class="modal-header">
                  <h5 class="modal-title" id="staticBackdropLabel">Exposición : ${item.nombre}</h5>
                  <!--<button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                  </button>-->
                </div>
                <div class="modal-body">
                  <div class="embed-responsive embed-responsive-16by9">
                    <iframe id="myframe" class="myFrame" width="560" height="315" src="${item.linkOficial}" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
                  </div>
                </div>
                <div class="modal-footer">

                  <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="pausar()">Salir</button>
                </div>
              </div>
            </div>
          </div>
          `;
        }else{
          if(lugar3==2){
            resultado3.innerHTML +=`
            <tr>
              <th scope="row">`+lugar3+`&nbsp;&nbsp;&nbsp;&nbsp;<i class="fa fa-trophy" aria-hidden="true" style="color:#485767;font-size:16px"></i></th>
              <td>${item.nombre}</td>
              <td><a href="#masVistosFeria${item.idProyecto}" data-toggle="modal" onclick="vervideo('${item.linkOficial}');">Ver</a></td>

            </tr>
            <div class="modal fade" id="masVistosFeria${item.idProyecto}" data-backdrop="static" data-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
              <div class="modal-dialog modal-lg">
                <div class="modal-content">
                  <div class="modal-header">
                    <h5 class="modal-title" id="staticBackdropLabel">Exposición : ${item.nombre}</h5>
                    <!--<button type="button" class="close" data-dismiss="modal" aria-label="Close">
                      <span aria-hidden="true">&times;</span>
                    </button>-->
                  </div>
                  <div class="modal-body">
                    <div class="embed-responsive embed-responsive-16by9">
                      <iframe id="myframe" class="myFrame" width="560" height="315" src="${item.linkOficial}" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
                    </div>
                  </div>
                  <div class="modal-footer">

                    <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="pausar()">Salir</button>
                  </div>
                </div>
              </div>
            </div>
            `;
          }else{
            if(lugar3==3){
              resultado3.innerHTML +=`
              <tr>
                <th scope="row">`+lugar3+`&nbsp;&nbsp;&nbsp;&nbsp;<i class="fa fa-trophy" aria-hidden="true" style="color:#CD7F32;font-size:16px"></i></th>
                <td>${item.nombre}</td>
                <td><a href="#masVistosFeria${item.idProyecto}" data-toggle="modal" onclick="vervideo('${item.linkOficial}');">Ver</a></td>

              </tr>
              <div class="modal fade" id="masVistosFeria${item.idProyecto}" data-backdrop="static" data-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                  <div class="modal-content">
                    <div class="modal-header">
                      <h5 class="modal-title" id="staticBackdropLabel">Exposición : ${item.nombre}</h5>
                      <!--<button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                      </button>-->
                    </div>
                    <div class="modal-body">
                      <div class="embed-responsive embed-responsive-16by9">
                        <iframe id="myframe" class="myFrame" width="560" height="315" src="${item.linkOficial}" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
                      </div>
                    </div>
                    <div class="modal-footer">

                      <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="pausar()">Salir</button>
                    </div>
                  </div>
                </div>
              </div>
              `;
            }else{
              resultado3.innerHTML +=`
              <tr>
                <th scope="row">`+lugar3+`<img src="" alt=""></th>
                <td>${item.nombre}</td>
                <td><a href="#masVistosFeria${item.idProyecto}" data-toggle="modal" onclick="vervideo('${item.linkOficial}');">Ver</a></td>

              </tr>
              <div class="modal fade" id="masVistosFeria${item.idProyecto}" data-backdrop="static" data-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                  <div class="modal-content">
                    <div class="modal-header">
                      <h5 class="modal-title" id="staticBackdropLabel">Exposición : ${item.nombre}</h5>
                      <!--<button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                      </button>-->
                    </div>
                    <div class="modal-body">
                      <div class="embed-responsive embed-responsive-16by9">
                        <iframe id="myframe" class="myFrame" width="560" height="315" src="${item.linkOficial}" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
                      </div>
                    </div>
                    <div class="modal-footer">

                      <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="pausar()">Salir</button>
                    </div>
                  </div>
                </div>
              </div>
              `;
            }
          }
        }

        lugar3++;
      }
    }
  }
}
function vervideo(video){
    console.log('link video : '+video);
  $('.myFrame').attr('src',video);
}
function pausar(){
  console.log("Pausa del video");
  $('.myFrame').removeAttr('src');
}
