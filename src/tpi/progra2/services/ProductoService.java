package tpi.progra2.services;

import java.util.ArrayList;
import java.util.List;
import tpi.progra2.entities.Categoria;
import tpi.progra2.entities.Producto;
import tpi.progra2.exceptions.DatoInvalidoException;
import tpi.progra2.exceptions.EntidadNoEncontradaException;

public class ProductoService {

    // #========== ATRIBUTOS ==========#
    private final List<Producto> productos;
    private final CategoriaService categoriaService;
    private long proximoId;

    // #========== CONSTRUCTORES ==========#
    public ProductoService(CategoriaService categoriaService) {
        this.productos = new ArrayList<>();
        this.categoriaService = categoriaService;
        this.proximoId = 1;
    }

    // #========== METODOS ==========#
    public List<Producto> listarProductos(Long categoriaId) {
        List<Producto> resultado = new ArrayList<>();

        for (Producto producto : productos) {
            if (!producto.isEliminado()
                    && (categoriaId == null
                    || (producto.getCategoria() != null && producto.getCategoria().getId().equals(categoriaId)))) {
                resultado.add(producto);
            }
        }

        return resultado;
    }

    public Producto crearProducto(String nombre, String descripcion, double precio,
            int stock, String imagen, boolean disponible, Long categoriaId) {

        validarProducto(nombre, descripcion, precio, stock, categoriaId);
        Categoria categoria = categoriaService.buscarPorId(categoriaId);

        Producto producto = new Producto(
                proximoId,
                nombre.trim(),
                descripcion.trim(),
                precio,
                stock,
                imagen == null ? "" : imagen.trim(),
                disponible,
                categoria
        );

        productos.add(producto);
        proximoId++;
        return producto;
    }

    public Producto buscarProducto(Long id) {
        for (Producto producto : productos) {
            if (producto.getId().equals(id) && !producto.isEliminado()) {
                return producto;
            }
        }

        throw new EntidadNoEncontradaException("No existe un producto activo con ID: " + id);
    }

    public void editarProducto(Long id, Double precio, Integer stock, Long categoriaId) {
        Producto producto = buscarProducto(id);

        if (precio != null) {
            if (precio < 0) {
                throw new DatoInvalidoException("El precio no puede ser negativo.");
            }
            producto.setPrecio(precio);
        }

        if (stock != null) {
            if (stock < 0) {
                throw new DatoInvalidoException("El stock no puede ser negativo.");
            }
            producto.setStock(stock);
        }

        if (categoriaId != null) {
            Categoria categoria = categoriaService.buscarPorId(categoriaId);
            producto.setCategoria(categoria);
        }
    }

    public void eliminarProducto(Long id) {
        Producto producto = buscarProducto(id);
        producto.eliminar();
    }

    private void validarProducto(String nombre, String descripcion, double precio,
            int stock, Long categoriaId) {

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new DatoInvalidoException("El nombre no puede estar vacio.");
        }
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new DatoInvalidoException("La descripcion no puede estar vacia.");
        }
        if (precio < 0) {
            throw new DatoInvalidoException("El precio no puede ser negativo.");
        }
        if (stock < 0) {
            throw new DatoInvalidoException("El stock no puede ser negativo.");
        }
        if (categoriaId == null || categoriaId <= 0) {
            throw new DatoInvalidoException("Debe seleccionar una categoria valida.");
        }
    }
}
