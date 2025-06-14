// APPUTILS - MÉTODOS CUESTIONABLES
//
// Algunos métodos en AppUtils.java podrían ser innecesarios

// MÉTODOS QUE PODRÍAN ELIMINARSE:

// 1. estaVacia() - Java ya tiene isEmpty()
public static boolean estaVacia(List<?> lista) {
    return lista == null || lista.isEmpty();
}
// MEJOR: usar directamente lista.isEmpty()

// 2. obtenerODefault() - Java 8+ tiene Optional.orElse()
public static <T> T obtenerODefault(T valor, T valorDefault) {
    return valor != null ? valor : valorDefault;
}
// MEJOR: usar Optional.ofNullable(valor).orElse(valorDefault)

// 3. limpiarString() - muy específico, usar en casos puntuales
public static String limpiarString(String texto) {
    if (texto == null) return null;
    return texto.trim().replaceAll("\\s+", " ");
}

// MANTENER SOLO:
// - formatearFecha()
// - validarCedula()  
// - generarCodigoUnico()
// - capitalizarNombre()

// BENEFICIO: Eliminar 20-30 líneas de métodos poco útiles
