<script setup lang="ts">

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

const {data: announcements} = await useFetch<Announcement[]>('http://localhost:8080/api/followed-products', {
  headers: {Authorization: 'Bearer ' + auth.token}
});


interface Announcement {
  id: number;
  name: string;
  category: Category;
  price: number;
  count: number;
  description: string;
  photo: string;
  createdAt: string;
  user: {
    id: number;
    username: number | null;
    email: string | null;
    averageStars: number | null;
  }
}

interface Product {
  id: number;
  name: string;
  price: number;
  count: number;
  description: string;
  photo: string;
  category: Category;
  createdAt: string;
  user: any;
}

const product = {
  id: 0,
  name: "",
  price: 0,
  count: 0,
  description: "",
  category: noCategory,
  createdAt: '',
  photo: '',
  user: {}
}

const isProductDetailsOpen = ref(false)
const selectedProduct: Ref<Product> = ref<Product>(product)


const showProductDetails = (announcement: Announcement) => {
  product.id = announcement.id
  product.name = announcement.name
  product.price = announcement.price
  product.count = announcement.count
  product.description = announcement.description
  product.category = announcement.category
  product.createdAt = announcement.createdAt
  product.photo = announcement.photo
  product.user = announcement.user

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
      <single-post v-for="announcement in announcements"
                   :key="announcement.id"
                   :id="announcement.id"
                   :name="announcement.name"
                   :price="announcement.price"
                   :count="announcement.count"
                   :category="announcement.category"
                   :description="announcement.description"
                   :photo="announcement.photo"
                   :created-at="announcement.createdAt"
                   @click="showProductDetails(announcement)"
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