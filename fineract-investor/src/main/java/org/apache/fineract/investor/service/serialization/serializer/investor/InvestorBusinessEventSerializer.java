package org.apache.fineract.investor.service.serialization.serializer.investor;

import lombok.RequiredArgsConstructor;
import org.apache.avro.generic.GenericContainer;
import org.apache.fineract.avro.generator.ByteBufferSerializable;
import org.apache.fineract.avro.loan.v1.LoanOwnershipTransferDataV1;
import org.apache.fineract.infrastructure.event.business.domain.BusinessEvent;
import org.apache.fineract.infrastructure.event.external.service.serialization.serializer.BusinessEventSerializer;
import org.apache.fineract.investor.data.ExternalTransferData;
import org.apache.fineract.investor.data.ExternalTransferStatus;
import org.apache.fineract.investor.data.LoanOwnershipTransferData;
import org.apache.fineract.investor.domain.InvestorBusinessEvent;
import org.apache.fineract.investor.service.ExternalAssetOwnersReadService;
import org.apache.fineract.investor.service.serialization.mapper.investor.ExternalTransferDataMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InvestorBusinessEventSerializer implements BusinessEventSerializer {

    private final ExternalTransferDataMapper mapper;
    private final ExternalAssetOwnersReadService externalAssetOwnersReadService;

    @Override
    public <T> boolean canSerialize(BusinessEvent<T> event) {
        return event instanceof InvestorBusinessEvent;
    }

    public Class<? extends GenericContainer> getSupportedSchema() {
        return LoanOwnershipTransferDataV1.class;
    }

    @Override
    public <T> ByteBufferSerializable toAvroDTO(BusinessEvent<T> rawEvent) {
        InvestorBusinessEvent event = (InvestorBusinessEvent) rawEvent;
        Long externalTransferId = event.get().getId();
        ExternalTransferData transferData = externalAssetOwnersReadService.retrieveTransferData(externalTransferId);

        LoanOwnershipTransferData ownershipData = LoanOwnershipTransferData.builder().loanId(transferData.getLoan().getLoanId())
                .loanExternalId(transferData.getLoan().getExternalId()).transferExternalId(transferData.getTransferExternalId())
                .type(transferData.getStatus() == ExternalTransferStatus.BUYBACK ? "BUYBACK" : "SALE")
                .purchasePriceRatio(transferData.getPurchasePriceRatio()).settlementDate(transferData.getSettlementDate()).build();
        return mapper.map(ownershipData);
    }
}
