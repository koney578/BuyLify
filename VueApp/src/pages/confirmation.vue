<script setup lang="ts">

import {useOrderStore} from "~/stores/order";
import {useAuthStore} from "~/stores/auth";

const auth = useAuthStore()
const productStore = useProductStore()
const product = productStore.product
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
  idPaymentMethod: number;
  idDeliveryMethod: number;
  idProduct: number;
  productQuantity: number;
  address: Address;
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

    <div class="mt-2rem">
      <div class="flex mt-1rem text-gray-900 px-8 py-4 bg-gray-100 shadow-xl">
        <div class="w-full">
          <img
              :src="product?.photo"
              alt="Główne zdjęcie"
              class=" h-auto w-1/2"
          />
        </div>
        <div class="w-full">


          <div class="grid justify-items-center">
            <p class="text-xl italic ml-1rem">Wybrano: {{ order?.productQuantity }}</p>
            <p class="text-xl italic ml-1rem">Cena jednostkowa za produkt: {{ product?.price }} zł</p>
            <p class="text-xl italic ml-1rem">Ilość produktu w ogłoszeniu: {{ product?.count }}</p>
            <p class="text-2xl italic ml-1rem mt-1rem">Łączny koszt: {{ product?.price * order?.productQuantity }} zł</p>
          </div>
          <div class="grid justify-between mt-2rem">
            <p class="text-2xl">Nazwa: {{ product?.name }}</p>
            <p class="text-2xl mt-2">Opis: {{ product?.description }}</p>
          </div>

        </div>
      </div>
    </div>

    <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
      <form @submit.prevent="buyProduct" class="space-y-6" action="#" method="POST">
        <submit-button />
      </form>

      <other-product-label />

    </div>
  </div>
</template>
