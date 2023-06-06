package shampoome.gateway.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class Feedback {

    @NotNull
    public String processId;

    @Min(1) @Max(5)
    public int overallSatisfaction;

    @Min(1) @Max(5)
    public int priceSatisfaction;

    @Pattern(regexp = "^[0-9a-zA-Z .]*$")
    public String comments;
}
