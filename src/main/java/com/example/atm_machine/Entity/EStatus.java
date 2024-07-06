package com.example.atm_machine.Entity;

public enum EStatus {
    C("Completed"), P("Pending"), R("Reject");
    private String status;
    EStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
//    get status by string
    public static EStatus getStatus(String status) {
        return switch (status) {
            case "Completed" -> C;
            case "Pending" -> P;
            case "Reject" -> R;
            default -> null;
        };
    }
}
