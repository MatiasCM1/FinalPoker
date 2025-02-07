package ar.edu.unlu.poker.rmi;

import java.util.concurrent.CountDownLatch;

public class Test {
	public static void main(String[] args) {
		// Latch para sincronización: asegura que los clientes esperen a que el servidor
		// esté listo
		CountDownLatch servidorIniciado = new CountDownLatch(1);
		CountDownLatch primerClienteIniciado = new CountDownLatch(1);

		// Inicia el servidor en un hilo separado
		Thread servidorThread = new Thread(() -> {
			try {
				AppServidor.main(new String[] {});
				servidorIniciado.countDown(); // Notifica que el servidor está listo
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		// Inicia el primer cliente en un hilo separado
		Thread cliente1Thread = new Thread(() -> {
			try {
				servidorIniciado.await(); // Espera a que el servidor esté listo
				AppCliente.main(new String[] {});
				primerClienteIniciado.countDown(); // Notifica que el primer cliente está listo
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		// Inicia el segundo cliente en otro hilo separado
		Thread cliente2Thread = new Thread(() -> {
			try {
				primerClienteIniciado.await(); // Espera a que el primer cliente esté listo
				AppCliente.main(new String[] {});
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		// Arranca los hilos
		servidorThread.start();
		cliente1Thread.start();
		cliente2Thread.start();
	}
}
