package shampoome.gateway.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class OrderInput {

    @NotNull
    public String processId;

    @Pattern(regexp = "^[a-zA-Z0-9/ -]{5,}$")
    public String shippingAddress;

    @Pattern(regexp = "^[0-9]{8}$")
    public String matriculationNumber;
}
