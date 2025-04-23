package Controller;

import java.io.Serializable;

/**
 * Representa una orden en el sistema.
 */
public class Orden implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // Atributos existentes
    private String estado;
    private String metodoPago;
    private String nombreUsuario;
    private String direccionEntrega;
    private String producto;
    private double precio;  // Se usa double para conservar decimales

    // Constructor
    public Orden(String estado, String metodoPago, String nombreUsuario,
                 String direccionEntrega, String producto, double precio) {
        this.estado = estado;
        this.metodoPago = metodoPago;
        this.nombreUsuario = nombreUsuario;
        this.direccionEntrega = direccionEntrega;
        this.producto = producto;
        this.precio = precio;
        System.out.println("[Orden] Creada: " + this);
    }

    @Override
    public String toString() {
        return "Orden {\n"
                + "  Estado: " + estado + ",\n"
                + "  Método de Pago: " + metodoPago + ",\n"
                + "  Nombre de Usuario: " + nombreUsuario + ",\n"
                + "  Dirección de Entrega: " + direccionEntrega + ",\n"
                + "  Producto: " + producto + ",\n"
                + "  Precio: " + precio + "\n"
                + '}';
    }
    
    // Métodos Getters y Setters existentes
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        System.out.println("[Orden] Set estado: " + estado);
        this.estado = estado;
    }
    
    public String getMetodoPago() {
        return metodoPago;
    }
    
    public void setMetodoPago(String metodoPago) {
        System.out.println("[Orden] Set método de pago: " + metodoPago);
        this.metodoPago = metodoPago;
    }
    
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    
    public void setNombreUsuario(String nombreUsuario) {
        System.out.println("[Orden] Set nombre de usuario: " + nombreUsuario);
        this.nombreUsuario = nombreUsuario;
    }
    
    public String getDireccionEntrega() {
        return direccionEntrega;
    }
    
    public void setDireccionEntrega(String direccionEntrega) {
        System.out.println("[Orden] Set dirección de entrega: " + direccionEntrega);
        this.direccionEntrega = direccionEntrega;
    }
    
    public String getProducto() {
        return producto;
    }
    
    public void setProducto(String producto) {
        System.out.println("[Orden] Set producto: " + producto);
        this.producto = producto;
    }
    
    public double getPrecio() {
        return precio;
    }
    
    public void setPrecio(double precio) {
        System.out.println("[Orden] Set precio: " + precio);
        this.precio = precio;
    }
    
    // Métodos solicitados por GestorDePedidos
    
    /**
     * Valida que la orden tenga los datos mínimos.
     */
    public boolean esValida() {
        // Por ejemplo, puede ser válida si nombre de usuario y producto
        // no son nulos ni vacíos.
        return nombreUsuario != null && !nombreUsuario.trim().isEmpty() 
            && producto != null && !producto.trim().isEmpty();
    }
    
    /**
     * Retorna el nombre del cliente (usamos el nombre de usuario).
     */
    public String getNombreCliente() { 
        return nombreUsuario;
    }
    
    /**
     * Devuelve la información del producto como String.
     */
    public String getProductos() {
        return producto;
    }
    
    /**
     * Devuelve un identificador ficticio para el cliente.
     */
    public String getClienteId() {
        return "0"; // Valor por defecto
    }
    
    /**
     * Devuelve un identificador ficticio para la orden.
     */
    public String getId() {
        return "1"; // Valor por defecto
    }
    
    /**
     * Método opcional para obtener una descripción resumida de la orden.
     */
    public String getDescripcion() {
        return "Producto: " + producto + ", Usuario: " + nombreUsuario + ", Dirección: " + direccionEntrega;
    }
}
