<script setup lang="ts">
import type {Auction} from "~/types"
import {calculateAuctionTime, calculateAuctionTimeExpired} from "~/utils";

const props = defineProps<Auction>()
const formattedDate = ref('')


onMounted(() => {
  formattedDate.value = calculateAuctionTime(props.endDate);

  const intervalId = setInterval(() => {
    formattedDate.value = calculateAuctionTime(props.endDate);
  }, 1000);

  onUnmounted(() => {
    clearInterval(intervalId);
  });
});

const routeToAuction = () => {
  const router = useRouter()
  router.push(`/auctions/${props.id}`)
}
</script>

<template>
  <div class="mt-2rem">
    <div class="flex mt-1rem text-gray-900 px-8 py-4 bg-gray-100 shadow-xl">
      <div class="w-full">
        <img
            :src="props.photo"
            alt="Główne zdjęcie"
            class=" h-auto w-1/2"
        />
      </div>
      <div class="w-full">
        <div class="grid justify-items-center">
          <p class="text-2xl">{{ props.name }}</p>
        </div>
        <div class="flex mt-2rem justify-between">
          <p class="text-xl italic mr-1rem">Aktualna cena: {{ props.currentPrice || props.startPrice }} zł</p>
          <p class="text-xl font-bold">Do końca aukcji: {{ formattedDate || 'Loading . . .' }}</p>
        </div>
        <div class="flex mt-2rem justify-between">
          <p class="text-xl italic mr-1rem">Kategoria aukcji: {{ props.category.name }}</p>
          <p class="text-xl italic">Aukcjoner: <NuxtLink :to="`/profile/`+ props.owner.id">{{ props.owner.username }}</NuxtLink></p>
        </div>
        <div class="flex mt-2rem justify-between">
          <p class="text-xl italic mr-1rem" v-if="calculateAuctionTimeExpired(props.endDate) > 0 && props.winner?.id">W
            aukcji prowadzi: <NuxtLink :to="`/profile/` + props.winner.id">{{ props.winner?.username }}</NuxtLink></p>
          <p class="text-xl italic mr-1rem" v-else-if="calculateAuctionTimeExpired(props.endDate) > 0">Nikt nie licytuje
            tej aukcji, bądź pierwszy!</p>
          <p class="text-xl italic mr-1rem" v-else-if="props.winner?.id">Aukcje wygrał:<NuxtLink :to="`/profile/` + props.winner.id"> {{ props.winner?.username }}</NuxtLink>
            !</p>
          <p class="text-xl italic mr-1rem" v-else>Aukcja Zakończona, nie wyłoniono zwycięzcy!</p>
        </div>
        <div class="flex mt-2rem justify-between">
          <p class="text-xl italic mr-1rem">{{ props.description }}</p>
        </div>

        <button v-if="formattedDate !== 'Aukcja zakończona'"
                type="button"
                @click="routeToAuction"
                class="inline-flex w-full justify-center rounded-md bg-indigo-600 mt-1rem px-12 py-4 text-lg font-semibold text-white shadow-sm hover:bg-indigo-500 sm:w-auto">
          Przejdź do aukcji
        </button>


      </div>
    </div>
  </div>
</template>
