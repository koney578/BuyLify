<script setup lang="ts">
import {useAuthStore} from "~/stores/auth";
import {useProductStore} from "~/stores/product";
import SinglePost from "~/components/singlePost.vue";
import {Listbox, ListboxButton, ListboxLabel, ListboxOption, ListboxOptions} from "@headlessui/vue";
import ProductQuickView from "~/components/ProductQuickView.vue";
import {CheckIcon} from "@heroicons/vue/20/solid";

const auth = useAuthStore()
const productStore = useProductStore()
const {data: orders} = await useFetch<any[]>('http://localhost:8080/api/orders', {
  headers: {Authorization: 'Bearer ' + auth.token}
});

console.log(orders)


</script>

<template>
  <div class="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
    <div class="sm:mx-auto sm:w-full sm:max-w-sm">
      <img class="mx-auto h-auto w-1/2" src="../images/logo-text.png" alt="BuyLify"/>
      <h1 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">Moje kupione produkty</h1>
    </div>
    <div class="sm:mx-auto sm:w-full sm:max-w-6xl">
      <single-order v-for="order in orders"
                    :key="order.id"
                    :id="order.id"
                    :address="order.address"
                    :payment-method="order.paymentMethod"
                    :product="order.product"
                    :product-quantity="order.productQuantity"
                    :order-status="order.orderStatus"
                    :total-value="order.totalValue"
                    :create-at="order.createAt"
                    :delivery-method="order.deliveryMethod"
      />
    </div>
  </div>

</template>