public record Accion(Tipo tipo, Object datos) {

    public enum Tipo {
        REGISTRAR_CLIENTE,
        ENCOLAR_PEDIDO,
        PROCESAR_PEDIDO
    }
}
