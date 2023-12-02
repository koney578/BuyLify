<script setup lang="ts">

import {PhotoIcon} from "@heroicons/vue/24/solid";
import {Listbox, ListboxButton, ListboxLabel, ListboxOption, ListboxOptions} from '@headlessui/vue'
import {CheckIcon, ChevronUpDownIcon} from '@heroicons/vue/20/solid'
import {useAuthStore} from "~/stores/auth";

const auth = useAuthStore()
const {data: categories} = await useFetch<Category[]>('http://localhost:8080/api/categories', {
  headers: {Authorization: 'Bearer ' + auth.token}
});

interface Category {
  id: number,
  name: string,
}

const noCategory: Category = {
  id: -1,
  name: 'Brak kategorii',
}

const selected = ref<Category>(categories.value?.[0] ?? noCategory)

const product = reactive({
  name: '',
  price: '',
  count: '',
  description: '',
  categoryId: selected.value.id,
  photos: '',
})


watch(selected, (newValue) => {
  product.categoryId = newValue.id;
});

let productNameError = "";
let productPriceError = "";
let productCountError = "";
let productDescriptionError = "";


function validateProductName() {
  if (product.name !== "") {
    if (product.name.length < 3) {
      productNameError = "Nazwa musi składać się przynajmniej z 3 znaków!";
      return false;
    } else {
      productNameError = "";
      return true;
    }
  } else {
    productNameError = "";
  }
}

const pricePattern = /^[0-9]+(\.[0-9]{1,2})?$/;

function validateProductPrice() {
  if (product.price === "") {
    productPriceError = "";
    return false;
  }
  else if (!pricePattern.test(product.price)) {
    productPriceError = "Zły format ceny! Przykład: 21.15";
    return false;
  } else {
    productPriceError = "";
    return true;
  }
}

const countPattern = /^[0-9]+$/;


function validateProductCount() {
  if (product.count === "") {
    productCountError = "";
    return false;
  }
  else if (!countPattern.test(product.count)) {
    productCountError = "Nie poprawna wartość!";
    return false;
  } else {
    productCountError = "";
    return true;
  }
}

function validateProductDescription() {
  if (product.description.length > 500) {
    productDescriptionError = "Opis musi mieścić się w 500 znakach!";
    return false;
  } else {
    productDescriptionError = "";
    return true;
  }
}

watch(product, () => {
  validateProductName();
  validateProductDescription();
  validateProductCount();
  validateProductPrice();
});


const addProduct = async () => {
  if (!product.name || !product.price || !product.description) {
    console.error('Wszystkie pola są wymagane!')
    return
  }

  if (!validateProductName()) {
    console.error(productNameError)
    return
  }

  if (!validateProductPrice) {
    console.error(productPriceError)
    return
  }

  if (!validateProductCount()) {
    console.error(productCountError)
    return
  }

  if (!validateProductDescription()) {
    console.error(productDescriptionError)
    return
  }

  const router = useRouter()
  const blob = new Blob([JSON.stringify(product)], {type: 'application/json'})
  const formData = new FormData();
  formData.append('post', blob);
  formData.append('file', product.photos);
  const data = await $fetch('http://localhost:8080/api/products', {
    method: 'POST',
    body: formData,
    headers: {Authorization: 'Bearer ' + auth.token}
  }).catch(err => console.error(err.data))

  await router.push('/board')
}

const handleFileChange = (event) => {
  const file = event.target.files[0];
  product.photos = file;
};
</script>

<template>
  <div class="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
    <div class="sm:mx-auto sm:w-full sm:max-w-sm">
      <img class="mx-auto h-auto w-1/2" src="../images/logo-text.png" alt="BuyLify"/>
      <h1 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">Dodaj ogłoszenie</h1>
    </div>

    <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
      <form @submit.prevent="addProduct" class="space-y-6" action="#" method="POST">
        <div>
          <label for="post-name" class="block text-sm font-medium leading-6 text-gray-900">
            Wpisz nazwę ogłoszenia
          </label>
          <div class="mt-2">
            <div v-if="productNameError" class="font-semibold text-rose-600">
              {{ productNameError }}
            </div>
            <input v-model="product.name" id="post-name" name="post-name" type="text" autocomplete="post-name"
                   required=""
                   placeholder="Nazwa Twojego ogłoszenia"
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
          <label for="count" class="block text-sm font-medium leading-6 text-gray-900">Ilość</label>
          <div v-if="productCountError" class="font-semibold text-rose-600">
            {{ productCountError }}
          </div>
          <div class="mt-2 rounded-md shadow-sm">
            <input v-model="product.count" type="text" name="count" id="count"
                   class="block w-full rounded-md border-0 text-gray-900 ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white p-0.5rem"
                   placeholder="0"
            />
          </div>
        </div>


        <div>
          <label for="price" class="block text-sm font-medium leading-6 text-gray-900">Cena</label>
          <div v-if="productPriceError" class="font-semibold text-rose-600 mt-2">
            {{ productPriceError }}
          </div>
          <div class="relative mt-2 rounded-md shadow-sm">
            <div class="pointer-events-none absolute inset-y-0 left-0 flex items-center pl-3">
              <span class="text-gray-500 sm:text-sm">$</span>
            </div>
            <input v-model="product.price" type="text" name="price" id="price"
                   class="block w-full rounded-md border-0 py-1.5 pl-7 pr-20 text-gray-900 ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white"
                   placeholder="0.00"
            />
            <div class="absolute inset-y-0 right-0 flex items-center">
              <label for="currency" class="sr-only">waluta</label>
              <select id="currency" name="currency"
                      class="h-full rounded-md border-0 bg-transparent py-0 pl-2 pr-7 text-gray-500 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm">
                <option>PLN</option>
                <option>USD</option>
                <option>EUR</option>
              </select>
            </div>
          </div>
        </div>


        <div>
          <div class="flex items-center justify-between">
            <label for="description" class="block text-sm font-medium leading-6 text-gray-900">Opis</label>
          </div>
          <div class="mt-2">
            <div v-if="productDescriptionError" class="font-semibold text-rose-600">
              {{ productDescriptionError }}
            </div>
            <textarea v-model="product.description" id="description" name="description" type="text"
                      autocomplete="description"
                      required=""
                      placeholder="Dodaj opis Twojego ogłoszenia"
                      class="h-[auto] block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white p-0.5rem"/>
          </div>
        </div>


        <div>
          <div class="items-center justify-between">
            <div class="mt-2 flex justify-center rounded-lg border border-dashed border-gray-900/25 px-6 py-10">
              <div class="text-center">
                <PhotoIcon class="mx-auto h-12 w-12 text-gray-300" aria-hidden="true"/>
                <div class="mt-4 flex text-sm leading-6 text-gray-600">
                  <label for="photos"
                         class="cursor-pointer rounded-md bg-white font-semibold text-indigo-600 focus-within:outline-none focus-within:ring-2 focus-within:ring-indigo-600 focus-within:ring-offset-2 hover:text-indigo-500">
                    <span>Wybierz plik</span>
<!--                    <input v-on:change="post.photos" id="photos" name="photos" type="file" class="sr-only"/>-->
                    <input v-on:change="handleFileChange" id="photos" name="photos" type="file" class="sr-only"/>

                  </label>
                  <p class="pl-1">lub przenieś</p>
                </div>
                <p class="text-xs leading-5 text-gray-600">PNG, JPG, GIF do 10MB</p>
              </div>
            </div>
          </div>
        </div>


        <div>
          <button type="submit"
                  class="flex w-full justify-center rounded-md bg-indigo-600  px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">
            Potwierdź
          </button>
        </div>

      </form>

    </div>
  </div>
</template>
