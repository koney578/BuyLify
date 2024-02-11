<script setup lang="ts">
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
  productQuantity: '',
})

let productQuantityError = ''
const quantityPattern = /^[0-9]+$/;

function validateProductCount() {
  if (purchaseMethods.productQuantity === '') {
    productQuantityError = "";
    return false;
  }
  else if (!quantityPattern.test(purchaseMethods.productQuantity)) {
    productQuantityError = "Nie poprawna wartość!";
    return false;
  }
  else if (parseInt(purchaseMethods.productQuantity) > (product?.count ?? 0)) {
    productQuantityError = "Sprawawca ma za niewystarczająco towaru!";
    return false;
  }
  else {
    productQuantityError = "";
    return true;
  }
}

watch(purchaseMethods, () => {
  validateProductCount()
})


const addPurchaseMethods = () => {

  if (!purchaseMethods.idPaymentMethod || !purchaseMethods.idDeliveryMethod || !purchaseMethods.productQuantity) {
    console.error('Wszystkie pola są wymagane')
    return
  }

  if (!validateProductCount()) {
    console.error(productQuantityError)
    return
  }

  orderStore.methods = purchaseMethods
  // orderStore.setMethods(purchaseMethods)
  orderStore.orderStage = 2

  const router = useRouter()

  router.push('')
}

</script>

<template>
  <form @submit.prevent="addPurchaseMethods" class="space-y-6" method="POST">

    <div>
      <label for="methodPayment" class="block text-sm font-medium leading-6 text-gray-900">Metoda płatności</label>
      <div class="mt-2 rounded-md shadow-sm">
        <select v-model="purchaseMethods.idPaymentMethod" name="methodPayment" id="methodPayment"
                class="block w-full rounded-md border-0 text-gray-900 ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white p-0.5rem">
          <option value="0" selected disabled hidden>Wybierz metodę płatności</option>
          <option v-for="paymentMethod in paymentMethods"
                  :key="paymentMethods?.id"
                  :value="paymentMethod.id">
            {{ paymentMethod.name }}
          </option>


        </select>
      </div>
    </div>

    <div>
      <label for="methodPayment" class="block text-sm font-medium leading-6 text-gray-900">Sposób dostawy</label>

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
        <div v-if="productQuantityError" class="font-semibold text-rose-600">
          {{ productQuantityError }}
        </div>
        <input v-model="purchaseMethods.productQuantity" id="productQuantity" name="productQuantity" type="text"
               autocomplete="productQuantity" required
               class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white p-0.5rem"/>
      </div>
    </div>
    <div>
      <button type="submit"
              class="flex w-full justify-center rounded-md bg-indigo-600  px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">
        Przejdź dalej
      </button>
    </div>
  </form>
</template>
