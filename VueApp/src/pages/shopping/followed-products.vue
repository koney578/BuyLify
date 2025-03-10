<script setup lang="ts">
import type { Category, Product } from "~/types"
const auth = useAuthStore()
const productStore = useProductStore()
const followedProductsStore = useFollowedProducts()
const {data: categories} = await useFetchAPI<Category[]>('/api/categories', {
  headers: {Authorization: 'Bearer ' + auth.token}
});

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

watchEffect(() => {
  followedProductsStore.fetchFollowedProducts()
})

const isProductDetailsOpen = ref(false)
const averageStars = ref(0)

const showProductDetails = (announcement: Product) => {
  const product = reactive<Product>({
    id: announcement.id,
    name: announcement.name,
    price: announcement.price,
    count: announcement.price,
    description: announcement.description,
    category: announcement.category,
    createdAt: announcement.createdAt,
    photo: announcement.photo,
    user: announcement.user,

  })
  if (announcement.user?.averageStars) {
    averageStars.value = announcement.user?.averageStars
  } else {
    averageStars.value = 0
  }

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
          <text-logo />
          <h1 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">Twoje zapisane produkty</h1>
        </div>
    </div>
    <div class="sm:mx-auto sm:w-full sm:max-w-5xl">
      <single-post v-for="announcement in followedProductsStore.followedProducts || []"
                   :key="announcement.id"
                   :id="announcement.id"
                   :name="announcement.name"
                   :price="announcement.price"
                   :count="announcement.count"
                   :category="announcement.category"
                   :description="announcement.description"
                   :photo="announcement.photo"
                   :created-at="announcement.createdAt"
                   :user="announcement.user"
                   @click="showProductDetails(announcement)"
      />
      <product-quick-view v-if="isProductDetailsOpen"
                        @close="closeProductDetails"
                        :closeModal="closeProductDetails"
                        :averageStars="averageStars"
      />
    </div>
  </div>
</template>
