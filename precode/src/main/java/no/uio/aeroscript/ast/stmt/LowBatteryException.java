package main.java.no.uio.aeroscript.ast.stmt;

/*
 * Exception thrown when battery level drops below 20%
 */
public class LowBatteryException extends RuntimeException{
    private final float batteryLevel;

    public LowBatteryException(float batteryLevel) {
        super("Battery level critically low: " + batteryLevel + "%");
        this.batteryLevel = batteryLevel;
    }
    
    public float getBatteryLevel() {
        return batteryLevel;
    }

}
