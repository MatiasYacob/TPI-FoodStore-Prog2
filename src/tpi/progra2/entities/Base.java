package tpi.progra2.entities;

import java.time.LocalDateTime;

public abstract class Base {

    // #========== ATRIBUTOS ==========#
    private Long id;
    private boolean eliminado;
    private LocalDateTime createdAt;

    // #========== CONSTRUCTORES ==========#
    public Base() {
        this.eliminado = false;
        this.createdAt = LocalDateTime.now();
    }

    public Base(Long id) {
        this.id = id;
        this.eliminado = false;
        this.createdAt = LocalDateTime.now();
    }

    // #========== GETTERS Y SETTERS ==========#
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // #========== METODOS ==========#
    public void eliminar() {
        this.eliminado = true;
    }

    public void eliminarLogicamente() {
        eliminar();
    }

    @Override
    public String toString() {
        return "id=" + id
                + ", eliminado=" + eliminado
                + ", createdAt=" + createdAt;
    }
}
