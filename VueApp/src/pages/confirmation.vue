<script setup lang="ts">
const auth = useAuthStore()
const productStore = useProductStore()
const product = productStore.product
const orderStore = useOrderStore()

const order = {

}


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
  await $fetch('http://localhost:8080/api/orders', {
    method: 'POST',
    body: order,
    headers: {Authorization: 'Bearer ' + auth.token}
  }).catch(err => console.error(err.data))
  const router = useRouter()
  await router.push('/board')
}

const totalPrice = ref(0)
watchEffect(() => {
  totalPrice.value = (product?.price || 0) * (product?.count || 0)
})

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



    <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
      <form @submit.prevent="buyProduct" class="space-y-6" action="#" method="POST">
        <submit-button />
      </form>
      <other-product-label />
    </div>
  </div>
</template>
