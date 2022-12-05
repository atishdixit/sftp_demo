package com.cb.dto;

import java.util.function.Function;

public class MapToItem implements Function<String, DomainDetails> {

    @Override
    public DomainDetails apply(String line) {
        DomainDetails domainDetails =  new DomainDetails();
        String cbValue[] = line.split(",");
        domainDetails.setPovoID(cbValue[0]);
        domainDetails.setPovoTel(cbValue[1]);
        domainDetails.setPovoEmailAddr(cbValue[2]);
        return domainDetails;
    }
}
