<script setup lang="ts">
import type {Product} from "~/types"

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

const isOpinionModalOpen = ref(false)

const showOpinionModal = () => {
  isOpinionModalOpen.value = true
}

const closeOrderDetails = () => {
  isOpinionModalOpen.value = false
}

const route = useRoute()
const isMyPurchases = computed(() => route.path.includes('my/purchases'))
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

        <div v-if="isMyPurchases" class="w-full flex mt-2rem justify-center">
          <button type="submit"
                  class="w-1/2 justify-center rounded-md bg-indigo-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
                  @click="showOpinionModal()"
          >
            Oceń produkt
          </button>
        </div>
      </div>
    </div>
    <opinion-modal v-if="isOpinionModalOpen"
                   @close="closeOrderDetails"
                   :closeModal="closeOrderDetails"
                   :userId="props.user?.id || 0"
                   :productId="props.id"
                   :productName="props.name"
                   :stars="0"
    />
  </div>
</template>
