package com.buylify.buylifyapp.mappers;

import com.buylify.buylifyapp.bid.Bid;
import com.buylify.buylifyapp.bid.BidDto;
import org.mapstruct.Mapper;

@Mapper
public interface BidMapper {

    BidDto bidToBidDto(Bid bid);
}
