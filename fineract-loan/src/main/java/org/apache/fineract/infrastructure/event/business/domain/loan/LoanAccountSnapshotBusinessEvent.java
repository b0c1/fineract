package org.apache.fineract.infrastructure.event.business.domain.loan;

import org.apache.fineract.portfolio.loanaccount.domain.Loan;

public class LoanAccountSnapshotBusinessEvent extends LoanBusinessEvent {

    private static final String TYPE = "LoanAccountSnapshotBusinessEvent";

    public LoanAccountSnapshotBusinessEvent(Loan value) {
        super(value);
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
