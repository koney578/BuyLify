<script setup lang="ts">
import type {Auction} from "~/types"

const auth = useAuthStore()

const {data: auctions} = await useFetchAPI<Auction[]>('/api/auctions', {
  headers: {Authorization: 'Bearer ' + auth.token}
})
</script>

<template>
  <div class="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">

    <div class="sm:mx-auto sm:w-full sm:max-w-sm">
      <img class="mx-auto h-auto w-1/2" src="../images/logo-text.png" alt="BuyLify"/>
      <h1 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">Aukcje</h1>
    </div>
    <single-auction v-for="auction in auctions"
                    :key="auction.id"
                    :id="auction.id"
                    :name="auction.name"
                    :currentPrice="auction.currentPrice"
                    :createdAt="auction.createdAt"
                    :photo="auction.photo"
                    :category="auction.category"
                    :description="auction.description"
                    :owner="auction.owner"
                    :count="auction.count"
                    :end-date="auction.endDate"
                    :last-bid-date="auction.lastBidDate"
                    :start-price="auction.startPrice"
                    :winner="auction.winner"
    />


  </div>
</template>
