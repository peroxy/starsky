package com.starsky.backend.api.schedule.shift;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import java.time.Instant;

public class ScheduleShiftResponse {
    @Schema(example = "1")
    @NotNull
    private final Long id;
    @JsonProperty("shift_start")
    @Schema(example = "1617032176.7171679", title = "Epoch timestamp of shift start", implementation = Double.class)
    @NotNull
    private final Instant shiftStart;
    @JsonProperty("shift_end")
    @Schema(example = "1617052176.7171679", title = "Epoch timestamp of shift end", implementation = Double.class)
    @NotNull
    private final Instant shiftEnd;
    @JsonProperty("number_of_required_employees")
    @Schema(example = "5", title = "Number of required employees for the entire shift")
    @NotNull
    private final int numberOfRequiredEmployees;

    public ScheduleShiftResponse(Long id, Instant shiftStart, Instant shiftEnd, int numberOfRequiredEmployees) {
        this.id = id;
        this.shiftStart = shiftStart;
        this.shiftEnd = shiftEnd;
        this.numberOfRequiredEmployees = numberOfRequiredEmployees;
    }

    public Long getId() {
        return id;
    }

    public Instant getShiftStart() {
        return shiftStart;
    }

    public Instant getShiftEnd() {
        return shiftEnd;
    }

    public int getNumberOfRequiredEmployees() {
        return numberOfRequiredEmployees;
    }
}
