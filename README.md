# FinalPoker

Este proyecto es el trabajo práctico final de la asignatura Programación Orientada a Objetos en la Universidad Nacional de Luján. Su objetivo es desarrollar un juego de póker multijugador en tiempo real, utilizando los patrones MVC y Observer, con persistencia mediante serialización.

## Tecnologías y patrones utilizados

- **Lenguaje:** Java (JDK)
- **Interfaz gráfica:** Java Swing
- **Control de versiones:** Git
- **Entorno de desarrollo:** Eclipse (IDE)
- **Patrones de diseño:** MVC, Observer
- **Persistencia:** Serialización
- **Librería RMI utilizada:** [RMIMVC](https://github.com/federicoradeljak/libreria-rmimvc) (provista por el equipo docente)

## Requisitos previos

Antes de ejecutar el proyecto, asegúrate de tener instalado:

- JDK 8 o superior
- Eclipse o cualquier otro IDE compatible con Java
- Git (opcional, pero recomendado)

## Diagrama de clases

A continuación, se muestra el diagrama de clases del proyecto:

![Diagrama de Clases](Diagrama.jpg)

## Uso

1. Elegir el tipo de interfaz:

   Para interfaz grafica comentar la linea IVista vista = new VistaConsolaSwing();

   ![Image](https://github.com/AlumnoProgAux/imagenes/blob/main/SeleccionInterfazGrafica.png)

     Para interfaz consola comentar la linea IVista vista = VistaGrafica.getInstance();

   ![Image](https://github.com/AlumnoProgAux/imagenes/blob/main/SeleccionInterfazConsola.png)

        
2. Ejecutar la clase AppServidor
   
     * Seleccionar dirección IP
       
       ![Image](https://github.com/AlumnoProgAux/imagenes/blob/main/SeleccionIPServidor.png)
       
     * Seleccionar puerto del servidor
       
       ![Image](https://github.com/AlumnoProgAux/imagenes/blob/main/SeleccionPuertoServidor.png)
       

3. Ejecutar la clase cliente

   * Seleccionar dirección IP

      ![Image](https://github.com/AlumnoProgAux/imagenes/blob/main/SeleccionIPCliente.png)
  
   * Seleccione el puerto para ese cliente

     ![Image](https://github.com/AlumnoProgAux/imagenes/blob/main/SeleccionPuertoCliente.png)
  
   * Seleccione la IP del servidor o presione directamente aceptar

     ![Image](https://github.com/AlumnoProgAux/imagenes/blob/main/SeleccionIPServidorCliente.png)
  
   * Seleccione el puerto donde corre el servdor

     ![Image](https://github.com/AlumnoProgAux/imagenes/blob/main/SeleccionPuertoServidorCliente.png)
  
Este proceso de ejecución debe repetirse por cada jugador que desee que participe en el juego, hasta un maximo de 7 jugadores. Como el juego se corre de manera local hay que asegurarse de que cada cliente escuche en un puerto diferente
