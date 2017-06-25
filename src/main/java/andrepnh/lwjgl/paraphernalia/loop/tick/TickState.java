package andrepnh.lwjgl.paraphernalia.loop.tick;

import java.util.HashMap;
import java.util.Map;

public class TickState {
    private final Map<TickProperty, Object> state = new HashMap<>();
    
    public Map<TickProperty, Object> put(TickProperty prop, Object value) {
        state.put(prop, value);
        return state;
    }
    
    public <T> T get(TickProperty prop) {
        return (T) state.get(prop);
    }
}
