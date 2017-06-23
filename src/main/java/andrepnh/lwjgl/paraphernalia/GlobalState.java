package andrepnh.lwjgl.paraphernalia;

import andrepnh.lwjgl.paraphernalia.loop.LoopKind;
import com.google.common.collect.Iterators;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class GlobalState {
    public final long window;
    
    public int x;
    
    public int y;
    
    public final List<int[]> squares;
    
    public boolean slowRender;
    
    public boolean slowUpdate;
    
    private final Iterator<LoopKind> cyclicLoopKinds;
    
    public LoopKind currentLoopKind;

    public GlobalState(long window) {
        this.window = window;
        squares = IntStream.range(0, 10)
            .mapToObj(i -> new int[] {0, i})
            .collect(Collectors.toList());
        cyclicLoopKinds = Iterators.cycle(LoopKind.values());
        nextLoopKind();
    }
    
    public void toogleSlowRender() {
        slowRender = !slowRender;
    }
    
    public void toogleSlowUpdate() {
        slowUpdate = !slowUpdate;
    }
    
    public void nextLoopKind() {
        currentLoopKind = cyclicLoopKinds.next();
    }
}
