package tpi.progra2.services;

import java.util.ArrayList;
import java.util.List;
import tpi.progra2.entities.Categoria;
import tpi.progra2.exceptions.DatoInvalidoException;
import tpi.progra2.exceptions.EntidadDuplicadaException;
import tpi.progra2.exceptions.EntidadNoEncontradaException;

public class CategoriaService {

    // #========== ATRIBUTOS ==========#
    private final List<Categoria> categorias;
    private long proximoId;

    // #========== CONSTRUCTORES ==========#
    public CategoriaService() {
        this.categorias = new ArrayList<>();
        this.proximoId = 1;
    }

    // #========== METODOS ==========#
    public List<Categoria> listar() {
        List<Categoria> categoriasActivas = new ArrayList<>();

        for (Categoria categoria : categorias) {
            if (!categoria.isEliminado()) {
                categoriasActivas.add(categoria);
            }
        }

        return categoriasActivas;
    }

    public Categoria crear(String nombre, String descripcion) {
        validarTextoNoVacio(nombre, "El nombre de la categoria no puede estar vacio.");
        validarTextoNoVacio(descripcion, "La descripcion de la categoria no puede estar vacia.");
        validarNombreUnico(nombre, null);

        Categoria categoria = new Categoria(proximoId, nombre.trim(), descripcion.trim());
        categorias.add(categoria);
        proximoId++;

        return categoria;
    }

    public Categoria buscarPorId(Long id) {
        validarId(id);

        for (Categoria categoria : categorias) {
            if (categoria.getId().equals(id) && !categoria.isEliminado()) {
                return categoria;
            }
        }

        throw new EntidadNoEncontradaException("No se encontro una categoria activa con el ID indicado.");
    }

    public Categoria editar(Long id, String nuevoNombre, String nuevaDescripcion) {
        Categoria categoria = buscarPorId(id);

        boolean cambiaNombre = nuevoNombre != null && !nuevoNombre.trim().isEmpty();
        boolean cambiaDescripcion = nuevaDescripcion != null && !nuevaDescripcion.trim().isEmpty();

        if (!cambiaNombre && !cambiaDescripcion) {
            throw new DatoInvalidoException("Debe ingresar al menos un dato para modificar.");
        }

        if (cambiaNombre) {
            String nombreLimpio = nuevoNombre.trim();
            validarNombreUnico(nombreLimpio, id);
            categoria.setNombre(nombreLimpio);
        }

        if (cambiaDescripcion) {
            categoria.setDescripcion(nuevaDescripcion.trim());
        }

        return categoria;
    }

    public void eliminar(Long id) {
        Categoria categoria = buscarPorId(id);
        categoria.eliminarLogicamente();
    }

    private void validarTextoNoVacio(String texto, String mensaje) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new DatoInvalidoException(mensaje);
        }
    }

    private void validarNombreUnico(String nombre, Long idActual) {
        String nombreNormalizado = nombre.trim();

        for (Categoria categoria : categorias) {
            boolean mismoNombre = categoria.getNombre().trim().equalsIgnoreCase(nombreNormalizado);
            boolean esOtraCategoria = idActual == null || !categoria.getId().equals(idActual);

            if (!categoria.isEliminado() && mismoNombre && esOtraCategoria) {
                throw new EntidadDuplicadaException("Ya existe una categoria activa con ese nombre.");
            }
        }
    }

    private void validarId(Long id) {
        if (id == null || id <= 0) {
            throw new EntidadNoEncontradaException("El ID ingresado no es valido.");
        }
    }
}
