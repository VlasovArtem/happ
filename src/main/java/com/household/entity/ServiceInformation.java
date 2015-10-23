package com.household.entity;

/**
 * Created by artemvlasov on 04/10/15.
 */
public class ServiceInformation {
    private int bankCode;
    private long checkingAccount;

    public ServiceInformation() {
    }

    public ServiceInformation(int bankCode, long checkingAccount) {
        this.bankCode = bankCode;
        this.checkingAccount = checkingAccount;
    }

    public int getBankCode() {
        return bankCode;
    }

    public void setBankCode(int bankCode) {
        this.bankCode = bankCode;
    }

    public long getCheckingAccount() {
        return checkingAccount;
    }

    public void setCheckingAccount(long checkingAccount) {
        this.checkingAccount = checkingAccount;
    }
}
