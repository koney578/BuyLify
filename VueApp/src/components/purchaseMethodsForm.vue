<script setup lang="ts">
import type {User, DeliveryMethod, PaymentMethod, SellerItem} from "~/types";

const auth = useAuthStore()
const cartStore = useCartStore()
const props = defineProps<User>()

const {data: deliveryMethods} = await useFetch<DeliveryMethod[]>('http://localhost:8080/api/delivery-methods', {
  headers: {Authorization: 'Bearer ' + auth.token}
});

const {data: paymentMethods} = await useFetch<PaymentMethod[]>('http://localhost:8080/api/payment-methods', {
  headers: {Authorization: 'Bearer ' + auth.token}
});

const purchaseMethods = reactive({
  deliveryMethod: deliveryMethods?.value?.[0],
  paymentMethod: paymentMethods?.value?.[0],
  user: props,
});

watchEffect(() => {
  purchaseMethods.paymentMethod = paymentMethods?.value?.[0]
  purchaseMethods.deliveryMethod = deliveryMethods?.value?.[0]
  purchaseMethods.user = props
})

const addPurchaseMethods = () => {

  if (!purchaseMethods.paymentMethod || !purchaseMethods.deliveryMethod) {
    console.error('Wszystkie pola są wymagane')
    return
  }
  cartStore.addSeller(purchaseMethods)
}

</script>

<template>
  <form @submit.prevent="addPurchaseMethods" class="space-y-6 mt-1rem" method="POST">

    <div>
      <label for="methodPayment" class="block text-sm font-medium leading-6 text-gray-900">Metoda płatności</label>
      <div class="mt-2 rounded-md shadow-sm">
        <select v-model="purchaseMethods.paymentMethod" name="methodPayment" id="methodPayment"
                class="block w-full rounded-md border-0 text-gray-900 ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white p-0.5rem">
          <option value="0" selected disabled hidden>Wybierz metodę płatności</option>
          <option v-for="paymentMethod in paymentMethods"
                  :key="paymentMethod.id"
                  :value="paymentMethod">
            {{ paymentMethod.name }}
          </option>


        </select>
      </div>
    </div>

    <div>
      <label for="methodPayment" class="block text-sm font-medium leading-6 text-gray-900">Sposób dostawy</label>

      <div class="mt-2 rounded-md shadow-sm">
        <select v-model="purchaseMethods.deliveryMethod"
                name="DeliveryMethod"
                id="DeliveryMethod"
                class="block w-full rounded-md border-0 text-gray-900 ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white p-0.5rem">
          <option value="0" selected disabled hidden>Wybierz sposób dostawy</option>
          <option v-for="deliveryMethod in deliveryMethods"
                  :key="deliveryMethod.id"
                  :value="deliveryMethod">
            {{ deliveryMethod.name }}
          </option>

        </select>
      </div>
    </div>

    <div>
      <button type="submit"
              class="flex w-full justify-center rounded-md bg-indigo-600  px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">
        Zatwierdź dostawę i płatność
      </button>
    </div>
  </form>
</template>
