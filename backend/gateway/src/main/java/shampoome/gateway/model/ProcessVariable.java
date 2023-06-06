package shampoome.gateway.model;

public class ProcessVariable<T> {
    public EmbeddedValue name;

    public T value;
    private String type;

    public String getType() {
        return value.getClass().getName();
    }

    class EmbeddedValue {

    }
}
