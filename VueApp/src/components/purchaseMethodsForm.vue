<script setup lang="ts">
import {useProductStore} from "~/stores/product";
import {useOrderStore} from "~/stores/order";

interface Category {
  id: number,
  name: string,
}

interface Product {
  id: number,
  name: string,
  price: number,
  count: number,
  description: string,
  category: Category,
  createdAt: Date,
  photo: any,
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

interface Order {
  idPaymentMethod: number,
  idDeliveryMethod: number,
  idProduct: number,
  productQuantity: number,
  address: Address,
}

const auth = useAuthStore()
const productStore = useProductStore()
const product = productStore.product
const orderStore = useOrderStore()

const {data: deliveryMethods} = await useFetch<any[]>('http://localhost:8080/api/delivery-methods', {
  headers: {Authorization: 'Bearer ' + auth.token}
});

const {data: paymentMethods} = await useFetch<any[]>('http://localhost:8080/api/payment-methods', {
  headers: {Authorization: 'Bearer ' + auth.token}
});

const purchaseMethods = reactive({
  idPaymentMethod: 0,
  idDeliveryMethod: 0,
  idProduct: product?.id,
  productQuantity: 0,
})


const addPurchaseMethods = () => {

  if (!purchaseMethods.idPaymentMethod || !purchaseMethods.idDeliveryMethod || !purchaseMethods.productQuantity) {
    console.error('Wszystkie pola są wymagane')
    return
  }

  orderStore.methods = purchaseMethods
  // orderStore.setMethods(purchaseMethods)
  orderStore.orderStage = 2
  // console.log(productStore.address?.email)

  const router = useRouter()
  // const data = await $fetch('http://localhost:8080/api/addresses', {
  //   method: 'POST',
  //   body: address,
  //   headers: {Authorization: 'Bearer ' + auth.token}
  // }).catch(err => console.error(err.data))
  // console.log(address)
  //
  router.push('')
}

</script>

<template>
  <form @submit.prevent="addPurchaseMethods" class="space-y-6" method="POST">

    <div>
      <label for="methodPayment" class="block text-sm font-medium leading-6 text-gray-900">Metoda płatności</label>
      <!--          <div v-if="productCountError" class="font-semibold text-rose-600">-->
      <!--            {{ productCountError }}-->
      <!--          </div>-->
      <div class="mt-2 rounded-md shadow-sm">
        <select v-model="purchaseMethods.idPaymentMethod" name="methodPayment" id="methodPayment"
                class="block w-full rounded-md border-0 text-gray-900 ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white p-0.5rem">
          <option value="0" selected disabled hidden>Wybierz metodę płatności</option>
          <option v-for="paymentMethod in paymentMethods"
                  :key="paymentMethods.id"
                  :value="paymentMethod.id">
            {{ paymentMethod.name }}
          </option>


        </select>
      </div>
    </div>

    <div>
      <label for="methodPayment" class="block text-sm font-medium leading-6 text-gray-900">Sposób dostawy</label>
      <!--          <div v-if="productCountError" class="font-semibold text-rose-600">-->
      <!--            {{ productCountError }}-->
      <!--          </div>-->
      <div class="mt-2 rounded-md shadow-sm">
        <select v-model="purchaseMethods.idDeliveryMethod"
                name="DeliveryMethod"
                id="DeliveryMethod"
                class="block w-full rounded-md border-0 text-gray-900 ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white p-0.5rem">
          <option value="0" selected disabled hidden>Wybierz sposób dostawy</option>
          <option v-for="deliveryMethod in deliveryMethods"
                  :key="deliveryMethod.id"
                  :value="deliveryMethod.id">
            {{ deliveryMethod.name }}
          </option>

        </select>
      </div>
    </div>


    <div>
      <label for="productQuantity" class="block text-sm font-medium leading-6 text-gray-900">Ilość produktu</label>
      <div class="mt-2">
        <input v-model="purchaseMethods.productQuantity" id="productQuantity" name="productQuantity" type="text"
               autocomplete="productQuantity" required=""
               class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white p-0.5rem"/>
      </div>
    </div>

    <!--        TODO dodać jakieś info o tym produkcie ktory wybral-->
    <!--        <div class="block text-sm font-medium leading-6 text-gray-900">{{ product?.name }}</div>-->
    <!--        <div class="block text-sm font-medium leading-6 text-gray-900">{{ product?.description }}</div>-->
    <div>
      <button type="submit"
              class="flex w-full justify-center rounded-md bg-indigo-600  px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">
        Przejdź dalej
      </button>
    </div>
  </form>
</template>