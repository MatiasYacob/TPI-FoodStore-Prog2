package tpi.progra2.entities;

public class Categoria extends Base {

    // #========== ATRIBUTOS ==========#
    private String nombre;
    private String descripcion;

    // #========== CONSTRUCTORES ==========#
    public Categoria() {
        super();
    }

    public Categoria(String nombre, String descripcion) {
        super();
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Categoria(Long id, String nombre, String descripcion) {
        super(id);
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // #========== GETTERS Y SETTERS ==========#
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // #========== METODOS ==========#
    @Override
    public String toString() {
        return "ID: " + getId()
                + " | Nombre: " + nombre
                + " | Descripcion: " + descripcion;
    }
}
