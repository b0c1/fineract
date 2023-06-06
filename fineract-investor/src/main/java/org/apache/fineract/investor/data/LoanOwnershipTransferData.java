package org.apache.fineract.investor.data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.apache.fineract.organisation.monetary.data.CurrencyData;
import org.apache.fineract.portfolio.loanaccount.data.LoanChargePaidByData;

@Getter
@Builder
@AllArgsConstructor
public class LoanOwnershipTransferData {

    private final Long loanId;
    private final String loanExternalId;
    private final String type;
    private final String transferExternalId;
    private final LocalDate submittedDate;
    private final String assetOwnerExternalId;
    private final CurrencyData currency;
    private final BigDecimal totalOutstandingBalanceAmount;
    private final BigDecimal outstandingPrincipalPortion;
    private final BigDecimal outstandingFeePortion;
    private final BigDecimal outstandingPenaltyPortion;
    private final BigDecimal outstandingInterestPortion;
    private final BigDecimal overPaymentPortion;
    private final BigDecimal unrecognizedIncomePortion;
    private final String purchasePriceRatio;
    private final LocalDate settlementDate;
    private final String transferStatus;
    private final String transferStatusReason;
    private final LocalDate businessDate;
    private Collection<LoanChargePaidByData> loanChargePaidByList;

    public void setLoanChargePaidByList(Collection<LoanChargePaidByData> loanChargePaidByList) {
        this.loanChargePaidByList = loanChargePaidByList;
    }
}
