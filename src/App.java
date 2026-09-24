import java.math.BigDecimal;
import java.util.Scanner;

public class App {

    private final Modelo modelo = new Modelo();
    private final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        new App().iniciar();
    }

    public void iniciar() {
        boolean seguir = true;
        while (seguir) {
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

            Integer opcion = leerEntero("Opción: ");
            if (opcion == null) {
                continue;
            }

            switch (opcion) {
                case 1 -> registrarCliente();
                case 2 -> encolarPedido();
                case 3 -> System.out.println(modelo.procesarSiguientePedido());
                case 4 -> System.out.println(modelo.deshacer());
                case 5 -> buscarCliente();
                case 6 -> System.out.println(modelo.reporte());
                case 7 -> seguir = false;
                default -> System.out.println("Opción inválida. Elegí un número del 1 al 7.");
            }
            System.out.println();
        }
        in.close();
    }

    private void registrarCliente() {
        String dni = leerLinea("Ingrese DNI: ");
        String nombre = leerLinea("Ingrese nombre: ");
        System.out.println("""
                1. Regular
                2. VIP
                """);
        Integer tipoOpcion = leerEntero("Tipo: ");
        Cliente.Tipo tipo = switch (tipoOpcion == null ? -1 : tipoOpcion) {
            case 1 -> Cliente.Tipo.REGULAR;
            case 2 -> Cliente.Tipo.VIP;
            default -> null;
        };
        if (tipo == null) {
            System.out.println("Tipo inválido. El cliente no se registró.");
            return;
        }
        System.out.println(modelo.registrarCliente(dni, nombre, tipo));
    }

    private void encolarPedido() {
        String dni = leerLinea("Ingrese DNI de cliente: ");
        BigDecimal monto = leerMonto("Ingrese el monto: ");
        if (monto == null) {
            return;
        }
        System.out.println(modelo.encolarPedido(dni, monto));
    }

    private void buscarCliente() {
        String dni = leerLinea("Ingrese DNI de cliente: ");
        System.out.println(modelo.buscarCliente(dni));
    }

    private String leerLinea(String prompt) {
        System.out.print(prompt);
        return in.nextLine();
    }

    private Integer leerEntero(String prompt) {
        System.out.print(prompt);
        String texto = in.nextLine().trim();
        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            System.out.println("Ingresá un número entero válido.");
            return null;
        }
    }

    private BigDecimal leerMonto(String prompt) {
        System.out.print(prompt);
        String texto = in.nextLine().trim().replace(',', '.');
        try {
            return new BigDecimal(texto);
        } catch (NumberFormatException e) {
            System.out.println("Ingresá un monto numérico válido.");
            return null;
        }
    }
}
