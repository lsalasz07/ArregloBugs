package Model;

import java.io.Serializable;

/**
 * Representa una confirmación sobre una orden, indicando si fue aceptada,
 * rechazada o enviada.
 */
public class ConfirmacionPedido implements Serializable {

    private int clienteId;
    private int restauranteId;
    private int pedidoId;
    private Estado estado;

    /**
     * Enumerado que define los posibles estados de la confirmación.
     */
    public enum Estado {
        ACEPTADA, RECHAZADA, ENVIADA
    }

    /**
     * Crea una confirmación de pedido.
     *
     * @param clienteId ID del cliente.
     * @param restauranteId ID del restaurante.
     * @param pedidoId ID de la orden.
     * @param estado El estado de confirmación.
     * @throws IllegalArgumentException si alguno de los IDs es menor o igual a
     * cero.
     */
    public ConfirmacionPedido(int clienteId, int restauranteId, int pedidoId, Estado estado) {
        if (clienteId <= 0 || restauranteId <= 0 || pedidoId <= 0) {
            throw new IllegalArgumentException("IDs deben ser mayores a cero.");
        }
        this.clienteId = clienteId;
        this.restauranteId = restauranteId;
        this.pedidoId = pedidoId;
        this.estado = estado;
    }

    // Getters
    public int getClienteId() {
        return clienteId;
    }

    public int getRestauranteId() {
        return restauranteId;
    }

    public int getPedidoId() {
        return pedidoId;
    }

    public Estado getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return String.format("ConfirmacionPedido [Cliente ID: %d, Restaurante ID: %d, Pedido ID: %d, Estado: %s]",
                clienteId, restauranteId, pedidoId, estado);
    }
}
