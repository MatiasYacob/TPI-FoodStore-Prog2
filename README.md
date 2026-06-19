# TPI Food Store - Programacion 2

Sistema de consola en Java para la gestion de pedidos de comida. El proyecto trabaja con Programacion Orientada a Objetos, colecciones en memoria, validaciones, excepciones propias y menus CRUD.

## Estado del proyecto

El repositorio se esta armando por partes para que cada integrante suba su modulo:

| Parte | Modulo | Responsable |
| --- | --- | --- |
| 1 | Gestion de categorias | Integrante A |
| 2 | Gestion de productos | Integrante B |
| 3 | Gestion de usuarios | Integrante C |
| 4 | Gestion de pedidos y detalles | Integrante D |

## Funcionalidades esperadas

- Gestionar categorias.
- Gestionar productos asociados a categorias.
- Gestionar usuarios.
- Crear pedidos con detalles.
- Calcular subtotales y total del pedido.
- Actualizar estado y forma de pago.
- Realizar bajas logicas.
- Validar datos de entrada.
- Manejar errores con excepciones propias.

## Estructura del proyecto

```text
src/
└── tpi/
    └── progra2/
        ├── TPIProgra2.java
        ├── entities/
        ├── enums/
        ├── exceptions/
        ├── interfaces/
        ├── menus/
        └── services/
```

## Base comun

La base comun incluye:

- `TPIProgra2.java`
- `MenuPrincipal.java`
- `Base.java`
- `Calculable.java`
- `Rol.java`
- `Estado.java`
- `FormaPago.java`
- excepciones comunes

## Parte 4 - Pedidos y detalles

Esta parte incluye:

- `Pedido.java`
- `DetallePedido.java`
- `PedidoService.java`

Responsabilidades principales:

- Crear pedidos asociados a usuarios.
- Agregar productos al pedido mediante detalles.
- Calcular subtotal por detalle.
- Calcular total del pedido usando `Calculable`.
- Validar usuario, producto, cantidad y stock.
- Actualizar estado y forma de pago.
- Eliminar pedidos con baja logica.

## Como ejecutar

1. Abrir el proyecto en NetBeans.
2. Verificar que la clase principal sea:

```text
tpi.progra2.TPIProgra2
```

3. Ejecutar **Clean and Build**.
4. Ejecutar el proyecto.

## Menu principal

```text
=== SISTEMA DE PEDIDOS (FOOD STORE) ===
1. Categorias
2. Productos
3. Usuarios
4. Pedidos
0. Salir
```

## Orden recomendado de integracion

1. Subir base comun.
2. Subir gestion de categorias.
3. Subir gestion de productos.
4. Subir gestion de usuarios.
5. Subir gestion de pedidos.
6. Probar el flujo completo desde consola.

## Prueba sugerida

Para verificar el sistema completo:

1. Crear una categoria.
2. Crear un producto asociado a esa categoria.
3. Crear un usuario.
4. Crear un pedido con uno o mas detalles.
5. Listar pedidos.
6. Verificar que:

```text
subtotal = cantidad * precio
total = suma de subtotales
```

## Notas

- El almacenamiento es en memoria usando colecciones.
- No se utiliza base de datos.
- Todas las bajas son logicas mediante el atributo `eliminado`.
- No se deben copiar los paquetes originales de los ZIP separados; el paquete final es `tpi.progra2`.
