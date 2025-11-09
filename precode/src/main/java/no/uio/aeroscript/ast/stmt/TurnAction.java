package no.uio.aeroscript.ast.stmt;

import java.util.HashMap;
import java.util.Map;

import main.java.no.uio.aeroscript.ast.stmt.LowBatteryException;
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
        @SuppressWarnings("unchecked")
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
        System.out.println("Turning " + direction + " by " + angleValue + " degrees");
        System.out.println("  Required battery " + batteryCost + " %");

        if (currentBattery - batteryCost < 0) {
            vars.put("battery level", 0.0f);
            throw new RuntimeException("Battery depleted!");
        }
        vars.put("battery level", currentBattery - batteryCost);

        
        System.out.println("  Battery remaining: " + vars.get("battery level") + " %");

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
