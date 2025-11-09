package no.uio.aeroscript.ast.stmt;

import java.util.HashMap;
import java.util.Map;

import main.java.no.uio.aeroscript.ast.stmt.LowBatteryException;
import no.uio.aeroscript.ast.expr.Expression;
import no.uio.aeroscript.type.Memory;

public class AscendAction extends Statement {

    private Expression distance; //ascend value
    private Expression time;
    private Expression speed;


    public AscendAction(Expression distance, Expression time, Expression speed) {
        this.distance = distance;
        this.time = time;
        this.speed = speed;
    }

    @Override
    public void execute() {
        @SuppressWarnings("unchecked")
        HashMap<String, Object> vars = (HashMap<String, Object>) heap.get(Memory.VARIABLES);
        float distanceF = 0;
        float timeF = 0;
        float speedF = 0;
        
        distanceF = ((Number) distance.evaluate()).floatValue();
        if (time != null) {
            timeF = ((Number) time.evaluate()).floatValue();
        }
        if (speed != null) {
            speedF = ((Number) speed.evaluate()).floatValue();
        }

        float currentAltitude = (float) vars.get("altitude");
        float currentBattery = (float) vars.get("battery level");
        float batteryCost = (distanceF * 0.6f) + (timeF * 0.1f) + (speedF * 1.0f);
        float currentDistance = (float) vars.get("distance travelled");

        System.out.println("Ascending by " + distanceF + " meters");
        System.out.println("  Required battery " + batteryCost + " %");
        
        if (currentBattery < batteryCost) {
            vars.put("battery level", 0.0f);
            throw new RuntimeException("Battery depleted!");
        }

        vars.put("altitude", currentAltitude + distanceF);
        vars.put("battery level", currentBattery - batteryCost);
        vars.put("distance travelled", currentDistance + distanceF);


        System.out.println("  New altitude: " + vars.get("altitude"));
        System.out.println("  Battery remaining: " + vars.get("battery level") + " %");
        System.out.println("  Distance travelled: " + vars.get("distance travelled") + " meters");

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
