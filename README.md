# Manual de Instrucciones del Proyecto de DBP

Al iniciar la aplicación, la pantalla por defecto es la de ingresar un nuevo evento.
En el cual se deben llenar los campos siguientes: 
- Evento (Se refiere al nombre del evento en cuestión)
- Fecha en la que se realizará el evento.
- Descripción breve de lo que consiste el evento.

Hay un dato opcional: Recordatorio
Se puede establecer el hecho de que se emita una notificación en el celular en la hora que se indica
(haciendo uso del TimePicker).

En la pantalla principal hay dos opciones: 
- Guardar (Almacena en la Base de Datos el evento que se registró).
- Mostrar (Muestra en una nueva activity la lista de los eventos y sus datos correspondientes).

Para poder Eliminar o Modificar un evento en específico, solo es necesario presionar en la pantalla el evento que se quiera usar.
Al hacerlo, la aplicación le mostrará otra activity opciones para poder sobreescribir los datos ya antes registrados.
Además también le mostrará un dos botones :
- Modificar: Para ejecutar la consulta de UPDATE en la Base de Datos.
- Eliminar: Par poder ejecutar la consulta de DELETE en la Base de Datos.
Ambos respecto al elemento que se seleccionó.
