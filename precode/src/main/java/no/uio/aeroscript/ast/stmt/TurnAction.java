package no.uio.aeroscript.ast.stmt;

import java.util.HashMap;
import java.util.Map;

import no.uio.aeroscript.error.LowBatteryException;
import no.uio.aeroscript.ast.expr.Expression;
import no.uio.aeroscript.type.Memory;

public class TurnAction extends Statement{

    private String direction;
    private Expression angle;
    private Expression duration;
    private Expression speed;

    public TurnAction(String direction, Expression angle, Expression duration, Expression speed) {
        this.direction = direction;
        this.angle = angle;
        this.duration = duration;
        this.speed = speed;
    }

    @Override
    public void execute() {
        HashMap<String, Object> vars = (HashMap<String, Object>) heap.get(Memory.VARIABLES);

        float angleValue = (float) angle.evaluate();
        float durationF = 0;
        float speedF = 0;

        if (duration != null) {
            durationF = ((Number) duration.evaluate()).floatValue();
        }
        if (speed != null) {
            speedF = ((Number) speed.evaluate()).floatValue();
        }

        float batteryCost = (angleValue * 0.3f) + (durationF * 0.1f) + (speedF * 1.0f);
        float currentBattery = (float) vars.get("battery level");
        float currentAltitude = (float) vars.get("altitude");

        System.out.println("\nTurning " + direction + " by " + angleValue + " degrees");
        System.out.println("     ├─ Required battery " + batteryCost + " %");
        System.out.println("     ├─ Current battery " + currentBattery + " %");
        System.out.println("     ├─ Current altitude " + currentAltitude);

        if (currentBattery - batteryCost < 0) {
            throw new LowBatteryException(currentBattery);
        }
        vars.put("battery level", currentBattery - batteryCost);

        
        System.out.println("     └─ Remaining battery: " + vars.get("battery level") + " %");

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
