package tpi.progra2.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import tpi.progra2.enums.Estado;
import tpi.progra2.enums.FormaPago;
import tpi.progra2.exceptions.NegocioException;
import tpi.progra2.interfaces.Calculable;

public class Pedido extends Base implements Calculable {

    // #========== ATRIBUTOS ==========#
    private LocalDate fecha;
    private Estado estado;
    private double total;
    private FormaPago formaPago;
    private Usuario usuario;
    private List<DetallePedido> detalles;

    // #========== CONSTRUCTORES ==========#
    public Pedido() {
        super();
        this.fecha = LocalDate.now();
        this.estado = Estado.PENDIENTE;
        this.detalles = new ArrayList<>();
    }

    public Pedido(Long id, Usuario usuario, FormaPago formaPago) {
        super(id);
        validarUsuario(usuario);
        this.fecha = LocalDate.now();
        this.estado = Estado.PENDIENTE;
        this.total = 0;
        this.formaPago = formaPago;
        this.usuario = usuario;
        this.detalles = new ArrayList<>();
    }

    // #========== GETTERS Y SETTERS ==========#
    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        if (estado == null) {
            throw new NegocioException("El estado del pedido no puede ser nulo.");
        }
        this.estado = estado;
    }

    public double getTotal() {
        return total;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        if (formaPago == null) {
            throw new NegocioException("La forma de pago no puede ser nula.");
        }
        this.formaPago = formaPago;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        validarUsuario(usuario);
        this.usuario = usuario;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    // #========== METODOS ==========#
    public void addDetallePedido(int cantidad, Producto producto) {
        validarProductoParaPedido(producto, cantidad);
        DetallePedido detalleExistente = findDetallePedidoByProducto(producto);

        if (detalleExistente != null) {
            detalleExistente.setCantidad(detalleExistente.getCantidad() + cantidad);
        } else {
            Long detalleId = (long) detalles.size() + 1;
            detalles.add(new DetallePedido(detalleId, cantidad, producto));
        }

        producto.descontarStock(cantidad);
        calcularTotal();
    }

    public DetallePedido findDetallePedidoByProducto(Producto producto) {
        if (producto == null) {
            return null;
        }

        for (DetallePedido detalle : detalles) {
            if (!detalle.isEliminado()
                    && detalle.getProducto() != null
                    && detalle.getProducto().getId() != null
                    && detalle.getProducto().getId().equals(producto.getId())) {
                return detalle;
            }
        }

        return null;
    }

    public boolean deleteDetallePedidoByProducto(Producto producto) {
        DetallePedido detalle = findDetallePedidoByProducto(producto);

        if (detalle == null) {
            return false;
        }

        detalle.setEliminado(true);
        calcularTotal();
        return true;
    }

    public boolean tieneDetallesActivos() {
        for (DetallePedido detalle : detalles) {
            if (!detalle.isEliminado()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public double calcularTotal() {
        double suma = 0;

        for (DetallePedido detalle : detalles) {
            if (!detalle.isEliminado()) {
                suma += detalle.calcularSubtotal();
            }
        }

        this.total = suma;
        return total;
    }

    private void validarUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new NegocioException("No se puede crear un pedido sin usuario.");
        }
        if (usuario.isEliminado()) {
            throw new NegocioException("No se puede crear un pedido con un usuario eliminado.");
        }
    }

    private void validarProductoParaPedido(Producto producto, int cantidad) {
        if (cantidad <= 0) {
            throw new NegocioException("La cantidad debe ser mayor a cero.");
        }
        if (producto == null) {
            throw new NegocioException("El producto no puede ser nulo.");
        }
        if (producto.isEliminado()) {
            throw new NegocioException("No se puede agregar un producto eliminado.");
        }
        if (!producto.isDisponible()) {
            throw new NegocioException("No se puede agregar un producto no disponible.");
        }
        if (producto.getStock() < cantidad) {
            throw new NegocioException("Stock insuficiente para el producto: " + producto.getNombre());
        }
    }

    @Override
    public String toString() {
        return "Pedido{"
                + "id=" + getId()
                + ", usuario=" + usuario.getNombre() + " " + usuario.getApellido()
                + ", fecha=" + fecha
                + ", estado=" + estado
                + ", formaPago=" + formaPago
                + ", total=" + total
                + '}';
    }
}
