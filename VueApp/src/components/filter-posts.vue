<script setup lang="ts">
import type { Category, Product } from "~/types";
import {Listbox, ListboxButton, ListboxLabel, ListboxOption, ListboxOptions} from "@headlessui/vue";
import {CheckIcon} from "@heroicons/vue/20/solid";

const auth = useAuthStore()
const boardProductStore = useBoardProductsStore()

onBeforeRouteLeave(() => {
  boardProductStore.category = 0
})

const noCategory: Category = {
  id: -1,
  name: 'Brak kategorii',
}

const defaultCategory: Category = {
  id: 0,
  name: 'Wszystko',
}

const {data: categories} = await useFetchAPI<Category[]>('/api/categories', {
  headers: {Authorization: 'Bearer ' + auth.token}
})

let allCategories: Category[] = []
if (!auth.isLoggedIn) {
  allCategories = [noCategory]
} else {
  allCategories = [defaultCategory, ...categories.value ?? []]
}

const selected = ref<Category>(allCategories[0])

const searchRestriction = reactive({
  filterName: '',
  priceMin: '',
  priceMax: '',
  categoryId: selected.value.id,
})

watch(selected, (newValue) => {
  searchRestriction.categoryId = newValue.id;
});

const filterPosts = async () => {
  boardProductStore.category = searchRestriction.categoryId
  boardProductStore.minPrice = searchRestriction.priceMin
  boardProductStore.maxPrice = searchRestriction.priceMax
  boardProductStore.name = searchRestriction.filterName
  await boardProductStore.fetchBoardProducts()
}
</script>

<template>
    <div class="sm:mx-auto sm:w-full sm:max-w-sm">
      <form @submit.prevent="filterPosts" class="space-y-6" action="#" method="POST">
        <div class="sm:mx-auto sm:w-full sm:max-w-sm">
          <img class="mx-auto h-auto w-1/2" src="../images/logo-text.png" alt="BuyLify"/>
          <label for="name" class="">
            <input v-model="searchRestriction.filterName"
                   id="name"
                   name="name"
                   type="text"
                   class="block bg-white color-black p-1rem max-w-2xl w-full mt-2rem rounded-3xl shadow-2xl"
                   placeholder="Wpisz frazę ...">
          </label>
          <search-button />
        </div>
        <div class="text-center mt-2">
          <h2 class="block text-sm font-medium leading-6 text-gray-900">Przedział cenowy</h2>
        </div>
        <div class="flex">
          <div class="mr-1rem">
            <label for="minimal-price" class="block text-sm font-medium leading-6 text-gray-900"></label>
            <div class="">
              <input v-model="searchRestriction.priceMin"
                     id="minimal-price"
                     name="minimal-price"
                     type="text"
                     placeholder="od"
                     class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white p-0.5rem"/>
            </div>
          </div>

          <div class="ml-1rem">
            <div class="flex items-center justify-between">
              <label for="maximum-price" class="block text-sm font-medium leading-6 text-gray-900"></label>
            </div>
            <div class="">
              <input v-model="searchRestriction.priceMax"
                     id="maximum-price"
                     name="maximum-price"
                     type="text"
                     placeholder="do"
                     class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white p-0.5rem"/>
            </div>
          </div>
        </div>

        <Listbox as="div" v-model="selected">
          <ListboxLabel class="block text-sm font-medium leading-6 text-gray-900">Kategoria</ListboxLabel>
          <div class="mt-2">
            <ListboxButton
                class="w-full cursor-default rounded-md bg-white py-1.5 pl-3 pr-10 text-left text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 focus:outline-none focus:ring-2 focus:ring-indigo-500 sm:text-sm sm:leading-6">
              <span class="flex items-center">
                <span class="block truncate">{{ selected.name }}</span>
              </span>
            </ListboxButton>

            <transition leave-active-class="transition ease-in duration-100" leave-from-class="opacity-100"
                        leave-to-class="opacity-0">
              <ListboxOptions
                  class="z-10 mt-1 max-h-56 w-full overflow-auto rounded-md bg-white py-1 text-base shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none sm:text-sm">
                <ListboxOption as="template" v-for="category in allCategories" :key="category.id" :value="category"
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
      </form>
    </div>
</template>
