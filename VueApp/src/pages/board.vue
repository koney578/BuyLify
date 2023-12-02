<script setup lang="ts">

import {Listbox, ListboxButton, ListboxLabel, ListboxOption, ListboxOptions} from "@headlessui/vue";
import {CheckIcon} from "@heroicons/vue/20/solid";

import {useAuthStore} from "~/stores/auth";
import SinglePost from "~/components/singlePost.vue";
import ProductQuickView from "~/components/ProductQuickView.vue";

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

const searchRestriction = reactive({
  filterName: '',
  priceMin: '',
  priceMax: '',
  categoryId: selected.value.id,
})

watch(selected, (newValue) => {
  searchRestriction.categoryId = newValue.id;
});

const {data: products} = await useFetch<Product[]>('http://localhost:8080/api/products', {
  headers: {Authorization: 'Bearer ' + auth.token}
});

interface Product {
  id: number,
  name: string,
  price: number,
  count: number,
  description: string,
  photo: string,
  category: Category,
  createdAt: Date,
}

const defaultProduct = {
  id: 0,
  name: "",
  price: 0,
  count: 0,
  description: "",
  category: noCategory,
  createdAt: new Date(),
  photo: '',
}

const isProductDetailsOpen = ref(false)
const selectedProduct: Ref<Product> = ref<Product>(defaultProduct)


const showProductDetails = (product: Product) => {
  selectedProduct.value = product
  isProductDetailsOpen.value = true
}

const closeProductDetails = () => {
  isProductDetailsOpen.value = false
}



const filterPosts = async () => {
  const router = useRouter()
  const data = await $fetch('http://localhost:8080/api/products', { // TODO sprawdzic czy taki adres zostanie czy inny
    method: 'POST',
    body: searchRestriction,
    headers: {Authorization: 'Bearer ' + auth.token}
  }).catch(err => console.error(err.data))


  await router.push('/')
}
</script>

<template>
  <div class="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
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
                   placeholder="Wpisz cos sobie byku">
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
                <ListboxOption as="template" v-for="category in categories" :key="category.id" :value="category"
                               v-slot="{ active, selected }">
                  <li :class="[active ? 'bg-indigo-600 text-white' : 'text-gray-900', 'relative cursor-default select-none py-2 pl-3 pr-9']">
                    <div class="flex items-center">
                      <span :class="[selected ? 'font-semibold' : 'font-normal', 'block truncate']">{{
                          category.name
                        }}</span>
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
    <div class="sm:mx-auto sm:w-full sm:max-w-3xl">
      <single-post v-for="product in products"
                   :key="product.id"
                   :id="product.id"
                   :name="product.name"
                   :price="product.price"
                   :count="product.count"
                   :category="product.category"
                   :description="product.description"
                   :photo="product.photo"
                   :created-at="product.createdAt"
                   @click="showProductDetails(product)"
      />
      <ProductQuickView v-if="isProductDetailsOpen"
                        @close="closeProductDetails"
                        :id="selectedProduct.id"
                        :name="selectedProduct.name"
                        :price="selectedProduct.price"
                        :count="selectedProduct.count"
                        :category="selectedProduct.category"
                        :description="selectedProduct.description"
                        :photo="selectedProduct.photo"
                        :created-at="selectedProduct.createdAt"
                        :is-open="selectedProduct.isOpen"
                        :closeModal="closeProductDetails"
      />
    </div>
  </div>
</template>
