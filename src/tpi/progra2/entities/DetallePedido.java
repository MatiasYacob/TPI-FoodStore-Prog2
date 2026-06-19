package tpi.progra2.entities;

import tpi.progra2.exceptions.NegocioException;

public class DetallePedido extends Base {

    // #========== ATRIBUTOS ==========#
    private int cantidad;
    private double subtotal;
    private Producto producto;

    // #========== CONSTRUCTORES ==========#
    public DetallePedido() {
        super();
    }

    public DetallePedido(Long id, int cantidad, Producto producto) {
        super(id);
        validarCantidad(cantidad);
        validarProducto(producto);
        this.cantidad = cantidad;
        this.producto = producto;
        calcularSubtotal();
    }

    // #========== GETTERS Y SETTERS ==========#
    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        validarCantidad(cantidad);
        this.cantidad = cantidad;
        calcularSubtotal();
    }

    public double getSubtotal() {
        return subtotal;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        validarProducto(producto);
        this.producto = producto;
        calcularSubtotal();
    }

    // #========== METODOS ==========#
    public double calcularSubtotal() {
        this.subtotal = cantidad * producto.getPrecio();
        return subtotal;
    }

    private void validarCantidad(int cantidad) {
        if (cantidad <= 0) {
            throw new NegocioException("La cantidad del detalle debe ser mayor a cero.");
        }
    }

    private void validarProducto(Producto producto) {
        if (producto == null) {
            throw new NegocioException("El producto del detalle no puede ser nulo.");
        }
        if (producto.isEliminado()) {
            throw new NegocioException("No se puede usar un producto eliminado.");
        }
        if (!producto.isDisponible()) {
            throw new NegocioException("El producto no esta disponible.");
        }
    }

    @Override
    public String toString() {
        return "DetallePedido{"
                + "id=" + getId()
                + ", producto=" + producto.getNombre()
                + ", cantidad=" + cantidad
                + ", subtotal=" + subtotal
                + '}';
    }
}
