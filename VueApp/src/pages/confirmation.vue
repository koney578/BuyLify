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

console.log(order)

const calculateTotalOrderCost = (): number => {
  let sum = 0;
  for (let item of cartStore.cartState) {
    sum += item.product.price * item.quantity
  }
  return sum
}

const totalSum = calculateTotalOrderCost()


// const methods = orderStore.methods
// const address = orderStore.address
//
// const order = {
//   idPaymentMethod: methods?.idPaymentMethod,
//   idDeliveryMethod: methods?.idDeliveryMethod,
//   idProduct: methods?.idProduct,
//   productQuantity: methods?.productQuantity,
//   address: {
//     name: address?.name,
//     surname: address?.surname,
//     phoneNumber: address?.phoneNumber,
//     email: address?.email,
//     country: address?.country,
//     city: address?.city,
//     street: address?.street,
//     houseUnitNumber: address?.houseUnitNumber,
//     postalCode: address?.postalCode,
//   },
// }

const buyProduct = async () => {
  await $fetchAPI('/api/orders', {
    method: 'POST',
    body: order,
    headers: {Authorization: 'Bearer ' + auth.token.token}
  }).then(() => {
    cartStore.clearCart()
  }).catch(err => console.error(err.data))
  const router = useRouter()
  await router.push('/board')
}


// const formattedTime = ref('loading ...')
//
// const calculateAuctionTime = () => { // TODO wykorzystac przy robieniu aukcji od nowa
//   if (product?.auctionEndsAt) {
//     const auctionEndTime = Date.parse(product.auctionEndsAt); // Parsowanie daty z product.auctionEndsAt
//     const currentTime = Date.now(); // Aktualny czas w milisekundach
//     const auctionTime = auctionEndTime - currentTime;
//     if (auctionTime < 0) {
//       return "Aukcja zakończona"
//     }
//     return formatTimeDifference(auctionTime);
//   }
//   return 0;
// }
//
// const formatTimeDifference = (milliseconds: number) => {
//   const seconds = Math.floor(milliseconds / 1000);
//   const minutes = Math.floor(seconds / 60);
//   const hours = Math.floor(minutes / 60);
//   const days = Math.floor(hours / 24);
//
//   const remainingHours = hours % 24;
//   const remainingMinutes = minutes % 60;
//   const remainingSeconds = seconds % 60;
//
//   formattedTime.value = `${days}d ${remainingHours}h ${remainingMinutes}m ${remainingSeconds}s`;
//   return formattedTime;
// };
//
// onMounted(() => {
//   calculateAuctionTime();
//
//   const intervalId = setInterval(() => {
//     calculateAuctionTime();
//   }, 1000);
//
//   onUnmounted(() => {
//     clearInterval(intervalId);
//   });
// });
</script>

<template>
  <div class="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
    <div class="sm:mx-auto sm:w-full sm:max-w-sm">
      <img class="mx-auto h-auto w-1/2" src="../images/logo-text.png" alt="BuyLify"/>
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
