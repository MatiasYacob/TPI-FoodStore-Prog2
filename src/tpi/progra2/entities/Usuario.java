package tpi.progra2.entities;

import tpi.progra2.enums.Rol;

public class Usuario extends Base {

    // #========== ATRIBUTOS ==========#
    private String nombre;
    private String apellido;
    private String mail;
    private String celular;
    private String contrasena;
    private Rol rol;

    // #========== CONSTRUCTORES ==========#
    public Usuario() {
        super();
    }

    public Usuario(Long id, String nombre, String apellido, String mail, String celular, String contrasena, Rol rol) {
        super(id);
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.celular = celular;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    // #========== GETTERS Y SETTERS ==========#
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    // #========== METODOS ==========#
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    @Override
    public String toString() {
        return "ID: " + getId()
                + " | Nombre: " + nombre
                + " | Apellido: " + apellido
                + " | Mail: " + mail
                + " | Celular: " + celular
                + " | Rol: " + rol;
    }
}
