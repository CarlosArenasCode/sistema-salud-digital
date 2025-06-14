package com.clinica.salud.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Utilidades comunes para el backend
 * Centraliza operaciones frecuentemente usadas
 */
public class AppUtils {
    
    private static final DateTimeFormatter FECHA_FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final Pattern CEDULA_PATTERN = Pattern.compile("^\\d{10}$");
    
    private AppUtils() {
        // Clase utilitaria - constructor privado
    }
    
    /**
     * Formatea una fecha para mostrar en la UI
     */
    public static String formatearFecha(LocalDateTime fecha) {
        if (fecha == null) {
            return "N/A";
        }
        return fecha.format(FECHA_FORMATO);
    }
    
    /**
     * Valida si una cédula ecuatoriana es válida
     */
    public static boolean validarCedula(String cedula) {
        if (cedula == null || !CEDULA_PATTERN.matcher(cedula).matches()) {
            return false;
        }
        
        int[] coeficientes = {2, 1, 2, 1, 2, 1, 2, 1, 2};
        int suma = 0;
        
        for (int i = 0; i < 9; i++) {
            int digito = Character.getNumericValue(cedula.charAt(i));
            int producto = digito * coeficientes[i];
            if (producto > 9) {
                producto -= 9;
            }
            suma += producto;
        }
        
        int digitoVerificador = Character.getNumericValue(cedula.charAt(9));
        int modulo = suma % 10;
        int resultado = modulo == 0 ? 0 : 10 - modulo;
        
        return resultado == digitoVerificador;
    }
    
    /**
     * Genera un código único basado en timestamp
     */
    public static String generarCodigoUnico(String prefijo) {
        long timestamp = System.currentTimeMillis();
        return (prefijo != null ? prefijo : "COD") + timestamp;
    }
    
    /**
     * Capitaliza la primera letra de cada palabra
     */
    public static String capitalizarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return nombre;
        }
        
        String[] palabras = nombre.toLowerCase().trim().split("\\s+");
        StringBuilder resultado = new StringBuilder();
        
        for (int i = 0; i < palabras.length; i++) {
            if (i > 0) {
                resultado.append(" ");
            }
            if (!palabras[i].isEmpty()) {
                resultado.append(Character.toUpperCase(palabras[i].charAt(0)))
                         .append(palabras[i].substring(1));
            }
        }
        
        return resultado.toString();
    }
    
    /**
     * Verifica si una lista está vacía o nula
     */
    public static boolean estaVacia(List<?> lista) {
        return lista == null || lista.isEmpty();
    }
    
    /**
     * Obtiene el valor o un default si es null
     */
    public static <T> T obtenerODefault(T valor, T valorDefault) {
        return valor != null ? valor : valorDefault;
    }
    
    /**
     * Limpia y normaliza un string
     */
    public static String limpiarString(String texto) {
        if (texto == null) {
            return null;
        }
        return texto.trim().replaceAll("\\s+", " ");
    }
}
