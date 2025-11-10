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
        System.out.println("\nReturning to base");
        System.out.println("    ├─ Required battery " + batteryCost + " %");
        System.out.println("    ├─ Current battery " + currentBattery + " %");

        if (currentBattery - batteryCost < 0) {
            throw new LowBatteryException(currentBattery);
        }

        // Calculate distance back to base
        float dx = initialPosition.getX() - currentPosition.getX();
        float dy = initialPosition.getY() - currentPosition.getY();
        distanceToBase = (float) Math.sqrt(dx * dx + dy * dy);

        System.out.println("    ├─ Descending from altitude " + altitude + " to ground");
        System.out.println("    └─ Moving from " + currentPosition + " to base at " + initialPosition);

        vars.put("current position", initialPosition);
        vars.put("altitude", 0.0f);
        vars.put("battery level", currentBattery - batteryCost);
        vars.put("distance travelled", distanceTravelled + altitude + distanceToBase);

    }

}
