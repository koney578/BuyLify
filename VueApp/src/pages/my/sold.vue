<script setup lang="ts">
import type {SoldProduct} from "~/types";
import {sortAndGroupSoldProducts} from "~/utils";

const auth = useAuthStore()

const {data: soldProducts} = await useFetchAPI<SoldProduct[]>('/api/products/sold', {
  headers: {Authorization: 'Bearer ' + auth.token},
})
const sortedSoldProducts = ref()
watchEffect(() => {
  if (soldProducts.value) {
    sortedSoldProducts.value = sortAndGroupSoldProducts(soldProducts.value)
    // console.log(sortedSoldProducts.value)
  }
})
</script>

<template>
  <div class="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
    <div class="sm:mx-auto sm:w-full sm:max-w-sm">
      <text-logo />
      <h1 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">Sprzedane produkty</h1>
    </div>
    <div class="sm:mx-auto sm:w-full sm:max-w-6xl">
      <single-sold v-for="product in sortedSoldProducts" :order-id="product.orderId" :products="product.products" :address="product.address" :status-id="product.statusId" />

    </div>
  </div>
</template>
