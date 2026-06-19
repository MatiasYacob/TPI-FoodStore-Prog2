package tpi.progra2.menus;

import java.util.List;
import java.util.Scanner;
import tpi.progra2.entities.Categoria;
import tpi.progra2.entities.Pedido;
import tpi.progra2.entities.Producto;
import tpi.progra2.entities.Usuario;
import tpi.progra2.enums.Estado;
import tpi.progra2.enums.FormaPago;
import tpi.progra2.enums.Rol;
import tpi.progra2.exceptions.NegocioException;
import tpi.progra2.services.CategoriaService;
import tpi.progra2.services.PedidoService;
import tpi.progra2.services.ProductoService;
import tpi.progra2.services.UsuarioService;

public class MenuPrincipal {

    // #========== ATRIBUTOS ==========#
    private final Scanner scanner;
    private final CategoriaService categoriaService;
    private final ProductoService productoService;
    private final UsuarioService usuarioService;
    private final PedidoService pedidoService;

    // #========== CONSTRUCTORES ==========#
    public MenuPrincipal() {
        this.scanner = new Scanner(System.in);
        this.categoriaService = new CategoriaService();
        this.productoService = new ProductoService(categoriaService);
        this.usuarioService = new UsuarioService();
        this.pedidoService = new PedidoService();
    }

    // #========== MENU PRINCIPAL ==========#
    public void mostrar() {
        int opcion;

        do {
            System.out.println();
            System.out.println("=== SISTEMA DE PEDIDOS (FOOD STORE) ===");
            System.out.println("1. Categorias");
            System.out.println("2. Productos");
            System.out.println("3. Usuarios");
            System.out.println("4. Pedidos");
            System.out.println("0. Salir");

            opcion = leerEntero("Seleccione: ");

            switch (opcion) {
                case 1:
                    menuCategorias();
                    break;
                case 2:
                    menuProductos();
                    break;
                case 3:
                    menuUsuarios();
                    break;
                case 4:
                    menuPedidos();
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opcion invalida.");
                    break;
            }
        } while (opcion != 0);
    }

    // #========== CATEGORIAS ==========#
    private void menuCategorias() {
        int opcion;

        do {
            System.out.println();
            System.out.println("=== MENU CATEGORIAS ===");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");

            opcion = leerEntero("Seleccione: ");

            try {
                switch (opcion) {
                    case 1:
                        listarCategorias();
                        break;
                    case 2:
                        crearCategoria();
                        break;
                    case 3:
                        editarCategoria();
                        break;
                    case 4:
                        eliminarCategoria();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opcion invalida.");
                        break;
                }
            } catch (NegocioException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        } while (opcion != 0);
    }

    private void listarCategorias() {
        List<Categoria> categorias = categoriaService.listar();

        if (categorias.isEmpty()) {
            System.out.println("No hay categorias cargadas.");
            return;
        }

        for (Categoria categoria : categorias) {
            System.out.println(categoria);
        }
    }

    private void crearCategoria() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Descripcion: ");
        String descripcion = scanner.nextLine();

        Categoria categoria = categoriaService.crear(nombre, descripcion);
        System.out.println("Categoria creada. ID: " + categoria.getId());
    }

    private void editarCategoria() {
        listarCategorias();
        Long id = leerLong("ID a editar: ");
        System.out.println("Deje vacio si no desea modificar.");
        System.out.print("Nuevo nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Nueva descripcion: ");
        String descripcion = scanner.nextLine();

        Categoria categoria = categoriaService.editar(id, nombre, descripcion);
        System.out.println("Categoria actualizada: " + categoria);
    }

    private void eliminarCategoria() {
        listarCategorias();
        Long id = leerLong("ID a eliminar: ");

        if (confirmar("Confirma eliminar? (S/N): ")) {
            categoriaService.eliminar(id);
            System.out.println("Categoria eliminada.");
        } else {
            System.out.println("Operacion cancelada.");
        }
    }

    // #========== PRODUCTOS ==========#
    private void menuProductos() {
        int opcion;

        do {
            System.out.println();
            System.out.println("=== MENU PRODUCTOS ===");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar precio/stock/categoria");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");

            opcion = leerEntero("Seleccione: ");

            try {
                switch (opcion) {
                    case 1:
                        listarProductos();
                        break;
                    case 2:
                        crearProducto();
                        break;
                    case 3:
                        editarProducto();
                        break;
                    case 4:
                        eliminarProducto();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opcion invalida.");
                        break;
                }
            } catch (NegocioException | NumberFormatException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        } while (opcion != 0);
    }

    private void listarProductos() {
        System.out.print("Categoria ID (ENTER = todas): ");
        String entrada = scanner.nextLine();
        Long categoriaId = entrada.isBlank() ? null : Long.valueOf(entrada);
        List<Producto> productos = productoService.listarProductos(categoriaId);

        if (productos.isEmpty()) {
            System.out.println("No hay productos cargados.");
            return;
        }

        for (Producto producto : productos) {
            System.out.println(producto);
        }
    }

    private void crearProducto() {
        if (categoriaService.listar().isEmpty()) {
            System.out.println("Primero debe crear una categoria.");
            return;
        }

        listarCategorias();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Descripcion: ");
        String descripcion = scanner.nextLine();
        double precio = leerDouble("Precio: ");
        int stock = leerEntero("Stock: ");
        System.out.print("Imagen: ");
        String imagen = scanner.nextLine();
        boolean disponible = confirmar("Disponible? (S/N): ");
        Long categoriaId = leerLong("Categoria ID: ");

        Producto producto = productoService.crearProducto(nombre, descripcion, precio, stock, imagen, disponible, categoriaId);
        System.out.println("Producto creado. ID: " + producto.getId());
    }

    private void editarProducto() {
        listarProductos();
        Long id = leerLong("ID producto: ");

        System.out.print("Nuevo precio (ENTER = mantener): ");
        String precioTexto = scanner.nextLine();
        Double precio = precioTexto.isBlank() ? null : Double.valueOf(precioTexto);

        System.out.print("Nuevo stock (ENTER = mantener): ");
        String stockTexto = scanner.nextLine();
        Integer stock = stockTexto.isBlank() ? null : Integer.valueOf(stockTexto);

        listarCategorias();
        System.out.print("Nueva categoria ID (ENTER = mantener): ");
        String categoriaTexto = scanner.nextLine();
        Long categoriaId = categoriaTexto.isBlank() ? null : Long.valueOf(categoriaTexto);

        productoService.editarProducto(id, precio, stock, categoriaId);
        System.out.println("Producto actualizado.");
    }

    private void eliminarProducto() {
        listarProductos();
        Long id = leerLong("ID a eliminar: ");

        if (confirmar("Confirma eliminar? (S/N): ")) {
            productoService.eliminarProducto(id);
            System.out.println("Producto eliminado.");
        } else {
            System.out.println("Operacion cancelada.");
        }
    }

    // #========== USUARIOS ==========#
    private void menuUsuarios() {
        int opcion;

        do {
            System.out.println();
            System.out.println("=== MENU USUARIOS ===");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");

            opcion = leerEntero("Seleccione: ");

            try {
                switch (opcion) {
                    case 1:
                        listarUsuarios();
                        break;
                    case 2:
                        crearUsuario();
                        break;
                    case 3:
                        editarUsuario();
                        break;
                    case 4:
                        eliminarUsuario();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opcion invalida.");
                        break;
                }
            } catch (NegocioException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        } while (opcion != 0);
    }

    private void listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarUsuariosActivos();

        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios cargados.");
            return;
        }

        for (Usuario usuario : usuarios) {
            System.out.println(usuario);
        }
    }

    private void crearUsuario() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();
        System.out.print("Mail: ");
        String mail = scanner.nextLine();
        System.out.print("Celular: ");
        String celular = scanner.nextLine();
        System.out.print("Contrasena: ");
        String contrasena = scanner.nextLine();
        Rol rol = leerRol(false);

        Usuario usuario = usuarioService.crearUsuario(nombre, apellido, mail, celular, contrasena, rol);
        System.out.println("Usuario creado. ID: " + usuario.getId());
    }

    private void editarUsuario() {
        listarUsuarios();
        Long id = leerLong("ID usuario: ");
        System.out.println("Deje vacio si no desea modificar.");
        System.out.print("Nuevo nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Nuevo apellido: ");
        String apellido = scanner.nextLine();
        System.out.print("Nuevo mail: ");
        String mail = scanner.nextLine();
        System.out.print("Nuevo celular: ");
        String celular = scanner.nextLine();
        System.out.print("Nueva contrasena: ");
        String contrasena = scanner.nextLine();
        Rol rol = leerRol(true);

        Usuario usuario = usuarioService.editarUsuario(id, nombre, apellido, mail, celular, contrasena, rol);
        System.out.println("Usuario actualizado: " + usuario);
    }

    private void eliminarUsuario() {
        listarUsuarios();
        Long id = leerLong("ID a eliminar: ");

        if (confirmar("Confirma eliminar? (S/N): ")) {
            usuarioService.eliminarUsuario(id);
            System.out.println("Usuario eliminado.");
        } else {
            System.out.println("Operacion cancelada.");
        }
    }

    // #========== PEDIDOS ==========#
    private void menuPedidos() {
        int opcion;

        do {
            System.out.println();
            System.out.println("=== MENU PEDIDOS ===");
            System.out.println("1. Listar");
            System.out.println("2. Crear pedido con detalles");
            System.out.println("3. Actualizar estado / forma de pago");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");

            opcion = leerEntero("Seleccione: ");

            try {
                switch (opcion) {
                    case 1:
                        listarPedidos();
                        break;
                    case 2:
                        crearPedido();
                        break;
                    case 3:
                        actualizarPedido();
                        break;
                    case 4:
                        eliminarPedido();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opcion invalida.");
                        break;
                }
            } catch (NegocioException | NumberFormatException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        } while (opcion != 0);
    }

    private void listarPedidos() {
        List<Pedido> pedidos = pedidoService.listarPedidos();

        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos cargados.");
            return;
        }

        for (Pedido pedido : pedidos) {
            System.out.println(pedido);
            for (var detalle : pedido.getDetalles()) {
                if (!detalle.isEliminado()) {
                    System.out.println("  - " + detalle);
                }
            }
        }
    }

    private void crearPedido() {
        if (usuarioService.listarUsuariosActivos().isEmpty()) {
            System.out.println("Primero debe crear un usuario.");
            return;
        }
        if (productoService.listarProductos(null).isEmpty()) {
            System.out.println("Primero debe crear un producto.");
            return;
        }

        listarUsuarios();
        Long usuarioId = leerLong("Usuario ID: ");
        Usuario usuario = usuarioService.buscarUsuarioActivoPorId(usuarioId);
        FormaPago formaPago = leerFormaPago();
        Pedido pedido = pedidoService.iniciarPedido(usuario, formaPago);

        boolean agregarOtro;
        do {
            listarProductos();
            Long productoId = leerLong("Producto ID: ");
            Producto producto = productoService.buscarProducto(productoId);
            int cantidad = leerEntero("Cantidad: ");

            pedido.addDetallePedido(cantidad, producto);
            System.out.println("Detalle agregado. Total parcial: " + pedido.getTotal());

            agregarOtro = confirmar("Agregar otro detalle? (S/N): ");
        } while (agregarOtro);

        pedidoService.guardarPedido(pedido);
        System.out.println("Pedido creado. ID: " + pedido.getId() + " | Total: " + pedido.getTotal());
    }

    private void actualizarPedido() {
        listarPedidos();
        Long id = leerLong("Pedido ID: ");
        Estado estado = leerEstado();
        FormaPago formaPago = leerFormaPago();

        pedidoService.actualizarEstadoYFormaPago(id, estado, formaPago);
        System.out.println("Pedido actualizado.");
    }

    private void eliminarPedido() {
        listarPedidos();
        Long id = leerLong("Pedido ID a eliminar: ");

        if (confirmar("Confirma eliminar? (S/N): ")) {
            pedidoService.eliminarPedido(id);
            System.out.println("Pedido eliminado.");
        } else {
            System.out.println("Operacion cancelada.");
        }
    }

    // #========== LECTURA DE DATOS ==========#
    private int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Debe ingresar un numero entero valido.");
            }
        }
    }

    private Long leerLong(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Long.valueOf(scanner.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Debe ingresar un ID valido.");
            }
        }
    }

    private double leerDouble(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Debe ingresar un numero valido.");
            }
        }
    }

    private boolean confirmar(String mensaje) {
        System.out.print(mensaje);
        String respuesta = scanner.nextLine();
        return respuesta.equalsIgnoreCase("S");
    }

    private Rol leerRol(boolean opcional) {
        while (true) {
            System.out.println("Rol:");
            System.out.println("1. ADMIN");
            System.out.println("2. USUARIO");
            if (opcional) {
                System.out.println("ENTER. Mantener");
            }

            System.out.print("Seleccione: ");
            String entrada = scanner.nextLine();

            if (opcional && entrada.isBlank()) {
                return null;
            }

            switch (entrada) {
                case "1":
                    return Rol.ADMIN;
                case "2":
                    return Rol.USUARIO;
                default:
                    System.out.println("Rol invalido.");
                    break;
            }
        }
    }

    private Estado leerEstado() {
        while (true) {
            System.out.println("Estado:");
            System.out.println("1. PENDIENTE");
            System.out.println("2. CONFIRMADO");
            System.out.println("3. TERMINADO");
            System.out.println("4. CANCELADO");

            int opcion = leerEntero("Seleccione: ");

            switch (opcion) {
                case 1:
                    return Estado.PENDIENTE;
                case 2:
                    return Estado.CONFIRMADO;
                case 3:
                    return Estado.TERMINADO;
                case 4:
                    return Estado.CANCELADO;
                default:
                    System.out.println("Estado invalido.");
                    break;
            }
        }
    }

    private FormaPago leerFormaPago() {
        while (true) {
            System.out.println("Forma de pago:");
            System.out.println("1. TARJETA");
            System.out.println("2. TRANSFERENCIA");
            System.out.println("3. EFECTIVO");

            int opcion = leerEntero("Seleccione: ");

            switch (opcion) {
                case 1:
                    return FormaPago.TARJETA;
                case 2:
                    return FormaPago.TRANSFERENCIA;
                case 3:
                    return FormaPago.EFECTIVO;
                default:
                    System.out.println("Forma de pago invalida.");
                    break;
            }
        }
    }
}
