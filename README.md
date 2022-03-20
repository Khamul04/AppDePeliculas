# AppDePeliculas
Prueba técnica solicitada por Grupo Salinas

Faltó por implementar dos cosas:
    1) Soporte local para la pantalla detalles (la pantalla principal si cuenta con soporte local)
    2) El video
    
Para el paso 1, se implementaria de la misma manera que el soporte local para la pantalla con la caratula de las peliculas:
    1) Crear un adapter con queries para trabajar con la base de datos
    2) Crear metodos adecuados en el Model para obtener los detalles desde la web y metodos para utilizar el adaptador de arriba.
  Dependiendo si queremos que los detalles guardados sean unicamente los que el usuario ya visitó o si queremos que se descarguen
  y guarden todos los detalles, podriamos seguir loc caminos A y B respectivamente
    A) El presentador de la pantalla detalles se inicia en el metodo onResume, ejecutandose el metodo start, este sería un buen lugar
       para guardar en local el detalle recien descargado.
    B) Este caso es mas complicado, pues deberíamos descargar y guardar todos los detalles al momento en que se descargan las portadas
       y nombres de las peliculas (en el metodo start del presentador). Habría que iterar sobre la lista de peliculas obtenidas e ir
       descargando cada detalle, tanto con la la lista de populares como con la lista playnow, y guardando los datos en la base de datos.
       Como es una tarea tardada deberá hacerse en segundo plano; sin embargo, las llamadas para obtener detalles, deberan ser secuanciales
       para evitar que varios hilos modifiquen la base de datos al mismo tiempo.
       
  3) En el presenter mismo se podria colocar logica para decidir si cargar información de internet o de la base de datos, como ya se hizo en
     en la pantalla principal
     
 Video
    El la pagina cuanta con un servicio que te da información sobre los videos, en que host se ubican y un valor key que sirve
    para completar la URL, de manera similar a las imagenes. Youtube tiene una API para embeber videos en aplicaciones, por lo
    que habría que revisarla para este paso.
