package myuan;

public enum Operation {
    PUSH,
    POP,
    CLEAR,
    ADD,
    MUL,
    NEG,
    INV,
    UNDO,
    PRINT,
    QUIT,
    UNKNOWN;

    public static Operation get(String operation) {
        try {
            return Operation.valueOf(operation.toUpperCase());
        } catch (Exception ex) {
            return UNKNOWN;
        }
    }
}
