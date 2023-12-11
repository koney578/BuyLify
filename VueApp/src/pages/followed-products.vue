<script setup lang="ts">

import {Listbox, ListboxButton, ListboxLabel, ListboxOption, ListboxOptions} from "@headlessui/vue";
import {CheckIcon} from "@heroicons/vue/20/solid";

import {useAuthStore} from "~/stores/auth";
import SinglePost from "~/components/singlePost.vue";
import ProductQuickView from "~/components/ProductQuickView.vue";
import {useProductStore} from "~/stores/product";

const auth = useAuthStore()
const productStore = useProductStore()
const {data: categories} = await useFetch<Category[]>('http://localhost:8080/api/categories', {
  headers: {Authorization: 'Bearer ' + auth.token}
});

interface Category {
  id: number;
  name: string;
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

const {data: products} = await useFetch<Product[]>('http://localhost:8080/api/' + auth.user.id + '/followed-products', {
  headers: {Authorization: 'Bearer ' + auth.token}
});

console.log(products)

interface Product {
  id: number;
  name: string;
  price: number;
  count: number;
  description: string;
  photo: string;
  category: Category;
  createdAt: Date;
  user: any;
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
  user: {}
}

const isProductDetailsOpen = ref(false)
const selectedProduct: Ref<Product> = ref<Product>(defaultProduct)


const showProductDetails = (product: Product) => {
  selectedProduct.value = product

  productStore.setProduct(product)
  isProductDetailsOpen.value = true
}

const closeProductDetails = () => {
  isProductDetailsOpen.value = false
}

</script>

<template>
  <div class="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
    <div class="sm:mx-auto sm:w-full sm:max-w-sm">
        <div class="sm:mx-auto sm:w-full sm:max-w-sm">
          <img class="mx-auto h-auto w-1/2" src="../images/logo-text.png" alt="BuyLify"/>
          <h1 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">Twoje zapisane produkty</h1>
        </div>
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
                        :is-open="selectedProduct.isOpen"
                        :closeModal="closeProductDetails"
                        :averageStars="selectedProduct.user.averageStars"
      />
    </div>
  </div>
</template>