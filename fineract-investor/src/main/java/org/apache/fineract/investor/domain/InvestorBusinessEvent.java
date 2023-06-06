package org.apache.fineract.investor.domain;

import org.apache.fineract.infrastructure.event.business.domain.AbstractBusinessEvent;

public abstract class InvestorBusinessEvent extends AbstractBusinessEvent<ExternalAssetOwnerTransfer> {

    private static final String CATEGORY = "Investor";

    public InvestorBusinessEvent(ExternalAssetOwnerTransfer value) {
        super(value);
    }

    @Override
    public String getCategory() {
        return CATEGORY;
    }

    @Override
    public Long getAggregateRootId() {
        return get().getId();
    }
}
