package tpi.progra2.services;

import java.util.ArrayList;
import java.util.List;
import tpi.progra2.entities.Pedido;
import tpi.progra2.entities.Usuario;
import tpi.progra2.enums.Estado;
import tpi.progra2.enums.FormaPago;
import tpi.progra2.exceptions.NegocioException;

public class PedidoService {

    // #========== ATRIBUTOS ==========#
    private final List<Pedido> pedidos;
    private long proximoId;

    // #========== CONSTRUCTORES ==========#
    public PedidoService() {
        this.pedidos = new ArrayList<>();
        this.proximoId = 1;
    }

    // #========== METODOS ==========#
    public Pedido crearPedido(Usuario usuario, FormaPago formaPago) {
        Pedido pedido = new Pedido(proximoId, usuario, formaPago);
        pedidos.add(pedido);
        proximoId++;
        return pedido;
    }

    public Pedido iniciarPedido(Usuario usuario, FormaPago formaPago) {
        return new Pedido(proximoId, usuario, formaPago);
    }

    public Pedido guardarPedido(Pedido pedido) {
        if (pedido == null) {
            throw new NegocioException("No se puede guardar un pedido nulo.");
        }
        if (!pedido.tieneDetallesActivos()) {
            throw new NegocioException("El pedido debe tener al menos un detalle.");
        }

        pedidos.add(pedido);
        proximoId++;
        return pedido;
    }

    public List<Pedido> listarPedidos() {
        List<Pedido> pedidosActivos = new ArrayList<>();

        for (Pedido pedido : pedidos) {
            if (!pedido.isEliminado()) {
                pedidosActivos.add(pedido);
            }
        }

        return pedidosActivos;
    }

    public List<Pedido> listarPedidosPorUsuario(Usuario usuario) {
        if (usuario == null || usuario.getId() == null) {
            throw new NegocioException("El usuario para filtrar pedidos no es valido.");
        }

        List<Pedido> resultado = new ArrayList<>();

        for (Pedido pedido : pedidos) {
            if (!pedido.isEliminado()
                    && pedido.getUsuario() != null
                    && usuario.getId().equals(pedido.getUsuario().getId())) {
                resultado.add(pedido);
            }
        }

        return resultado;
    }

    public Pedido buscarPedidoPorId(Long id) {
        for (Pedido pedido : pedidos) {
            if (!pedido.isEliminado() && pedido.getId() != null && pedido.getId().equals(id)) {
                return pedido;
            }
        }

        throw new NegocioException("No existe un pedido activo con id: " + id);
    }

    public void actualizarEstadoYFormaPago(Long id, Estado estado, FormaPago formaPago) {
        Pedido pedido = buscarPedidoPorId(id);
        pedido.setEstado(estado);
        pedido.setFormaPago(formaPago);
    }

    public void eliminarPedido(Long id) {
        Pedido pedido = buscarPedidoPorId(id);
        pedido.setEliminado(true);
    }
}
