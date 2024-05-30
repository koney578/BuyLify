<script setup lang="ts">
import type {Auction} from "~/types";
import {calculateAuctionTime, calculateAuctionTimeExpired, formatDateTime} from "~/utils";
import {$fetchAPI} from "~/composables/$fetchApi";

const auth = useAuthStore()
const route = useRoute()

const {data: auction} = await useFetchAPI<Auction>('/api/auctions/' + route.params.id, {
  headers: {Authorization: 'Bearer ' + auth.token},
})

const formattedDate = ref('')
const newPriceError = ref('')
const newPrice = ref((auction.value?.currentPrice || 0) + 1)

const bidAuction = async () => {
  if (auction.value?.currentPrice) {
    if ((newPrice.value <= auction.value?.currentPrice) || newPrice.value <= 0) {
      newPriceError.value = "Nowa cena musi być większa od aktualnej ceny."
      const errorTime = (ms: number) => new Promise(resolve => setTimeout(resolve, ms));
      await errorTime(3000)
      newPriceError.value = ''
      return
    }
  }

  await $fetchAPI('/api/auctions/' + auction.value?.id, {
    method: 'patch',
    body: {
      currentPrice: newPrice.value
    },
    headers: {Authorization: 'Bearer ' + auth.token}
  }).catch(err => console.error(err.data))

  const router = useRouter()
  await router.push('/auctions')
}

onMounted(() => {
  formattedDate.value = calculateAuctionTime(auction.value?.endDate || '');
  if (formattedDate.value === 'Aukcja zakończona') {
    const router = useRouter()
    router.push('/auctions')
  }

  const intervalId = setInterval(() => {
    formattedDate.value = calculateAuctionTime(auction.value?.endDate || '');
  }, 1000);

  onUnmounted(() => {
    clearInterval(intervalId);
  });
});



</script>

<template>
  <div class="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">

    <div class="sm:mx-auto sm:w-full sm:max-w-sm">
      <text-logo/>
      <h1 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">Wybrana aukcja</h1>
    </div>

    <div class="mt-2rem">
      <div class="flex mt-1rem text-gray-900 px-8 py-4 bg-gray-100 shadow-xl">
        <div class="w-1/2">
          <img
              :src="auction?.photo"
              alt="Główne zdjęcie"
              class="h-auto w-3/4"
          />
        </div>
        <div class="w-full relative">

          <div class="grid justify-items-center">
            <p class="text-3xl">{{ auction?.name }}</p>
          </div>

          <div class="flex mt-1rem justify-between">
            <div class="text-xl mr-2rem flex">Aktualana cena: <p class="italic ml-1">{{ auction?.currentPrice }} zł</p>
            </div>
            <div class="text-xl ml-2rem flex">Sprzedawca: <p class="italic ml-1">{{ auction?.owner.username }}</p></div>
          </div>

          <div class="flex mt-1rem justify-between">
            <div class="text-xl flex">Kategoria: <p class="italic ml-1">{{ auction?.category.name }}</p></div>
            <div class="text-xl flex ml-2rem">email sprzedawcy: <p class="italic ml-1">{{ auction?.owner.email }}</p>
            </div>
          </div>

          <div class="flex mt-1rem justify-between">
            <p class="text-xl italic mr-1rem font-bold text-amber-600"
               v-if="calculateAuctionTimeExpired(auction?.endDate || '') > 0 && auction?.winner?.username === auth.user.username">Prowadzisz w tej aukcji!</p>
            <p class="text-xl italic mr-1rem font-bold text-amber-600"
               v-else-if="calculateAuctionTimeExpired(auction?.endDate || '') > 0 && auction?.winner?.id">W
              aukcji prowadzi: {{ auction.winner?.username }}</p>
            <p class="text-xl italic mr-1rem font-bold text-amber-600"
               v-else-if="calculateAuctionTimeExpired(auction?.endDate || '') > 0">Nikt nie licytuje
              tej aukcji, bądź pierwszy!</p>
            <p class="text-xl italic mr-1rem font-bold text-amber-600" v-else-if="auction?.winner?.id">Aukcje wygrał:
              {{ auction?.winner?.username }} !</p>
            <p class="text-xl italic mr-1rem font-bold text-amber-600" v-else>Aukcja Zakończona, nie wyłoniono
              zwycięzcy!</p>
            <p class="text-xl font-bold">Do końca aukcji: {{ formattedDate || 'Loading . . .' }}</p>
          </div>

          <div class="flex mt-1rem justify-between">
            <div/>
            <div v-if="auction?.lastBidDate" class="text-xl flex ml-2rem">Ostatnie podbicie: <p class="italic ml-1">
              {{ formatDateTime(auction?.lastBidDate || '') }}</p></div>
          </div>

          <div class="flex mt-2rem mb-3rem justify-between">
            <div class="text-xl flex">Opis: <p class="italic ml-1 text-justify">{{ auction?.description }}</p></div>
          </div>


          <div v-if="newPriceError" class="font-semibold text-rose-600 flex absolute bottom-8rem w-full">
            <p class="mx-auto">{{ newPriceError }}</p>
          </div>

          <div class="flex mt-2rem justify-center items-center">
            <label for="product-name" class="block text-lg font-medium leading-6 text-gray-900">
              Podbij cenę do:
            </label>

            <div class="ml-1rem relative">
              <div class="pointer-events-none absolute inset-y-0 left-0 flex items-center pl-3">
                <span class="text-gray-500 sm:text-sm">PLN</span>
              </div>
              <input id="new-prince" name="new-prince" type="text"
                     v-model="newPrice"
                     required
                     :placeholder="`${(auction?.currentPrice || 10) + 1}`"
                     class="pl-3rem block w-full rounded-md border-0 py-1.5 px-3 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white"
              />
            </div>
          </div>


          <div class="w-full flex mt-2rem justify-center">
            <button type="submit"
                    class="flex w-1/4 justify-center rounded-md bg-indigo-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
                    @click="bidAuction"
            >
              Licytuj
            </button>
          </div>

        </div>
      </div>
    </div>

  </div>
</template>
