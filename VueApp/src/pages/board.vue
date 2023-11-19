<script setup lang="ts">

import {Listbox, ListboxButton, ListboxLabel, ListboxOption, ListboxOptions} from "@headlessui/vue";
import {CheckIcon} from "@heroicons/vue/20/solid";

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

const post = reactive({
  name: '',
  price: '',
  description: '',
  categoryId: selected.value.id,
  photos: '',
})


watch(selected, (newValue) => {
  post.categoryId = newValue.id;
});

const addPost = async () => {
  if (!post.name || !post.price || !post.description) {
    console.error('Wszystkie pola są wymagane')
    return
  }

  const router = useRouter()
  const data = await $fetch('http://localhost:8080/api/products', {
    method: 'POST',
    body: post,
    headers: {Authorization: 'Bearer ' + auth.token}
  }).catch(err => console.error(err.data))
  console.log(post)

  await router.push('/')
}
</script>

<template>
  <div class="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
    <div class="sm:mx-auto sm:w-full sm:max-w-sm">
      <img class="mx-auto h-auto w-1/2" src="../images/logo-text.png" alt="BuyLify"/>
      <label class="">
        <input class="block bg-white color-black p-1rem rounded-3xl max-w-2xl w-full mt-2rem shadow-2xl" value=""
               placeholder="Wpisz cos sobie byku">
      </label>
      <div class="max-w-2xl w-full">
        <button type="submit"
                class="flex justify-center rounded-md bg-indigo-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600 mt-0.5rem ml-auto">
          Wyszukaj
        </button>
      </div>
    </div>
    <div class="sm:mx-auto sm:w-full sm:max-w-sm">
      <form class="space-y-6" action="#" method="POST">
        <div class="text-center mt-2">
          <h2 class="block text-sm font-medium leading-6 text-gray-900">Przedział cenowy</h2>
        </div>
        <div class="flex">
          <div class="mr-1rem">
            <label for="minimal-price" class="block text-sm font-medium leading-6 text-gray-900"></label>
            <div class="">
              <input id="minimal-price" name="minimal-price" type="text"
                     placeholder="od"
                     class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white p-0.5rem"/>
            </div>
          </div>

          <div class="ml-1rem">
            <div class="flex items-center justify-between">
              <label for="maximum-price" class="block text-sm font-medium leading-6 text-gray-900"></label>
            </div>
            <div class="">
              <input id="maximum-price" name="maximum-price" type="text"
                     placeholder="do"
                     class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white p-0.5rem"/>
            </div>
          </div>
        </div>
        <Listbox as="div" v-model="selected">
          <ListboxLabel class="block text-sm font-medium leading-6 text-gray-900">Kategoria</ListboxLabel>
          <div class="mt-2">
            <ListboxButton class="w-full cursor-default rounded-md bg-white py-1.5 pl-3 pr-10 text-left text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 focus:outline-none focus:ring-2 focus:ring-indigo-500 sm:text-sm sm:leading-6">
              <span class="flex items-center">
                <span class="block truncate">{{ selected.name }}</span>
              </span>
            </ListboxButton>

            <transition leave-active-class="transition ease-in duration-100" leave-from-class="opacity-100" leave-to-class="opacity-0">
              <ListboxOptions class="z-10 mt-1 max-h-56 w-full overflow-auto rounded-md bg-white py-1 text-base shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none sm:text-sm">
                <ListboxOption as="template" v-for="category in categories" :key="category.id" :value="category" v-slot="{ active, selected }">
                  <li :class="[active ? 'bg-indigo-600 text-white' : 'text-gray-900', 'relative cursor-default select-none py-2 pl-3 pr-9']">
                    <div class="flex items-center">
                      <span :class="[selected ? 'font-semibold' : 'font-normal', 'block truncate']">{{ category.name }}</span>
                    </div>
                    <span v-if="selected" :class="[active ? 'text-white' : 'text-indigo-600', 'absolute inset-y-0 right-0 flex items-center pr-4']">
                      <CheckIcon class="h-5 w-5" aria-hidden="true" />
                    </span>
                  </li>
                </ListboxOption>
              </ListboxOptions>
            </transition>
          </div>
        </Listbox>
      </form>
    </div>
  </div>
</template>
