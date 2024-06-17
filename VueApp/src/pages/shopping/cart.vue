<script setup lang="ts">

const cartStore = useCartStore()
cartStore.sortCartByUserId()

</script>

<template>
  <div class="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">

    <div class="sm:mx-auto sm:w-full sm:max-w-sm">
      <text-logo />
      <h1 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">Mój koszyk</h1>
    </div>

    <div class="sm:mx-auto sm:w-full sm:max-w-5xl">
      <single-cart-product v-for="product in cartStore.cartState"
                           :key="product.product.id"
                           :product="product.product"
                           :quantity="product.quantity"
      />
    </div>

    <div class="w-1/3 mx-auto mt-1rem">
      <div v-if="cartStore.cartState.length">
        <NuxtLink to="/shopping/purchase">
          <button type="submit"
                  class="flex w-full justify-center rounded-md bg-indigo-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">
            Kup teraz
          </button>
        </NuxtLink>
      </div>
      <div v-else>
        <other-product-label/>
      </div>
    </div>

  </div>
</template>
