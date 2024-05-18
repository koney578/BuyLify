<script setup lang="ts">
import type {Order, OrderProduct, OrderInfo} from "~/types"
import {$fetchAPI} from "~/composables/$fetchApi";
const auth = useAuthStore()
const cartStore = useCartStore()
const orderStore = useOrderStore()

const order: Order = {
  products: [],
  orderInfo: [],
  address: {
    id: orderStore.address?.id || 0,
    name: orderStore.address?.name || "",
    surname: orderStore.address?.surname || "",
    phoneNumber: orderStore.address?.phoneNumber || "",
    email: orderStore.address?.phoneNumber || "",
    country: orderStore.address?.country || "",
    city: orderStore.address?.city || "",
    street: orderStore.address?.street || "",
    houseUnitNumber: orderStore.address?.houseUnitNumber || "",
    postalCode: orderStore.address?.postalCode || "",
  },
}

for (let item of cartStore.cartState) {
  let newItem = {
    productId: item.product.id,
    sellerId: item.product.user?.id,
    productQuantity: item.quantity
  }
  order.products.push(<OrderProduct>newItem)
}

for (let item of cartStore.sellerState) {
  let newItem = {
    sellerId: item.user.id,
    idPaymentMethod: item.paymentMethod.id,
    idDeliveryMethod: item.deliveryMethod.id,
  }
  order.orderInfo.push(<OrderInfo>newItem)
}

const calculateTotalOrderCost = (): number => {
  let sum = 0;
  for (let item of cartStore.cartState) {
    sum += item.product.price * item.quantity
  }
  return sum
}

const totalSum = calculateTotalOrderCost()
const confirmPurchase = ref(false)

const buyProduct = async () => {
  confirmPurchase.value = true
  await $fetchAPI('/api/orders', {
    method: 'POST',
    body: order,
    headers: {Authorization: 'Bearer ' + auth.token}
  }).then(() => {
    cartStore.clearCart()
  }).catch(err => console.error(err.data))
  const router = useRouter()
  await router.push('/shopping/completed')
}
</script>

<template>
  <UProgress animation="carousel" v-if="confirmPurchase"/>
  <div class="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
    <div class="sm:mx-auto sm:w-full sm:max-w-sm">
      <img class="mx-auto h-auto w-1/2" src="~/images/logo-text.png" alt="BuyLify"/>
      <h1 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">Potwierdź swoje zamówienie</h1>
    </div>

    <div class="text-2xl text-black mx-auto my-2rem">
      <p class="">
        Łączny koszt zamówienia: {{ totalSum }} zł
      </p>
    </div>


    <div class="sm:mx-auto sm:w-full sm:max-w-sm">
      <form @submit.prevent="buyProduct" class="space-y-6" action="#" method="POST">
        <submit-button />
      </form>
      <other-product-label />
    </div>
  </div>
</template>
