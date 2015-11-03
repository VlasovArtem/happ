package com.household.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * Created by artemvlasov on 04/10/15.
 */
@JsonAutoDetect
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
