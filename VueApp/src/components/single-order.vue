<script setup lang="ts">
import type {SortedOrder} from "~/types";

const props = defineProps<SortedOrder>()

</script>

<template>
  <div class="mt-2rem">
    <div class="flex mt-1rem text-gray-900 px-8 py-4 bg-orange-200 shadow-xl w-full">
      <div class="text-2xl color-black w-full">
        <p class="text-2xl my-1rem flex">Numer zamówienia: <div class="italic ml-1">{{ props.orderId }}</div></p> <!-- TODO: NAPRAWIC -->
        <p class="text-2xl my-1rem flex">Sprzedawca: <div class="italic ml-1">{{ props.sellerName }}</div></p> <!-- TODO: NAPRAWIC -->
        <p class="text-2xl my-1rem flex">Status: <div class="italic ml-1">{{ props.statusName }}</div></p>
        <single-post v-for="product in props.products" :key="product.id"
                     :id="product.id"
                     :name="product.name"
                     :created-at="product.createdAt"
                     :description="product.description"
                     :photo="product.photo"
                     :price="product.price"
                     :user="product.user"
                     :category="product.category"
                     :count="product.count"
                     class="w-full"
        />

      </div>


    </div>
  </div>
</template>
