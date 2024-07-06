package com.atm_machine.Entity;

public enum ETransactionType {
    D("Deposit"), W("Withdrawal"), T("Transfer");
    private final String type;
    ETransactionType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
//    get transaction type by string
    public static ETransactionType fromString(String type) {
        return switch (type) {
            case "Deposit" -> D;
            case "Withdrawal" -> W;
            case "Transfer" -> T;
            default -> null;
        };
    }
}
