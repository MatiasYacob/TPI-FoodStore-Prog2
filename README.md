# Food Store - Sistema de Gestion de Pedidos

Food Store es una aplicacion de consola desarrollada en Java para administrar un negocio de comidas. Permite gestionar categorias, productos, usuarios y pedidos usando Programacion Orientada a Objetos, colecciones en memoria, validaciones y manejo de excepciones.

## Funcionalidades

- Gestion de categorias.
- Gestion de productos asociados a categorias.
- Gestion de usuarios.
- Creacion de pedidos con uno o mas detalles.
- Calculo automatico de subtotales y total del pedido.
- Actualizacion de estado y forma de pago.
- Baja logica de registros mediante el atributo `eliminado`.
- Validacion de datos ingresados por consola.
- Manejo de errores con excepciones propias.

## Tecnologias

- Java
- NetBeans
- Aplicacion de consola
- Colecciones en memoria
- Programacion Orientada a Objetos

## Estructura

```text
src/
+-- tpi/
    +-- progra2/
        +-- TPIProgra2.java
        +-- entities/
        +-- enums/
        +-- exceptions/
        +-- interfaces/
        +-- menus/
        +-- services/
```

## Entidades principales

- `Categoria`
- `Producto`
- `Usuario`
- `Pedido`
- `DetallePedido`

Todas las entidades heredan de `Base`, que contiene:

- `id`
- `eliminado`
- `createdAt`

## Menu principal

Al ejecutar el programa se muestra el siguiente menu:

```text
=== SISTEMA DE PEDIDOS (FOOD STORE) ===
1. Categorias
2. Productos
3. Usuarios
4. Pedidos
0. Salir
```

Cada opcion abre un submenu con operaciones de alta, listado, modificacion y baja logica.

## Como ejecutar el proyecto

1. Clonar el repositorio:

```bash
git clone https://github.com/MatiasYacob/TPI-FoodStore-Prog2.git
```

2. Abrir el proyecto en NetBeans.

3. Verificar que la clase principal sea:

```text
tpi.progra2.TPIProgra2
```

4. Ejecutar **Clean and Build**.

5. Ejecutar el proyecto desde NetBeans.

## Flujo de prueba recomendado

Para probar el sistema completo:

1. Crear una categoria.
2. Crear un producto asociado a esa categoria.
3. Crear un usuario.
4. Crear un pedido seleccionando usuario, forma de pago, producto y cantidad.
5. Listar pedidos.
6. Verificar que el subtotal y el total se calculen correctamente.

Ejemplo:

```text
subtotal = cantidad * precio del producto
total = suma de subtotales
```

## Reglas principales

- No se puede crear un producto con precio negativo.
- No se puede crear un producto con stock negativo.
- No se puede crear un pedido sin usuario.
- No se puede crear un detalle con cantidad menor o igual a cero.
- No se deben usar usuarios eliminados para nuevos pedidos.
- Los pedidos y demas entidades se eliminan mediante baja logica.

## Notas

- El proyecto no utiliza base de datos.
- Los datos se guardan en memoria mientras el programa esta en ejecucion.
- Al cerrar el programa, los datos cargados se pierden.
- El paquete principal del proyecto es `tpi.progra2`.
