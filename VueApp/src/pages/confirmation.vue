<script setup lang="ts">

import {useOrderStore} from "~/stores/order";
import {useAuthStore} from "~/stores/auth";

const auth = useAuthStore()
const orderStore = useOrderStore()
orderStore.orderStage = 3

interface Address {
  name: string;
  surname: string;
  phoneNumber: string;
  email: string;
  country: string;
  city: string;
  street: string;
  houseUnitNumber: string;
  postalCode: string;
}

interface Order {
  idPaymentMethod: number,
  idDeliveryMethod: number,
  idProduct: number,
  productQuantity: number,
  address: Address,
}

const methods = orderStore.methods
const address = orderStore.address

const order = {
  idPaymentMethod: methods?.idPaymentMethod,
  idDeliveryMethod: methods?.idDeliveryMethod,
  idProduct: methods?.idProduct,
  productQuantity: methods?.productQuantity,
  address: {
    name: address?.name,
    surname: address?.surname,
    phoneNumber: address?.phoneNumber,
    email: address?.email,
    country: address?.country,
    city: address?.city,
    street: address?.street,
    houseUnitNumber: address?.houseUnitNumber,
    postalCode: address?.postalCode,
  },
}


const buyProduct = async () => {
  const router = useRouter()

  const data = await $fetch('http://localhost:8080/api/orders', {
    method: 'POST',
    body: order,
    headers: {Authorization: 'Bearer ' + auth.token}
  }).catch(err => console.error(err.data))

  await router.push('/board')
}

</script>

<template>
  <div class="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
    <div class="sm:mx-auto sm:w-full sm:max-w-sm">
      <img class="mx-auto h-auto w-1/2" src="../images/logo-text.png" alt="BuyLify"/>
      <h1 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">Jesteś krok od swojego produktu!</h1>
    </div>

    <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
      <form @submit.prevent="buyProduct" class="space-y-6" action="#" method="POST">
        <div>
          <button type="submit"
                  class="flex w-full justify-center rounded-md bg-indigo-600  px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">
            Potwierdź zakup
          </button>
        </div>
      </form>

    </div>
  </div>
</template>
