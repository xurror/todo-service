package jolly.roger.todoService.domain;

public enum Status {
    INVALID(0, "invalid"),
    TODO(1, "not done"),
    DONE(2, "done"),
    OVERDUE(3, "past due");

    private final int code;
    private final String value;

    Status(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public static Status fromInt(final Integer code) {
        return switch (code) {
            case 1 -> TODO;
            case 2 -> DONE;
            case 3 -> OVERDUE;
            default -> INVALID;
        };
    }

    public Integer getCode() {
        return code;
    }
}
