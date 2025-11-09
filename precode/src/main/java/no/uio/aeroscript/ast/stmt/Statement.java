package no.uio.aeroscript.ast.stmt;

import java.util.HashMap;

import no.uio.aeroscript.type.Memory;

public abstract class Statement {
    protected HashMap<Memory, Object> heap;
    
    public void setHeap(HashMap<Memory, Object> heap) { 
        this.heap = heap;
    }

    public abstract void execute();
}
