package no.uio.aeroscript.ast.stmt;

import java.util.HashMap;
import java.util.Map;

import no.uio.aeroscript.error.LowBatteryException;
import no.uio.aeroscript.ast.expr.Expression;
import no.uio.aeroscript.type.Memory;

public class DescendAction extends Statement{

    private Expression distance; //ascend value
    private boolean toGround;
    private Expression time;
    private Expression speed;


    public DescendAction(Expression distance, Expression time, Expression speed, boolean toGround) {
        this.distance = distance;
        this.toGround = toGround;
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

        if (toGround) {
            distanceF = (float) vars.get("altitude");
        } else if (distance != null) {  // ADD null check
            distanceF = ((Number) distance.evaluate()).floatValue();
        }

        if (time != null) {
            timeF = ((Number) time.evaluate()).floatValue();
        }
        if (speed != null) {
            speedF = ((Number) speed.evaluate()).floatValue();
        }

        float currentAltitude = (float) vars.get("altitude");
        float currentBattery = (float) vars.get("battery level");
        float currentDistance = (float) vars.get("distance travelled");
        float batteryCost = (distanceF * 0.2f) + (timeF * 0.1f) + (speedF * 1.0f);
        
        System.out.println("Descending by " + distanceF + " meters");
        System.out.println("  Required battery " + batteryCost + " %");

        if (currentBattery < batteryCost) {
            vars.put("battery level", 0.0f);
            throw new RuntimeException("Battery depleted while descending!");
        }

        float newAltitude = Math.max(0, currentAltitude - distanceF);
        vars.put("altitude", newAltitude);
        vars.put("battery level", currentBattery - batteryCost);
        vars.put("distance travelled", currentDistance + distanceF);

        System.out.println("  New altitude: " + vars.get("altitude") + " meters");
        System.out.println("  Battery remaining: " + String.format("%.2f", (float) vars.get("battery level")) + " %");      
        System.out.println("  Distance travelled: " + vars.get("distance travelled") + " meters");

        checkLowBattery();
    }   

    private void checkLowBattery() {
        HashMap<String, Object> vars = (HashMap<String, Object>) heap.get(Memory.VARIABLES);
        float batteryLevel = (float) vars.get("battery level");
        
        if (batteryLevel < 20.0f && batteryLevel > 0) {            
            Map<String, String> reactions = (Map<String, String>) heap.get(Memory.REACTIONS);         
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
            throw new LowBatteryException(0.0f);
        }
    }
}
