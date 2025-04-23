package Controller;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.SwingUtilities;

public class RestauranteConexionManager {

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 5002;
    
    private volatile boolean tokenConfirmed = false;
    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    
    // Listener que notificará la llegada de nuevas órdenes
    private OrdenListener ordenListener;
    
    public void setOrdenListener(OrdenListener listener) {
        this.ordenListener = listener;
    }
    
    public RestauranteConexionManager() throws IOException {
        System.out.println("[RCM] Conectándose al servidor en " + SERVER_HOST + ":" + SERVER_PORT + "...");
        socket = new Socket(SERVER_HOST, SERVER_PORT);
        System.out.println("[RCM] Socket conectado desde: " + socket.getInetAddress());
        
        out = new ObjectOutputStream(socket.getOutputStream());
        System.out.println("[RCM] ObjectOutputStream creado.");
        
        in = new ObjectInputStream(socket.getInputStream());
        System.out.println("[RCM] ObjectInputStream creado.");
        
        startTokenSender();
        startListener();
    }
    
    private void startTokenSender() {
        System.out.println("[RCM] Iniciando envío periódico del token 'RESTAURANTE' cada 2 segundos.");
        scheduler.scheduleAtFixedRate(() -> {
            if (!tokenConfirmed) {
                try {
                    System.out.println("[RCM] Enviando token 'RESTAURANTE'.");
                    sendMessage("RESTAURANTE");
                } catch(IOException e) {
                    System.err.println("[RCM] Error al enviar el token: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                System.out.println("[RCM] Token confirmado. Deteniendo envío periódico.");
                scheduler.shutdown();
            }
        }, 0, 2, TimeUnit.SECONDS);
    }
    
    private void startListener() {
        System.out.println("[RCM] Iniciando hilo de escucha...");
        new Thread(() -> {
            try {
                // Fase 1: Espera de confirmación
                while (!tokenConfirmed) {
                    System.out.println("[RCM] Esperando confirmación del token...");
                    Object mensaje = in.readObject();
                    System.out.println("[RCM] Mensaje recibido (previo a confirmación): " + mensaje);
                    if (mensaje instanceof String) {
                        String msg = (String) mensaje;
                        if (msg.equals("CONEXION RESTAURANTE CONFIRMADA")) {
                            tokenConfirmed = true;
                            System.out.println("[RCM] Confirmación recibida: " + msg);
                        } else {
                            System.out.println("[RCM] Mensaje recibido que no es confirmación: " + msg);
                        }
                    } else {
                        System.out.println("[RCM] Se recibió un objeto de tipo " + mensaje.getClass().getName());
                    }
                }
                // Fase 2: Bucle permanente para la recepción de órdenes
                System.out.println("[RCM] Iniciando escucha de órdenes...");
                while (true) {
                    Object mensaje = in.readObject();
                    System.out.println("[RCM] Mensaje recibido del servidor: " + mensaje);
                    if (mensaje instanceof Orden) {
                        Orden orden = (Orden) mensaje;
                        System.out.println("[RCM] Orden recibida: " + orden);
                        if (ordenListener != null) {
                            // Asegurarse de actualizar la interfaz en el EDT:
                            SwingUtilities.invokeLater(() -> ordenListener.onOrdenRecibida(orden));
                        }
                    } else {
                        System.out.println("[RCM] Objeto desconocido recibido: " + mensaje);
                    }
                }
            } catch(IOException | ClassNotFoundException e) {
                System.err.println("[RCM] Error en el listener: " + e.getMessage());
                e.printStackTrace();
            }
        }).start();
    }
    
    public synchronized void sendMessage(Object msg) throws IOException {
        System.out.println("[RCM] Enviando mensaje: " + msg);
        out.writeObject(msg);
        out.flush();
    }
    
    public void close() {
        try {
            if(out != null) out.close();
            if(in != null) in.close();
            if(socket != null) socket.close();
            scheduler.shutdownNow();
            System.out.println("[RCM] Conexión cerrada.");
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
