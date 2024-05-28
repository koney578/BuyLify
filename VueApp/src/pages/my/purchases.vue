<script setup lang="ts">
import type { Order } from "~/types"
import { sortAndGroupOrders } from "~/utils"

const auth = useAuthStore()
const {data: orders} = await useFetchAPI<Order[]>('/api/products/purchased', {
  headers: {Authorization: 'Bearer ' + auth.token}
});
console.log(orders.value)

const sortedOrders = ref()
watchEffect(() => {
  if (orders.value) {
    sortedOrders.value = sortAndGroupOrders(orders.value)
    console.log(sortedOrders)
  }
})

</script>

<template>
  <div class="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
    <div class="sm:mx-auto sm:w-full sm:max-w-sm">
      <text-logo />
      <h1 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">Moje kupione produkty</h1>
    </div>
    <div class="sm:mx-auto sm:w-full sm:max-w-6xl">
      <single-order v-for="order in sortedOrders" :order-id="order.orderId" :products="order.products" />

    </div>
  </div>
</template>
