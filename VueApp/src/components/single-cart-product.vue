<script setup lang="ts">
import type {CartItem} from "~/types"
import RemoveCartProduct from "~/components/remove-cart-product.vue";

const cartStore = useCartStore()
const props = defineProps<CartItem>()
const quantity = ref('')

const quantityRange = ref(props.quantity)
const quantityInput = ref(props.quantity.toString())
const countPattern = /^[0-9]+$/;

watch(() => quantityInput.value, (newValue) => {
  if (quantityInput.value === '') {
    quantityInput.value = '0'
  } else {
    if (!countPattern.test(quantityInput.value)) {
      quantityInput.value = '0'
    }
    quantityRange.value = parseInt(newValue)
  }
})

watch(() => quantityRange.value, (newValue) => {
  quantityInput.value = newValue.toString()
})

const quantityError = ref('')


const setQuantity = async () => {
  quantity.value = quantityRange.value.toString()
  if (!countPattern.test(quantity.value) || parseInt(quantity.value) > props.product.count) {
    quantityError.value = "Podana ilość jest nieprawidłowa."
    const errorTime = (ms: number) => new Promise(resolve => setTimeout(resolve, ms));
    await errorTime(3000)
    quantityError.value = ''
    return
  }
  cartStore.updateProductQuantity(props.product.id, parseInt(quantity.value))
}

const changeQuantityOptionVisible = ref(false)
const changeQuantityButtonClicked = () => {
  changeQuantityOptionVisible.value = !changeQuantityOptionVisible.value
}
</script>

<template>
  <div class="mt-2rem w-full">
    <div class="flex mt-1rem text-gray-900 px-8 py-4 bg-gray-100 shadow-xl w-full">
      <div class="w-full flex">
        <img
            :src="props.product.photo"
            alt="Główne zdjęcie"
            class="object-cover h-64 w-64"
        />
      </div>
      <div class="w-full">
        <div class="grid justify-items-center">
          <p class="text-3xl">{{ props.product.name }}</p>
        </div>
        <div class="flex mt-1rem justify-between">
          <div class="text-xl flex">Cena: <p class="italic ml-1">{{ props.product.price }} zł</p></div>
          <div class="text-xl flex">Ocena: <p class="italic ml-1">{{ props.product.user?.averageStars || "brak" }}</p></div>
        </div>
        <div class="flex mt-2rem justify-between">
          <div class="text-xl flex">Kategoria: <p class="italic ml-1">{{ props.product.category.name }}</p></div>
          <div class="text-xl flex">sprzedawca: <p class="italic ml-1">{{ props.product.user?.username }}</p></div>
        </div>
        <div class="flex mt-1rem justify-between">
          <div class="text-xl flex">Opis: <p class="italic ml-1 text-justify">{{ props.product.description }}</p></div>
        </div>
        <div class="flex mt-2rem justify-between">
          <div class="text-xl flex">Wybrano: <p class="italic ml-1">{{ props.quantity }}</p></div>
          <div class="text-xl flex">Ilość: <p class="italic ml-1">{{ props.product.count }}</p></div>
        </div>

        <div class="my-1rem">
          <button type="button" @click="changeQuantityButtonClicked"
                  class="flex justify-center rounded-md bg-indigo-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">
            Zmień ilość
          </button>
        </div>


        <div v-if="changeQuantityOptionVisible" class="mt-2rem">
          <form @submit.prevent="setQuantity">
            <div class="flex">
              <div v-if="quantityError" class="font-semibold animate-jello text-rose-600 my-1">
                {{ quantityError }}
              </div>
            </div>
            <label for="quantity" class="block text-sm font-medium leading-6 text-gray-900">
              Wybierz ilość produktu
            </label>
            <div class="mt-2">
              <input v-model="quantityInput" id="quantity" name="quantity" type="text"
                     autocomplete="quantity"
                     required
                     placeholder="1"
                     class="block rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white p-0.5rem"/>
            </div>
            <URange :min="0" :max="props.product.count" v-model="quantityRange" color="indigo" class="my-1rem"/>

            <div class="mt-1rem">
              <button type="submit"
                      class="flex justify-center rounded-md bg-indigo-600  px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">
                Potwierdź
              </button>
            </div>
          </form>
        </div>

      </div>
      <remove-cart-product class="mt-1rem" :id="props.product.id"/>
    </div>

  </div>
</template>
