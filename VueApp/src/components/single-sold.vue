<script setup lang="ts">
import type {OrderStatus, SoldProduct, SortedSoldProduct} from "~/types";

const props = defineProps<SortedSoldProduct>()
// const orderStatuses = [{
//   id: 1,
//   name: 'Zamówienie przyjęte'
// }, {
//   id: 2,
//   name: 'Wysłane'
// }, {
//   id: 3,
//   name: 'Dostarczone'
// }, {
//   id: 4,
//   name: 'Zwrócone'
//
// }]

const auth = useAuthStore()
const selected = ref()

const {data: orderStatuses} = await useFetchAPI<OrderStatus[]>('/api/order-statuses', {
  headers: {Authorization: 'Bearer ' + auth.token},
})

watchEffect(() => {
  if (orderStatuses.value) {
    selected.value = props.statusId
  }

})

const changeOrderStatus = async () => {
  await useFetchAPI('/api/orders/' + props.orderId + '/status', {
    method: 'PATCH',
    headers: {Authorization: 'Bearer ' + auth.token},
    body: {
      statusId: selected.value
    }
  })
}

</script>

<template>
  <div class="mt-2rem">
    <div class="flex mt-1rem text-gray-900 px-8 py-4 bg-orange-200 shadow-xl w-full">
      <div class="text-2xl color-black w-full">
<!--        <p class="text-2xl my-1rem">Numer zamówienia: {{ props.orderId }}</p> &lt;!&ndash; TODO: NAPRAWIC &ndash;&gt;-->
<!--        <p class="text-2xl my-1rem">Sprzedawca {{ props.products[0].user?.username }}</p> &lt;!&ndash; TODO: NAPRAWIC &ndash;&gt;-->
<!--        <p class="text-2xl my-1rem">Status: </p>-->
        <single-post v-for="product in props.products" :key="product.id"
                     :id="product.id"
                     :name="product.name"
                     :created-at="product.createdAt"
                     :description="product.description"
                     :photo="product.photo"
                     :price="product.price"
                     :user="product.user"
                     :category="product.category"
                     :count="product.count"
                     class="w-full"
        />

        <div class="flex mt-2rem">
          <p class="mx-auto font-bold">Dane do wysyłki</p>
        </div>
        <div>
          <div class="flex">
            <p>Kupujący:</p>
            <p class="ml-1 italic">{{ props.address.name }} {{ props.address.surname }}</p>
          </div>
          <p class="flex">Kraj: <div class="ml-1 italic">{{ props.address.country }}</div></p>
          <p class="flex">Miasto: <div class="ml-1 italic">{{ props.address.city }}</div></p>
          <p class="flex">Kod pocztowy: <div class="ml-1 italic">{{ props.address.postalCode }}</div></p>
          <p class="flex">Ulica: <div class="ml-1 italic">{{ props.address.street }}</div></p>
          <p class="flex">Numer domu: <div class="ml-1 italic">{{ props.address.houseUnitNumber }}</div></p>
          <p class="flex">Adres email: <div class="ml-1 italic">{{ props.address.email }}</div></p>
          <p class="flex">Numer telefonu: <div class="ml-1 italic">{{ props.address.phoneNumber }}</div></p>
        </div>

        <div class="mt-2rem">
          <USelectMenu v-model="selected" :options="orderStatuses || []" value-attribute="id" option-attribute="name" size="xl" />
          <div class="flex">
            <button @click="changeOrderStatus" class="bg-emerald px-4rem py-2 rounded-2xl mt-1rem mx-auto">Zmień status zamówienia</button>
          </div>
        </div>

      </div>


    </div>
  </div>
</template>
