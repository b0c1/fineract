package org.apache.fineract.investor.service.serialization.mapper.investor;

import org.apache.fineract.avro.loan.v1.LoanOwnershipTransferDataV1;
import org.apache.fineract.infrastructure.event.external.service.serialization.mapper.support.AvroMapperConfig;
import org.apache.fineract.investor.data.LoanOwnershipTransferData;
import org.mapstruct.Mapper;

@Mapper(config = AvroMapperConfig.class)
public interface ExternalTransferDataMapper {

    LoanOwnershipTransferDataV1 map(LoanOwnershipTransferData loanOwnershipTransferData);
}
