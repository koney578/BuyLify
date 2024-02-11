<script setup lang="ts">
import type { Order } from "~/types";
import type { Ref } from "vue";

const props = defineProps<Order>()

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
}

const isOpinionModalOpen = ref(false)
const selectedOrder: Ref<any> = ref<any>('')

const showOpinionModal = (order: Order) => {
  selectedOrder.value = order
  isOpinionModalOpen.value = true
}

const closeOrderDetails = () => {
  isOpinionModalOpen.value = false
}
</script>

<template>
  <div class="mt-2rem">
    <div class="flex mt-1rem text-gray-900 px-8 py-4 bg-gray-100 shadow-xl">
      <div class="w-1/2">
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
          <div class="text-xl mr-2rem flex">Zapłacono: <p class="italic ml-1">{{ props.totalValue }} zł</p></div>
          <div class="text-xl ml-2rem flex">Ilość: <p class="italic ml-1">{{ props.productQuantity }}</p></div>
        </div>

        <div class="flex mt-1rem justify-between">
          <div class="text-xl flex">Opis: <p class="italic ml-1 text-justify">{{ props.product.description }}</p></div>
        </div>

        <div class="flex mt-2rem justify-between">
          <div class="text-xl flex mr-2rem">Dostawa: <p class="italic ml-1">{{ props.deliveryMethod.name }}</p></div>
          <div class="text-xl flex ml-2rem">Płatność: <p class="italic ml-1">{{props.paymentMethod.name}}</p></div>
        </div>

        <div class="flex mt-1rem justify-between">
          <div class="text-xl flex mr-1rem">Status zamówienia: <p class="italic ml-1">{{ props.orderStatus.name }}</p></div>
          <div class="text-xl flex">Kupiono: <p class="italic ml-1">{{ formatDateTime(props.createAt) }}</p></div>
        </div>

        <div class="flex mt-1rem justify-between">
          <div class="text-xl flex mr-1rem">Sprzedawca: <p class="italic ml-1">{{ props.product.user.username }}</p></div>
          <div class="text-xl flex">Kontakt: <p class="italic ml-1">{{ props.product.user.email }}</p></div>
        </div>

        <div class="w-full flex mt-2rem justify-center">
            <button type="submit"
                    class="flex w-1/4 justify-center rounded-md bg-indigo-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
                    @click="showOpinionModal(props)"
            >
              Oceń produkt
            </button>
        </div>
        <opinionModal v-if="isOpinionModalOpen"
                      @close="closeOrderDetails"
                      :order="props"
                      :closeModal="closeOrderDetails"
        />

      </div>
    </div>
  </div>
</template>
