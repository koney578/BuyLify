<script setup lang="ts">
import type { Product } from "~/types"

const props = defineProps<Product>()

const formatDateTime = (dateTimeString: string) => {
  const options: Intl.DateTimeFormatOptions = {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit'
  };
  const dateTime = new Date(dateTimeString);
  return dateTime.toLocaleString('pl-PL', options);
};
</script>

<template>
  <div class="mt-2rem w-full">
    <div class="flex mt-1rem text-gray-900 px-8 py-4 bg-gray-100 shadow-xl w-full">
      <div class="w-full flex">
        <img
            :src="props.photo"
            alt="Główne zdjęcie"
            class="object-cover h-64 w-64"
        />
      </div>
      <div class="w-full">
        <div class="grid justify-items-center">
          <p class="text-2xl">{{ props.name }}</p>
        </div>
        <div class="flex mt-2rem justify-between">
          <p class="text-xl italic mr-1rem">{{ props.price }} zł</p>
          <p>Data dodania: {{ formatDateTime(props.createdAt) }}</p>
        </div>
      </div>
    </div>
  </div>
</template>
