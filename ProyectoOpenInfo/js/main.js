const resizeHeader = (scroll_pos) => {
  const imgEl = document.querySelector('.logo-container > img')
  const firstContEl = document.querySelector('.header-container')
  if (scroll_pos < 100) {
    document.documentElement.style.setProperty('--text-menu-size', '0.85rem')
    firstContEl.style.height = '52px'
    firstContEl.style.paddingTop = '6px'
    firstContEl.style.paddingBottom = '6px'
    imgEl.style.height = '60px'
  } else if (scroll_pos > 140) {
    document.documentElement.style.setProperty('--text-menu-size', '0.79rem')
    firstContEl.style.height = '0'
    firstContEl.style.padding = '0'
    imgEl.style.height = '40px'
  }
}

const showOrHideArrowTop = (scrollY) => {
  const arrowEl = document.querySelector('.arrow-to-top')
  if (scrollY > 140) {
    if (arrowEl.classList.contains('show-arrow')) return
    arrowEl.classList.add('show-arrow')
  }
  if (scrollY < 100) {
    if (!arrowEl.classList.contains('show-arrow')) return
    arrowEl.classList.remove('show-arrow')
  }
}

const scrollEvents = () => {
  let last_known_scroll_position = 0;
  let ticking = false;

  window.addEventListener('scroll', (e) => {
    last_known_scroll_position = window.scrollY;

    if (!ticking) {
      window.requestAnimationFrame(function () {
        // Put here all functions which need scroll events
        if (window.innerWidth > 920) {
          resizeHeader(last_known_scroll_position);
          showOrHideArrowTop(last_known_scroll_position);
        }
        ticking = false;
      });

      ticking = true;
    }

  })
}

const handleArrowTop = () => {
  document.querySelector('.arrow-to-top').addEventListener('click', () => {
    window.scrollTo({ top: 0 })
  })
}

/**
 * Determine whether a node's text content is entirely whitespace.
 *
 * @param nod  A node implementing the |CharacterData| interface (i.e.,
 *             a |Text|, |Comment|, or |CDATASection| node
 * @return     True if all of the text content of |nod| is whitespace,
 *             otherwise false.
 */
function is_all_ws(nod) {
  // Use ECMA-262 Edition 3 String and RegExp features
  return !(/[^\t\n\r ]/.test(nod.textContent));
}


/**
 * Determine if a node should be ignored by the iterator functions.
 *
 * @param nod  An object implementing the DOM1 |Node| interface.
 * @return     true if the node is:
 *                1) A |Text| node that is all whitespace
 *                2) A |Comment| node
 *             and otherwise false.
 */

function is_ignorable(nod) {
  return (nod.nodeType == 8) || // A comment node
    ((nod.nodeType == 3) && is_all_ws(nod)); // a text node, all ws
}

function clean(node) {
  for (var n = 0; n < node.childNodes.length; n++) {
    var child = node.childNodes[n];
    if (is_ignorable(child)) {
      node.removeChild(child);
      n--;
    }
    else if (child.nodeType === 1) {
      clean(child);
    }
  }
}

const handleSearchTeacher = () => {
  const inputEl = document.querySelector('#teacher-search')
  if (!inputEl) return
  const teacherContEl = document.querySelector('#teacher-data-cont')
  clean(teacherContEl)
  const elements = Array.from(teacherContEl.childNodes)

  inputEl.addEventListener('input', () => {
    const filteredElements = elements.filter(node => {
      return node.innerText.toUpperCase().indexOf(inputEl.value.toUpperCase()) != -1
    })
    teacherContEl.innerHTML = ""
    teacherContEl.append(...filteredElements)
  })
}

window.addEventListener('DOMContentLoaded', () => {
  scrollEvents();
  handleArrowTop();
  handleSearchTeacher();
})


//codigo js para la galeria de imagenes
var num = 1;
function siguiente() {
  num++;
  if (num > 5) {
    num = 1;
  }
  var foto = document.getElementById("Galeria");
  foto.src = "./img/galeria/G_Imagen_" + num + ".jpg";
  console.log(foto);
}
function anterior() {
  num--;
  if (num < 1) {
    num = 5;
  }
  var foto = document.getElementById("Galeria");
  foto.src = "./img/galeria/G_Imagen_" + num + ".jpg";
  console.log(foto);
}


