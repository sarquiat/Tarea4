async function get() {
    var json;
    let response = await fetch("http://localhost:9090/TareaErick/list", {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        }});
    if (response.ok) {
        json = await response.json();
        console.log(json);
        //crearTabla(json);
    } else {
        alert("HTTP-Error: " + response.status);
        //tabla.innerHTML = "<p>No sirvio unu</p>";
    }
    crearTabla(json);
}

function crearTabla(json) {
    var tabla = document.getElementById("cuerpo");
    var t = "<table border='1' align='center'>";
    t += "<tr>";
    t += "<th>ID</th>";
    t += "<th>Titulo</th>";
    t += "<th>Autor</th>";
    t += "<th>Precio</th>";
    t += "<th>Actiones</th>";
    t += "</tr>";
    for (i in json)
    {
        t += "<tr>";
        t += "<td>" + json[i].bookId + "</td>";
        t += "<td>" + json[i].title + "</td>";
        t += "<td>" + json[i].author + "</td>";
        t += "<td>" + json[i].price + "</td>";
        t += "<td><button type='button' class='btn btn-default' onclick='borrarLibro(this)' id='" + json[i].bookId + "'>Eliminar Libro</button>"
        t += "</tr>";
    }

    t += "</table>";
    tabla.innerHTML = t;
}

async function registrar() {
    let titulo = document.getElementById("titulo").value;
    let autor = document.getElementById("autor").value;
    let precio = document.getElementById("precio").value;
    if (titulo == "" || autor == "" || precio == null) {
        alert("Todos los espacios deben llenarse");
    } else {
        var request = newLibro(titulo, autor, precio);
    }
}

async function newLibro(t, a, p) {
    let response = await fetch("http://localhost:9090/TareaErick/book", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify({
            bookId: 20,
            title: t,
            author: a,
            price: p,
        }
        )
    });
    if (response.ok) {
        alert("Libro registrado");
    } else {
        alert("HTTP-Error: " + response.status);
        //tabla.innerHTML = "<p>No sirvio unu</p>";
    }
}

async function borrarLibro(e) {
    var id = e.id;
    let response = await fetch("http://localhost:9090/TareaErick/book", {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify({
            bookId: id
        }
        )
    });
    if (response.ok) {
        json = await response.json();
        console.log(json);
        //crearTabla(json);
    } else {
        alert("HTTP-Error: " + response.status);
        //tabla.innerHTML = "<p>No sirvio unu</p>";
    }
}