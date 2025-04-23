package Controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RestauranteConexion implements Runnable {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public RestauranteConexion(Socket socket, ObjectInputStream in) {
        this.socket = socket;
        this.in = in;
        try {
            this.out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("Conexión de RESTAURANTE establecida desde: " + socket.getInetAddress());
        try {
            // Ejemplo: leer un mensaje del restaurante.
            Object mensaje = in.readObject();
            System.out.println("Mensaje recibido del restaurante:\n" + mensaje);
            // Aquí podrías enviar confirmaciones o almacenar la conexión para futuras comunicaciones.
        } catch (Exception e) {
            System.err.println("Error en RestauranteConexion:");
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) { e.printStackTrace(); }
        }
    }
}
