import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Modelo {

    private final Map<String, Cliente> clientes = new HashMap<>();
    private final ArrayDeque<Pedido> cola = new ArrayDeque<>();
    private final List<Pedido> pedidosCompletados = new ArrayList<>();
    private final Deque<Accion> acciones = new ArrayDeque<>();

    private BigDecimal total = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

    public String registrarCliente(String dni, String nombre, Cliente.Tipo tipo) {
        String dniNormalizado = normalizarDni(dni);
        String nombreNormalizado = nombre == null ? "" : nombre.trim();

        if (dniNormalizado.isEmpty()) {
            return "El DNI no puede estar vacío.";
        }
        if (nombreNormalizado.isEmpty()) {
            return "El nombre no puede estar vacío.";
        }
        if (tipo == null) {
            return "Tipo de cliente inválido.";
        }
        if (clientes.containsKey(dniNormalizado)) {
            return "Ya existe un cliente con DNI " + dniNormalizado + ".";
        }

        Cliente cliente = switch (tipo) {
            case REGULAR -> new ClienteRegular(dniNormalizado, nombreNormalizado);
            case VIP -> new ClienteVIP(dniNormalizado, nombreNormalizado);
        };

        clientes.put(dniNormalizado, cliente);
        acciones.addLast(new Accion(Accion.Tipo.REGISTRAR_CLIENTE, dniNormalizado));
        return "Cliente registrado: " + cliente;
    }

    public String encolarPedido(String dni, BigDecimal monto) {
        Cliente cliente = clientes.get(normalizarDni(dni));
        if (cliente == null) {
            return "No hay un cliente registrado con ese DNI.";
        }
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            return "El monto debe ser mayor a 0.";
        }

        Pedido pedido = new Pedido(cliente, monto);
        cola.add(pedido);
        acciones.addLast(new Accion(Accion.Tipo.ENCOLAR_PEDIDO, pedido));
        return "Pedido encolado: " + pedido;
    }

    public String procesarSiguientePedido() {
        Pedido pedido = cola.poll();
        if (pedido == null) {
            return "No hay pedidos en la cola.";
        }

        total = total.add(pedido.getMontoFinal());
        pedidosCompletados.add(pedido);
        acciones.addLast(new Accion(Accion.Tipo.PROCESAR_PEDIDO, pedido));
        return "Pedido procesado: " + pedido;
    }

    public String deshacer() {
        Accion accion = acciones.pollLast();
        if (accion == null) {
            return "No hay operaciones para deshacer.";
        }

        return switch (accion.tipo()) {
            case REGISTRAR_CLIENTE -> deshacerRegistro((String) accion.datos());
            case ENCOLAR_PEDIDO -> deshacerEncolado((Pedido) accion.datos());
            case PROCESAR_PEDIDO -> deshacerProcesado((Pedido) accion.datos());
        };
    }

    public String buscarCliente(String dni) {
        Cliente cliente = clientes.get(normalizarDni(dni));
        if (cliente == null) {
            return "No se encontró un cliente con ese DNI.";
        }
        return cliente.toString();
    }

    public String reporte() {
        if (pedidosCompletados.isEmpty()) {
            return "No hay pedidos procesados.\nTotal = " + total;
        }

        StringBuilder sb = new StringBuilder("Pedidos procesados:\n");
        for (int i = 0; i < pedidosCompletados.size(); i++) {
            sb.append(i + 1).append(". ").append(pedidosCompletados.get(i)).append('\n');
        }
        sb.append("Total = ").append(total);
        return sb.toString();
    }

    private String deshacerRegistro(String dni) {
        Cliente eliminado = clientes.remove(dni);
        if (eliminado == null) {
            return "No se pudo deshacer el registro: el cliente ya no está en el sistema.";
        }
        return "Se deshizo el registro del cliente " + eliminado.getDni() + ".";
    }

    private String deshacerEncolado(Pedido pedido) {
        if (!cola.remove(pedido)) {
            return "No se pudo deshacer el encolado: el pedido ya no está en la cola.";
        }
        return "Se deshizo el encolado del pedido: " + pedido;
    }

    private String deshacerProcesado(Pedido pedido) {
        if (!pedidosCompletados.remove(pedido)) {
            return "No se pudo deshacer el procesamiento: el pedido ya no está en completados.";
        }
        total = total.subtract(pedido.getMontoFinal());
        cola.addFirst(pedido);
        return "Se deshizo el procesamiento. El pedido volvió al frente de la cola.";
    }

    private static String normalizarDni(String dni) {
        return dni == null ? "" : dni.trim();
    }
}
