package tpi.progra2.services;

import java.util.ArrayList;
import java.util.List;
import tpi.progra2.entities.Usuario;
import tpi.progra2.enums.Rol;
import tpi.progra2.exceptions.DatoInvalidoException;
import tpi.progra2.exceptions.EntidadNoEncontradaException;
import tpi.progra2.exceptions.MailDuplicadoException;

public class UsuarioService {

    // #========== ATRIBUTOS ==========#
    private final List<Usuario> usuarios;
    private long proximoId;

    // #========== CONSTRUCTORES ==========#
    public UsuarioService() {
        this.usuarios = new ArrayList<>();
        this.proximoId = 1;
    }

    // #========== METODOS ==========#
    public List<Usuario> listarUsuariosActivos() {
        List<Usuario> activos = new ArrayList<>();

        for (Usuario usuario : usuarios) {
            if (!usuario.isEliminado()) {
                activos.add(usuario);
            }
        }

        return activos;
    }

    public Usuario crearUsuario(String nombre, String apellido, String mail,
            String celular, String contrasena, Rol rol) {

        validarDatosObligatorios(nombre, apellido, mail, celular, contrasena, rol);

        String mailNormalizado = normalizarMail(mail);
        validarFormatoMail(mailNormalizado);
        validarMailUnico(mailNormalizado, null);

        Usuario usuario = new Usuario(
                proximoId,
                nombre.trim(),
                apellido.trim(),
                mailNormalizado,
                celular.trim(),
                contrasena,
                rol
        );

        usuarios.add(usuario);
        proximoId++;

        return usuario;
    }

    public Usuario buscarUsuarioActivoPorId(Long id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId().equals(id) && !usuario.isEliminado()) {
                return usuario;
            }
        }

        throw new EntidadNoEncontradaException("No existe un usuario activo con id " + id);
    }

    public Usuario editarUsuario(Long id, String nombre, String apellido,
            String mail, String celular, String contrasena, Rol rol) {

        Usuario usuario = buscarUsuarioActivoPorId(id);

        if (nombre != null && !nombre.isBlank()) {
            usuario.setNombre(nombre.trim());
        }
        if (apellido != null && !apellido.isBlank()) {
            usuario.setApellido(apellido.trim());
        }
        if (mail != null && !mail.isBlank()) {
            String mailNormalizado = normalizarMail(mail);
            validarFormatoMail(mailNormalizado);
            validarMailUnico(mailNormalizado, id);
            usuario.setMail(mailNormalizado);
        }
        if (celular != null && !celular.isBlank()) {
            usuario.setCelular(celular.trim());
        }
        if (contrasena != null && !contrasena.isBlank()) {
            usuario.setContrasena(contrasena);
        }
        if (rol != null) {
            usuario.setRol(rol);
        }

        return usuario;
    }

    public void eliminarUsuario(Long id) {
        Usuario usuario = buscarUsuarioActivoPorId(id);
        usuario.eliminar();
    }

    public boolean existeMail(String mail) {
        String mailNormalizado = normalizarMail(mail);

        for (Usuario usuario : usuarios) {
            if (usuario.getMail().equalsIgnoreCase(mailNormalizado)) {
                return true;
            }
        }

        return false;
    }

    private void validarDatosObligatorios(String nombre, String apellido, String mail,
            String celular, String contrasena, Rol rol) {

        if (nombre == null || nombre.isBlank()) {
            throw new DatoInvalidoException("El nombre no puede estar vacio.");
        }
        if (apellido == null || apellido.isBlank()) {
            throw new DatoInvalidoException("El apellido no puede estar vacio.");
        }
        if (mail == null || mail.isBlank()) {
            throw new DatoInvalidoException("El mail no puede estar vacio.");
        }
        if (celular == null || celular.isBlank()) {
            throw new DatoInvalidoException("El celular no puede estar vacio.");
        }
        if (contrasena == null || contrasena.isBlank()) {
            throw new DatoInvalidoException("La contrasena no puede estar vacia.");
        }
        if (rol == null) {
            throw new DatoInvalidoException("El rol no puede estar vacio.");
        }
    }

    private void validarFormatoMail(String mail) {
        if (!mail.contains("@") || !mail.contains(".")) {
            throw new DatoInvalidoException("El mail debe tener un formato basico valido.");
        }
    }

    private void validarMailUnico(String mail, Long idAExcluir) {
        for (Usuario usuario : usuarios) {
            boolean mismoMail = usuario.getMail().equalsIgnoreCase(mail);
            boolean mismoUsuario = idAExcluir != null && usuario.getId().equals(idAExcluir);

            if (mismoMail && !mismoUsuario) {
                throw new MailDuplicadoException("Ya existe un usuario con el mail " + mail);
            }
        }
    }

    private String normalizarMail(String mail) {
        return mail.trim().toLowerCase();
    }
}
