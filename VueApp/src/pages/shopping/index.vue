<script setup lang="ts">

import type { Product } from "~/types"

const productStore = useProductStore()
const boardProductStore = useBoardProductsStore()

onMounted( async () => {
  await boardProductStore.fetchBoardProducts()
})

const isProductDetailsOpen = ref(false)
const averageStars = ref(0)

const showProductDetails = (product: Product) => {
  if (product.user?.averageStars) {
    averageStars.value = product.user?.averageStars
  }
  productStore.setProduct(product)
  isProductDetailsOpen.value = true
}

const closeProductDetails = () => {
  isProductDetailsOpen.value = false
}

const page = ref(1)

watchEffect(() => {
  boardProductStore.page = page.value
  boardProductStore.fetchBoardProducts()
})

</script>

<template>
  <div class="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
    <filter-posts />
    <div class="sm:mx-auto sm:w-full sm:max-w-5xl">
      <single-post v-for="product in boardProductStore.boardProducts"
                   :key="product.id"
                   :id="product.id"
                   :name="product.name"
                   :price="product.price"
                   :count="product.count"
                   :category="product.category"
                   :description="product.description"
                   :photo="product.photo"
                   :created-at="product.createdAt"
                   :user="product.user"
                   @click="showProductDetails(product)"
      />
      <product-quick-view v-if="isProductDetailsOpen"
                        @close="closeProductDetails"
                        :closeModal="closeProductDetails"
                        :averageStars="averageStars"
      />
    </div>
    <div class="mx-auto mt-5%">
      <UPagination v-model="page" :page-count="boardProductStore.size" :total="boardProductStore.totalElements" />
    </div>
  </div>
</template>
