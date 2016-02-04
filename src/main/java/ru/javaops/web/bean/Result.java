package ru.javaops.web.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * GKislin
 * 05.02.2016
 */
public class Result<T> {
    T result;

    boolean success = true;

    public Result(@JsonProperty("result") T result, @JsonProperty("success") boolean success) {
        this.result = result;
        this.success = success;
    }

    public static <T> Result<T> ok(T result) {
        return new Result<>(result, true);
    }

    public static <T> Result<T> fail(T result) {
        return new Result<>(result, false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Result<?> result1 = (Result<?>) o;

        return success == result1.success && (result != null ? result.equals(result1.result) : result1.result == null);
    }

    @Override
    public int hashCode() {
        int result1 = result != null ? result.hashCode() : 0;
        result1 = 31 * result1 + (success ? 1 : 0);
        return result1;
    }
}
