<script setup lang="ts">
import type { Category } from "~/types"
import {PhotoIcon} from "@heroicons/vue/24/solid";
import {Listbox, ListboxButton, ListboxLabel, ListboxOption, ListboxOptions} from '@headlessui/vue'
import {CheckIcon, ChevronUpDownIcon} from '@heroicons/vue/20/solid'
import {$fetchAPI} from "~/composables/$fetchApi";
import VueDatePicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css'


const auth = useAuthStore()
const {data: categories} = await useFetchAPI<Category[]>('/api/categories', {
  headers: {Authorization: 'Bearer ' + auth.token}
});

const noCategory: Category = {
  id: -1,
  name: 'Brak kategorii',
}

const selected = ref<Category>(categories.value?.[0] ?? noCategory)
const auction = reactive({
  name: '',
  startPrice: '',
  description: '',
  endDate: '',
  categoryId: selected.value.id,
  photos: '',
})


watch(selected, (newValue) => {
  auction.categoryId = newValue.id;
});

watchEffect(() => {
  console.log(auction.endDate)
})


let auctionNameError = "";
let auctionPriceError = "";
let auctionDescriptionError = "";

function validateAuctionName() {
  if (auction.name !== "") {
    if (auction.name.length < 3) {
      auctionNameError = "Nazwa musi składać się przynajmniej z 3 znaków!";
      return false;
    } else {
      auctionNameError = "";
      return true;
    }
  } else {
    auctionNameError = "";
  }
}

const pricePattern = /^[0-9]+(\.[0-9]{1,2})?$/;

function validateAuctionStartPrice() {
  if (auction.startPrice === "") {
    auctionPriceError = "";
    return false;
  }
  else if (!pricePattern.test(auction.startPrice)) {
    auctionPriceError = "Zły format ceny! Przykład: 21.15";
    return false;
  } else {
    auctionPriceError = "";
    return true;
  }
}

function validateAuctionDescription() {
  if (auction.description.length > 255) {
    auctionDescriptionError = "Opis musi mieścić się w 255 znakach!";
    return false;
  } else {
    auctionDescriptionError = "";
    return true;
  }
}

watch(auction, () => {
  validateAuctionName();
  validateAuctionDescription();
  validateAuctionStartPrice();
});


const addAuction = async () => {
  if (!auction.name || !auction.startPrice || !auction.description) {
    console.error('Wszystkie pola są wymagane!')
    return
  }

  if (!validateAuctionName()) {
    console.error(auctionNameError)
    return
  }

  if (!validateAuctionStartPrice) {
    console.error(auctionPriceError)
    return
  }

  if (!validateAuctionDescription()) {
    console.error(auctionDescriptionError)
    return
  }

  if (!auction.endDate) {
    console.error('Data zakończenia aukcji jest wymagana!')
    return
  }

  const router = useRouter()
  const blob = new Blob([JSON.stringify(auction)], {type: 'application/json'})
  const formData = new FormData();
  formData.append('post', blob);
  formData.append('file', auction.photos);
  console.log(formData)
  console.log(auction)
  await $fetchAPI('/api/auctions', {
    method: 'POST',
    body: formData,
    headers: {Authorization: 'Bearer ' + auth.token}
  }).catch(err => console.error(err.data))

  await router.push('/auctions')
}

const handleFileChange = (event: any) => {
  auction.photos = event.target.files[0];
};
</script>

<template>

  <div class="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
    <div class="sm:mx-auto sm:w-full sm:max-w-sm">
      <text-logo />
      <h1 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">Dodaj aukcję</h1>
    </div>

    <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
      <form @submit.prevent="addAuction" class="space-y-6" action="#" method="POST">
        <div>
          <label for="auction-name" class="block text-sm font-medium leading-6 text-gray-900">
            Wpisz nazwę aukcji
          </label>
          <div class="mt-2">
            <div v-if="auctionNameError" class="font-semibold text-rose-600">
              {{ auctionNameError }}
            </div>
            <input v-model="auction.name" id="auction-name" name="auction-name" type="text" autocomplete="auction-name"
                   required
                   placeholder="Nazwa Twojej aukcji"
                   class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white p-0.5rem"/>
          </div>
        </div>

        <Listbox as="div" v-model="selected">
          <ListboxLabel class="block text-sm font-medium leading-6 text-gray-900">Kategoria</ListboxLabel>
          <div class="relative mt-2">
            <ListboxButton
                class=" relative w-full cursor-default rounded-md bg-white py-1.5 pl-3 pr-10 text-left text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 focus:outline-none focus:ring-2 focus:ring-indigo-500 sm:text-sm sm:leading-6">
              <span class="flex items-center">
                <span class="block truncate">{{ selected.name }}</span>
              </span>
              <span class="pointer-events-none absolute inset-y-0 right-0 ml-3 flex items-center pr-2">
                <ChevronUpDownIcon class="h-5 w-5 text-gray-400" aria-hidden="true"/>
              </span>
            </ListboxButton>

            <transition leave-active-class="transition ease-in duration-100" leave-from-class="opacity-100"
                        leave-to-class="opacity-0">
              <ListboxOptions
                  class="absolute z-10 mt-1 max-h-56 w-full overflow-auto rounded-md bg-white py-1 text-base shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none sm:text-sm">
                <option disabled value="">Wybierz kategorię</option>
                <ListboxOption as="template" v-for="category in categories" :value="category" :key="category.id"
                               v-slot="{ active, selected }">
                  <li :class="[active ? 'bg-indigo-600 text-white' : 'text-gray-900', 'relative cursor-default select-none py-2 pl-3 pr-9']">
                    <div class="flex items-center">
                      <span :class="[selected ? 'font-semibold' : 'font-normal', 'block truncate']">{{ category.name }}</span>
                    </div>
                    <span v-if="selected"
                          :class="[active ? 'text-white' : 'text-indigo-600', 'absolute inset-y-0 right-0 flex items-center pr-4']">
                      <CheckIcon class="h-5 w-5" aria-hidden="true"/>
                    </span>
                  </li>
                </ListboxOption>
              </ListboxOptions>
            </transition>
          </div>
        </Listbox>

        <div>
          <label for="price" class="block text-sm font-medium leading-6 text-gray-900">Cena wywoławcza</label>
          <div v-if="auctionPriceError" class="font-semibold text-rose-600 mt-2">
            {{ auctionPriceError }}
          </div>
          <div class="relative mt-2 rounded-md shadow-sm">
            <div class="pointer-events-none absolute inset-y-0 left-0 flex items-center pl-3">
              <span class="text-gray-500 sm:text-sm">PLN</span>
            </div>
            <input v-model="auction.startPrice" type="text" name="price" id="price"
                   class="block w-full rounded-md border-0 py-1.5 pl-3rem pr-20 text-gray-900 ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white"
                   placeholder="0.00"
            />
          </div>
        </div>

        <div>
          <div class="flex items-center justify-between">
            <label for="description" class="block text-sm font-medium leading-6 text-gray-900">Opis</label>
          </div>
          <div class="mt-2">
            <div v-if="auctionDescriptionError" class="font-semibold text-rose-600">
              {{ auctionDescriptionError }}
            </div>
            <textarea v-model="auction.description" id="description" name="description" type="text"
                      autocomplete="description"
                      required
                      placeholder="Dodaj opis Twojego ogłoszenia"
                      class="h-[auto] block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white p-0.5rem"/>
          </div>
        </div>

        <VueDatePicker v-model="auction.endDate" model-type="iso" format="dd/MM/yyyy, HH:mm" locale="pl-PL" select-text="Potwierdź" cancel-text="Anuluj" :min-date="new Date().toISOString()" placeholder="Wybierz datę końca aukcji"></VueDatePicker>

        <div>
          <div class="items-center justify-between">
            <div class="mt-2 flex justify-center rounded-lg border border-dashed border-gray-900/25 px-6 py-10">
              <div class="text-center">
                <PhotoIcon class="mx-auto h-12 w-12 text-gray-300" aria-hidden="true"/>
                <div class="mt-4 flex text-sm leading-6 text-gray-600">
                  <label for="photos"
                         class="cursor-pointer rounded-md bg-white font-semibold text-indigo-600 focus-within:outline-none focus-within:ring-2 focus-within:ring-indigo-600 focus-within:ring-offset-2 hover:text-indigo-500">
                    <span>Wybierz plik</span>
                    <input v-on:change="handleFileChange" id="photos" name="photos" type="file" class="sr-only"/>
                  </label>
                  <p class="pl-1">lub przenieś</p>
                </div>
                <p class="text-xs leading-5 text-gray-600">PNG, JPG, GIF do 10MB</p>
              </div>
            </div>

          </div>
        </div>
        <div v-if="!!auction.photos" class="flex">
          <p class="mx-auto font-bold text-indigo-600">Zdjęcie {{ auction.photos?.name }} zostało wgrane prawidłowo</p>
        </div>
        <submit-button/>

      </form>

    </div>
  </div>
</template>
