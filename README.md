# FinalPoker

Este proyecto es el trabajo práctico final para la asignatura Programación Orientada a Objetos de la Universidad Nacional de Luján. Su objetivo es desarrollar un juego de póker que permita una partida con múltiples usuarios en tiempo real, utilizando los patrones MVC y Observer, con persistencia por serialización.

## Tecnologías y patrones utilizados

* Java (JDK)
* Java Swing 
* Git
* Eclipse (IDE)
* Librería [RMIMVC](https://github.com/federicoradeljak/libreria-rmimvc) provista por el equipo docente
* MVC
* Observer
* Serialización

## Diagrama de clases

![Image](Diagrama.jpg)

## Uso

1. Elegir el tipo de interfaz:

   Para interfaz grafica comentar la linea IVista vista = new VistaConsolaSwing();

   ![Image](https://github.com/AlumnoProgAux/imagenes/blob/main/SeleccionInterfazGrafica.png)

     Para interfaz consola comentar la linea IVista vista = VistaGrafica.getInstance();

   ![Image](https://github.com/AlumnoProgAux/imagenes/blob/main/SeleccionInterfazConsola.png)

        
2. Ejecutar la clase AppServidor
   
     * Seleccionar dirección IP
       
       [Imagen direcccion ip]
       
     * Seleccionar puerto del servidor
       
       [Imagen puerto servidor]
       

3. Ejecutar la clase cliente

   * Seleccionar dirección IP

      [Imagen]
  
   * Seleccione el puerto para ese cliente

     [Imagen]
  
   * Seleccione la IP del servidor o presione directamente aceptar

     [imagen]
  
   * Seleccione el puerto donde corre el servdor

     [Imagen]
  
Este proceso de ejecución debe repetirse por cada jugador que desee que participe en el juego, hasta un maximo de 7 jugadores. Como el juego se corre de manera local hay que asegurarse de que cada cliente escuche en un puerto diferente
