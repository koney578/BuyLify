package com.buylify.buylifyapp.mappers;

import com.buylify.buylifyapp.auction.Auction;
import com.buylify.buylifyapp.auction.AuctionDto;
import com.buylify.buylifyapp.auction.CreateAuctionDto;
import org.mapstruct.Mapper;

@Mapper
public interface AuctionMapper {
    Auction toEntity(CreateAuctionDto dto);
    AuctionDto toAuctionDto(Auction auction);
}
