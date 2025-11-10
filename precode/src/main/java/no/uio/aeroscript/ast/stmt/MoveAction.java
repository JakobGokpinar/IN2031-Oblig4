package no.uio.aeroscript.ast.stmt;

import no.uio.aeroscript.ast.expr.Expression;
import java.util.HashMap;
import java.util.Map;

import no.uio.aeroscript.error.LowBatteryException;
import no.uio.aeroscript.type.Memory;
import no.uio.aeroscript.type.Point;

public class MoveAction extends Statement{

    private String moveType;
    private Object distance;
    private Expression time;
    private Expression speed;

    public MoveAction(String moveType, Object distance, Expression time, Expression speed) {
        this.moveType = moveType;
        this.distance = distance;
        this.time = time;
        this.speed = speed;
    }

    @Override
    public void execute() {
        @SuppressWarnings("unchecked")
        HashMap<String, Object> vars = (HashMap<String, Object>) heap.get(Memory.VARIABLES);
        Point currentPoint = (Point) vars.get("current position");
        Point targetPoint;
        float distanceF;
        float timeF = 0;
        float speedF = 0;
        float multiplier; //to calculate battery cost

        if (moveType == "Point") {
            targetPoint = (Point) distance;
            float dx = targetPoint.getX() - currentPoint.getX();
            float dy = targetPoint.getY() - currentPoint.getY();
            distanceF = (float) Math.sqrt(dx * dx + dy * dy);
            multiplier = 0.7f;
        } else {
            distanceF = Float.parseFloat(((Expression) distance).evaluate().toString());
            targetPoint = new Point(
                currentPoint.getX() + distanceF,
                currentPoint.getY()
            );
            multiplier = 0.5f;
        }

        if (time != null) {
            timeF = ((Number) time.evaluate()).floatValue();
        }
        if (speed != null) {
            speedF = ((Number) speed.evaluate()).floatValue();
        }

        float batteryCost = (distanceF * multiplier) + (timeF * 0.1f) + (speedF * 1.0f);
        float currentBattery = (float) vars.get("battery level");
        float currentDTravelled = (float) vars.get("distance travelled");
        System.out.println("Moving to " + targetPoint);
        System.out.println("  Distance: " + distanceF + " meters");
        System.out.println("  Required battery " + batteryCost + " %");

        if (currentBattery < batteryCost) {
            vars.put("battery level", 0.0f);
            throw new RuntimeException("Battery depleted! Cannot move.");
        }

        vars.put("current position", targetPoint);
        vars.put("distance travelled", currentDTravelled + distanceF);
        vars.put("battery level", currentBattery - batteryCost);

        System.out.println("  Total distance: " + vars.get("distance travelled") + " meters");
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
