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
        
