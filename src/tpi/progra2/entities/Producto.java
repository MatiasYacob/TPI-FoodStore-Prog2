package tpi.progra2.entities;

public class Producto extends Base {

    // #========== ATRIBUTOS ==========#
    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;
    private String imagen;
    private boolean disponible;
    private Categoria categoria;

    // #========== CONSTRUCTORES ==========#
    public Producto() {
        super();
    }

    public Producto(Long id, String nombre, double precio, String descripcion, int stock, String imagen, boolean disponible) {
        super(id);
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.stock = stock;
        this.imagen = imagen;
        this.disponible = disponible;
    }

    public Producto(Long id, String nombre, String descripcion, double precio, int stock, String imagen, boolean disponible, Categoria categoria) {
        super(id);
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.imagen = imagen;
        this.disponible = disponible;
        this.categoria = categoria;
    }

    // #========== GETTERS Y SETTERS ==========#
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public boolean isDisponibilidad() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponible = disponibilidad;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    // #========== METODOS ==========#
    public void descontarStock(int cantidad) {
        this.stock -= cantidad;
    }

    @Override
    public String toString() {
        return "Producto{"
                + "id=" + getId()
                + ", nombre='" + nombre + '\''
                + ", precio=" + precio
                + ", stock=" + stock
                + ", categoria=" + (categoria != null ? categoria.getNombre() : "Sin categoria")
                + ", disponible=" + disponible
                + '}';
    }
}
