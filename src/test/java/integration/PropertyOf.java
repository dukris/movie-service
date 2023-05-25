package integration;

import lombok.AllArgsConstructor;
import org.springframework.test.context.DynamicPropertyRegistry;

@AllArgsConstructor
public class PropertyOf {

    private final DynamicPropertyRegistry registry;
    private final String key;
    private final Object value;

    public void set() {
        this.registry.add(this.key, () -> this.value);
    }

}