<script setup lang="ts">

// import VueDatePicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css'
import {CheckIcon, ChevronUpDownIcon} from "@heroicons/vue/20/solid";
import {Listbox, ListboxButton, ListboxLabel, ListboxOption, ListboxOptions} from "@headlessui/vue";
import type {Category} from "~/types"
import {$fetchAPI} from "~/composables/$fetchApi";

const editProductStore = useEditProductStore()
const auth = useAuthStore()
const product = reactive({
  id: editProductStore.product?.id,
  name: editProductStore.product?.name,
  price: editProductStore.product?.price,
  count: editProductStore.product?.count,
  description: editProductStore.product?.description,
  categoryId: editProductStore.product?.category.id,
  createdAt: editProductStore.product?.createdAt,
  photo: editProductStore.product?.photo,
})

const {data: categories} = await useFetchAPI<Category[]>('/api/categories', {
  headers: {Authorization: 'Bearer ' + auth.token}
});

const noCategory: Category = {
  id: -1,
  name: 'Brak kategorii',
}

const selected = ref<Category>(editProductStore.product?.category ?? noCategory)
const changedProduct = reactive({
  id: product?.id || -1,
  name: product?.name,
  price: (product?.price || '').toString(),
  count: (product?.count || '').toString(),
  description: product?.description,
  categoryId: product?.categoryId || noCategory.id,
  photo: product?.photo,
  modifiedAt: '',
  createdAt: product?.createdAt,
})

watch(selected, () => {
  changedProduct.categoryId = selected.value.id
})

const countPattern = /^[0-9]+$/;
const pricePattern = /^[0-9]+(\.[0-9]{1,2})?$/;

const editProduct = async () => {
  if (changedProduct.name === '') {
    return
  }
  if (changedProduct.categoryId === -1) {
    return
  }
  if (changedProduct.count === '' || !countPattern.test(changedProduct.count)) {
    return
  }
  if (changedProduct.price === '' || !pricePattern.test(changedProduct.price)) {
    return
  }

  const currentDate = new Date();
  const year = currentDate.getFullYear();
  const month = String(currentDate.getMonth() + 1).padStart(2, '0');
  const day = String(currentDate.getDate()).padStart(2, '0');
  const hours = String(currentDate.getHours()).padStart(2, '0');
  const minutes = String(currentDate.getMinutes()).padStart(2, '0');
  const seconds = String(currentDate.getSeconds()).padStart(2, '0');
  const milliseconds = String(currentDate.getMilliseconds()).padStart(3, '0');
  changedProduct.modifiedAt = `${year}-${month}-${day}T${hours}:${minutes}:${seconds}.${milliseconds}`

  await $fetchAPI('/api/products/' + changedProduct.id, {
    method: 'PUT',
    body: changedProduct,
    headers: {Authorization: 'Bearer ' + auth.token}
  }).catch(err => console.error(err.data))

  const router = useRouter()
  await router.push('/my-profile/sales')
}

const deleteProduct = async () => {
  const router = useRouter()
  await $fetchAPI('/api/products/' + changedProduct.id + '/deactivate', {
    method: 'PUT',
    headers: {Authorization: 'Bearer ' + auth.token}
  }).then(() => {
    router.push('/my-profile/sales')
  }).catch(err => console.error(err.data))

}

const deleteVisible = ref(false)
const deleteButtonClicked = () => {
  deleteVisible.value = !deleteVisible.value
}
</script>

<template>
  <div class="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
    <div class="sm:mx-auto sm:w-full sm:max-w-sm">
      <img class="mx-auto h-auto w-1/2" src="~/images/logo-text.png" alt="BuyLify"/>
      <h1 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">Możesz edytować swoje
        ogłoszenie.</h1>
    </div>

    <div class="mt-2rem">
      <div class="flex mt-1rem text-gray-900 px-8 py-4 bg-gray-100 shadow-xl">
        <div class="w-2/3">
          <div class="w-1/2">
            <img
                :src="product?.photo"
                alt="Główne zdjęcie"
                class="h-auto"
            />
            <div class="my-2rem">
              <button type="button" @click="deleteButtonClicked"
                      class="flex justify-center rounded-md bg-rose-600 px-3 py-1.5 sm:mx-auto sm:w-full sm:max-w-sm text-sm font-semibold leading-6 text-white shadow-sm hover:bg-rose-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">
                Usuń ogłoszenie
              </button>
            </div>

            <div v-if="deleteVisible" class="mt-2rem">
              <form @submit.prevent="deleteProduct">
                <button type="submit"
                        class="flex justify-center rounded-md bg-rose-700 sm:mx-auto sm:w-full sm:max-w-xs px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-rose-600 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">
                  Potwierdź
                </button>
              </form>
            </div>
          </div>
        </div>


        <form @submit.prevent="editProduct" method="POST" class="w-1/2">
          <div class="w-full">
            <div class="justify-between mt-2rem">
              <div>
                <label for="product-name" class="block text-sm font-medium leading-6 text-gray-900">
                  Nazwa ogłoszenia:
                </label>
                <div class="mt-2">
                  <input v-model="changedProduct.name" id="product-name" name="product-name" type="text"
                         autocomplete="product-name"
                         required
                         placeholder="Nazwa"
                         class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white p-0.5rem"/>
                </div>
              </div>


              <Listbox as="div" v-model="selected">
                <ListboxLabel class="block text-sm font-medium leading-6 text-gray-900 mt-4">Kategoria</ListboxLabel>
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
                            <span :class="[selected ? 'font-semibold' : 'font-normal', 'block truncate']">
                              {{ category.name }}
                            </span>
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
                <label for="product-count" class="block mt-4 text-sm font-medium leading-6 text-gray-900">
                  Ilość produktu:
                </label>
                <div class="mt-2">
                  <input v-model="changedProduct.count" id="product-count" name="product-count" type="text"
                         autocomplete="product-count"
                         required
                         placeholder="10"
                         class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white p-0.5rem"/>
                </div>
              </div>


              <div>
                <label for="product-price" class="block mt-4 text-sm font-medium leading-6 text-gray-900">
                  Cena za produkt:
                </label>
                <div class="mt-2">
                  <input v-model="changedProduct.price" id="product-price" name="product-price" type="text"
                         autocomplete="product-price"
                         required
                         placeholder="9.99"
                         class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white p-0.5rem"/>
                </div>
              </div>


              <div>
                <div class="flex items-center justify-between">
                  <label for="product-description"
                         class="block mt-4 text-sm font-medium leading-6 text-gray-900">Opis</label>
                </div>
                <div class="mt-2">
                    <textarea v-model="changedProduct.description" id="product-description" name="product-description"
                              type="text"
                              autocomplete="product-description"
                              required
                              placeholder="Dodaj opis Twojego ogłoszenia"
                              class="h-[auto] block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white p-0.5rem"/>
                </div>
              </div>


              <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
                <div class="space-y-6">
                  <submit-button/>
                </div>

                <p class="mt-10 text-center text-sm text-gray-500">
                  Chcesz porzucić edycję?
                  {{ ' ' }}
                  <NuxtLink
                      to="/my-profile/sales" class="font-semibold leading-6 text-indigo-600 hover:text-indigo-500">Kliknij
                    tutaj
                    by wrócić!
                  </NuxtLink>
                </p>
              </div>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>
