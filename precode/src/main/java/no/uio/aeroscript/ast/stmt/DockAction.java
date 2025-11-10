package no.uio.aeroscript.ast.stmt;

import java.util.HashMap;
import java.util.Map;

import no.uio.aeroscript.error.LowBatteryException;
import no.uio.aeroscript.ast.expr.Expression;
import no.uio.aeroscript.type.Memory;
import no.uio.aeroscript.type.Point;

public class DockAction extends Statement{

    /* We'll never need these, but still count in while calculation battery status */
    private Expression time;
    private Expression speed;

    public DockAction(Expression time, Expression speed) {
        this.time = time;
        this.speed = speed;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void execute() {
        HashMap<String, Object> vars = (HashMap<String, Object>) heap.get(Memory.VARIABLES);
        float timeF = 0;
        float speedF = 0;
        float distanceToBase = 0;
        
        if (time != null) {
            timeF = ((Number) time.evaluate()).floatValue();
        }
        if (speed != null) {
            speedF = ((Number) speed.evaluate()).floatValue();
        }

        float altitude = (float) vars.get("altitude");
        float currentBattery = (float) vars.get("battery level");
        float distanceTravelled = (float) vars.get("distance travelled");
        Point currentPosition = (Point) vars.get("current position");
        Point initialPosition = (Point) vars.get("initial position");

        float batteryCost = altitude + (timeF * 0.1f) + (speedF * 1.0f); 
        System.out.println("Returning to base");
        System.out.println("  Required battery " + batteryCost + " %");

        if (currentBattery - batteryCost < 0) {
            vars.put("battery level", 0.0f);
            throw new RuntimeException("Battery depleted before returning to base!");
        }

        // Calculate distance back to base
        float dx = initialPosition.getX() - currentPosition.getX();
        float dy = initialPosition.getY() - currentPosition.getY();
        distanceToBase = (float) Math.sqrt(dx * dx + dy * dy);

        System.out.println("  Descending from altitude " + altitude + " to ground");
        System.out.println("  Moving from " + currentPosition + " to base at " + initialPosition);

        vars.put("current position", initialPosition);
        vars.put("altitude", 0.0f);
        vars.put("battery level", currentBattery - batteryCost);
        vars.put("distance travelled", distanceTravelled + altitude + distanceToBase);


        // Printing final statistics
        System.out.println();
        System.out.println("=== MISSION COMPLETED ===");
        System.out.println("Total distance travelled: " + vars.get("distance travelled") + " meters");
        System.out.println("Final battery level: " + vars.get("battery level") + " %");
        System.out.println("========================");   
        
        checkLowBattery();
    }

    private void checkLowBattery() {
        HashMap<String, Object> vars = (HashMap<String, Object>) heap.get(Memory.VARIABLES);
        float batteryLevel = (float) vars.get("battery level");
        
        if (batteryLevel < 20.0f && batteryLevel > 0) {
            System.out.println("WARNING: Battery level critically low (" + String.format("%.2f", batteryLevel) + "%)!");
            
            Map<String, String> reactions = (Map<String, String>) heap.get(Memory.REACTIONS);         
            // Only throw if reaction exists AND not already triggered
            if (reactions != null && reactions.containsKey("low battery")) {
                // Check if we're already in emergency by seeing if battery was already low
                Boolean emergencyTriggered = (Boolean) vars.get("emergency_triggered");
                if (emergencyTriggered == null || !emergencyTriggered) {
                    vars.put("emergency_triggered", true);  // Mark as triggered
                    throw new LowBatteryException(batteryLevel);
                }
            }
        }     
        if (batteryLevel <= 0) {
            System.err.println("Battery depleted!");
            System.exit(1);
        }
    }
}
