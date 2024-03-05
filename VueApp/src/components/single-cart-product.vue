<script setup lang="ts">
import type { CartItem } from "~/types"
import RemoveCartProduct from "~/components/remove-cart-product.vue";

const cartStore = useCartStore()
const props = defineProps<CartItem>()
const quantity = ref(props.quantity.toString())

const countPattern = /^[0-9]+$/;
const setQuantity = async () => {
  if (!countPattern.test(quantity.value) || parseInt(quantity.value) > props.product.count) {
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
  <div class="mt-2rem flex">
    <div class="flex mt-1rem text-gray-900 px-8 py-4 bg-gray-100 shadow-xl">
      <div class="w-1/2">
        <img
            :src="props.product.photo"
            alt="Główne zdjęcie"
            class="h-auto w-3/4"
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
                  class="flex justify-center rounded-md bg-indigo-600  px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">
            Zmień ilość
          </button>
        </div>


        <div v-if="changeQuantityOptionVisible" class="mt-2rem">
          <form @submit.prevent="setQuantity">
            <label for="quantity" class="block text-sm font-medium leading-6 text-gray-900">
              Wybierz ilość produktu
            </label>
            <div class="mt-2">
              <input v-model="quantity" id="quantity" name="quantity" type="text"
                     autocomplete="quantity"
                     required
                     placeholder="1"
                     class="block rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white p-0.5rem"/>
            </div>

            <div class="mt-1rem">
              <button type="submit"
                      class="flex justify-center rounded-md bg-indigo-600  px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">
                Potwierdź
              </button>
            </div>
          </form>
        </div>

      </div>
    </div>
    <remove-cart-product class="mt-1rem" :id="props.product.id"/>
  </div>
</template>
