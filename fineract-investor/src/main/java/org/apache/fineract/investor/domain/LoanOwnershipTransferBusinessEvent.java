package org.apache.fineract.investor.domain;

public class LoanOwnershipTransferBusinessEvent extends InvestorBusinessEvent {

    private static final String TYPE = "LoanOwnershipTransferBusinessEvent";

    public LoanOwnershipTransferBusinessEvent(ExternalAssetOwnerTransfer value) {
        super(value);
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
