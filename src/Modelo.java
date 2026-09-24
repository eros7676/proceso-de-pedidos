import java.util.*;

public class Modelo {

    private HashMap<String, Cliente> clientes;
    private int contador;

    private Scanner in;

    private LinkedList<Pedido> cola;
    private ArrayList<Pedido> pedidosCompletados;

    private float total;

    private LinkedList<Accion> acciones;

    public Modelo(){
        clientes = new HashMap<>();
        cola = new LinkedList<>();
        acciones = new LinkedList<>();
    }

    public void bucle(){
        in = new Scanner(System.in);

        boolean flag = true;

        while (flag){

            System.out.println("""
                    === SISTEMA DE LOGÍSTICA Y PEDIDOS ===
                    1. Registrar un nuevo cliente (Guarda en el Map)
                    2. Encolar un nuevo pedido (Entra a la Queue)
                    3. Procesar siguiente pedido de la cola (Saca de la Queue, calcula total y pasa a List)
                    4. Deshacer última operación (Usa el Stack para revertir)
                    5. Buscar cliente por DNI (Usa el Map)
                    6. Ver reporte de pedidos procesados y recaudación total
                    7. Salir
                    """);

            int opcion = in.nextInt();
            in.nextLine();

            switch(opcion){
                case 1:
                    registrarCliente();
                    break;
                case 2:
                    encolarPedido();
                    break;
                case 3:
                    procesarSiguientePedido();
                    break;
                case 4:
                    deshacer();
                    break;
                case 5:
                    obtenerCliente();
                    break;
                case 6:
                    verPedidosCompletadosYTotal();
                    break;
                case 7:
                    flag = false;
                    break;
            }
        }
    }

    public void registrarCliente(){

        System.out.println("Ingrese DNI");
        String dni = in.nextLine();

        System.out.println("Ingrese Nombre");
        String nombre = in.nextLine();

        Cliente cliente;

        System.out.println("""
                1. Regular
                2. VIP
                """);
        cliente = switch (in.nextInt()) {
            case 1 -> new ClienteRegular(contador++, nombre);
            case 2 -> new ClienteVIP(contador++, nombre);
            default -> null;
        };

        clientes.put(dni, cliente);
        System.out.println("Cliente registrado!");

        acciones.addLast(new Accion(Accion.REGISTRAR_USUARIO, dni));
    }

    public void encolarPedido(){

        System.out.println("Ingrese dni de cliente");
        String dni = in.nextLine();

        System.out.println("Ingrese el monto");
        float monto = in.nextFloat();
        
        Pedido pedido = new Pedido(clientes.get(dni), monto);

        cola.addLast(pedido);

        acciones.addLast(new Accion(Accion.ENCOLAR_PEDIDO, null));
    }

    public void procesarSiguientePedido(){
        Pedido pedido = cola.pollFirst();

        this.total += pedido.cliente.calcularTotalConDescuento(pedido.monto);

        pedidosCompletados.add(pedido);

        acciones.addLast(new Accion(Accion.PROCESAR_PEDIDO, pedido));
    }

    public void deshacer(){

        Accion accion = acciones.pollLast();

        switch (accion.tipo){
            case Accion.REGISTRAR_USUARIO:
                String dni = (String) accion.objeto;

                // eliminar cliente
                clientes.remove(dni);
                break;
            case Accion.ENCOLAR_PEDIDO:
                cola.removeLast();
                break;
            case Accion.PROCESAR_PEDIDO:
                Pedido pedido = (Pedido) accion.objeto;

                // sacarlo de pedidos completados
                pedidosCompletados.removeLast();

                // restarlo del total
                total -= pedido.cliente.calcularTotalConDescuento(pedido.monto);

                // volverlo a poner en la cola (primero)
                cola.addFirst(pedido);
                break;
        }

    }

    public void obtenerCliente(){
        System.out.println("Ingrese dni de cliente");
        String dni = in.nextLine();

        System.out.println(clientes.get(dni));
    }

    public void verPedidosCompletadosYTotal(){
        System.out.println(pedidosCompletados);

        System.out.println("Total = " + this.total);
    }

    public static void main(String[] args) {

        Modelo modelo = new Modelo();

        modelo.bucle();
    }
}



/*
=== SISTEMA DE LOGÍSTICA Y PEDIDOS ===
1. Registrar un nuevo cliente (Guarda en el Map)
2. Encolar un nuevo pedido (Entra a la Queue)
3. Procesar siguiente pedido de la cola (Saca de la Queue, calcula total y pasa a List)
4. Deshacer última operación (Usa el Stack para revertir)
5. Buscar cliente por DNI (Usa el Map)
6. Ver reporte de pedidos procesados y recaudación total
7. Salir
 */