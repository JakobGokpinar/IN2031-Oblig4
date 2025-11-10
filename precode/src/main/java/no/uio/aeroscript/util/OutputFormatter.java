package no.uio.aeroscript.util;

public class OutputFormatter {
    
    // Section headers
    public static void printSectionHeader(String title) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  " + title);
        System.out.println("=".repeat(60));
    }
    
    // Action with details
    public static void printActionStart(String action) {
        System.out.println("\n▸ " + action);
    }
    
    public static void printDetail(String key, String value) {
        System.out.println("  ├─ " + key + ": " + value);
    }
    
    public static void printLastDetail(String key, String value) {
        System.out.println("  └─ " + key + ": " + value);
    }
    
    // Warnings
    public static void printWarning(String message) {
        System.out.println("\n" + "!".repeat(60));
        System.out.println("⚠️  WARNING: " + message);
        System.out.println("!".repeat(60));
    }
    
    // Mode transitions
    public static void printTransition(String fromMode, String toMode) {
        System.out.println("\n" + "─".repeat(60));
        System.out.println("  Transitioning: " + fromMode + " → " + toMode);
        System.out.println("─".repeat(60));
    }
    
    // Format numbers
    public static String formatFloat(float value) {
        return String.format("%.2f", value);
    }
    
    public static String formatPercent(float value) {
        return String.format("%.2f%%", value);
    }
}