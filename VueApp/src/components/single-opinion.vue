<script setup lang="ts">
import type {Opinion} from "~/types"
import {formatDateTime} from "~/utils";

const props = defineProps<Opinion>()
</script>

<template>
  <div class="bg-gray-100 sm:mx-2rem md:mx-10rem lg:mx-15rem p-8 text-black text-2xl rounded-2xl shadow-md flex">

    <div class="w-full flex">
      <img
          :src="props.product.photo"
          alt="Główne zdjęcie"
          class="object-cover h-64 w-64"
      />
    </div>
    <div class="w-full">
      <div class="grid justify-items-center">
        <p class="text-2xl">{{ props.product.name }}</p>
      </div>

      <div class="mt-2rem justify-between">
        <div class="flex items-center">
          <Icon name="heroicons:star" v-for="rating in 5" :key="rating"
                :class="[props.stars + 1 > rating ? 'text-yellow-500' : 'text-gray-200', 'h-5 w-5 flex-shrink-0']"
                aria-hidden="true"
          />
        </div>
        <p class="text-xl italic mt-1rem mr-1rem">Komentarz: {{ props.description }}</p>
      </div>
      <p class="text-xl mt-1rem">Opinię wystawił: <NuxtLink :to="`/profile/` + props.userId">{{ props.username }}</NuxtLink></p>
      <p class="text-xl mt-1rem">Data wystawienia: {{ formatDateTime(props.createdAt) }}</p>
    </div>
  </div>

</template>
