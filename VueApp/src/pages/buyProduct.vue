<script setup lang="ts">

import {useProductStore} from "~/stores/product";
import {useOrderStore} from "~/stores/order";
import AddressForm from "~/components/addressForm.vue";

interface Category {
  id: number;
  name: string;
}

interface Product {
  id: number;
  name: string;
  price: number;
  count: number;
  description: string;
  category: Category;
  createdAt: Date;
  photo: any;
}

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

const auth = useAuthStore()
const productStore = useProductStore()
const product = productStore.product
const orderStore = useOrderStore()


orderStore.orderStage = 1

const {data: deliveryMethods} = await useFetch<any[]>('http://localhost:8080/api/delivery-methods', {
  headers: {Authorization: 'Bearer ' + auth.token}
});

const {data: paymentMethods} = await useFetch<any[]>('http://localhost:8080/api/payment-methods', {
  headers: {Authorization: 'Bearer ' + auth.token}
});

const ifAddressConfirm = ref(false)

const order = reactive({
  idPaymentMethod: 0,
  idDeliveryMethod: 0,
  idProduct: product?.id,
  productQuantity: 0,
  address: {
    name: '',
    surname: '',
    phoneNumber: '',
    email: '',
    country: '',
    city: '',
    street: '',
    houseUnitNumber: '',
    postalCode: '',
  },
})

const decreaseStage = () => {
  orderStore.orderStage = 1
}

</script>

<template>
  <div class="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
    <div class="sm:mx-auto sm:w-full sm:max-w-sm">
      <img class="mx-auto h-auto w-1/2" src="../images/logo-text.png" alt="BuyLify"/>
      <h1 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">Podaj dane do wysyłki</h1>
    </div>

    <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
      <div v-if="orderStore.orderStage === 1">
        <purchase-methods-form />
        <other-product-label />
      </div>

      <div v-else-if="orderStore.orderStage === 2">
        <addressForm />
        <div class="mt-10 text-center text-sm text-gray-500">
          Chcesz zmienić ustawienia zakupu?
          {{ ' ' }}
          <p @click="decreaseStage" class="font-semibold leading-6 text-indigo-600 hover:text-indigo-500">Kliknij tutaj!</p>
        </div>
        <other-product-label />
      </div>
    </div>
  </div>
</template>