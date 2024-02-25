<script setup lang="ts">
import type { CartItem } from "~/types"

const props = defineProps<CartItem>()
</script>

<template>
  <div>
    <div class="flex mt-1rem text-gray-900 px-8 py-4 bg-gray-100 shadow-xl">
      <div class="w-1/4">
        <img
            :src="props.product.photo"
            alt="Główne zdjęcie"
            class="h-auto w-3/4"
        />
      </div>
      <div class="w-full">

        <div class="grid justify-items-center">
          <p class="text-3xl">{{ props.product.name }}</p>
        </div>

        <div class="flex mt-1rem justify-between">
          <div class="text-xl flex">Łączna cena: <p class="italic ml-1">{{ (props.product.price * props.quantity).toFixed(2) }} zł</p></div>
          <div class="text-xl flex">Wybrano: <p class="italic ml-1">{{ props.quantity }}</p></div>
        </div>

      </div>
    </div>
  </div>
</template>

