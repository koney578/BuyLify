<script setup lang="ts">
const orderStore = useOrderStore()
const cartStore = useCartStore()
orderStore.orderStage = 1

cartStore.sortCartByUserId()

const shouldDisplaySeller = (index: number) => {
  if (index === 0) {
    return true;
  }

  const currentUserId = cartStore.cartState[index].product.user?.id;
  const previousUserId = cartStore.cartState[index - 1].product.user?.id;
  return currentUserId !== previousUserId;
}

const shouldDisplayPurchaseMethodsForm = (index: number) => {
  if (index === cartStore.cartState.length - 1) {
    return true
  }

  const currentUserId = cartStore.cartState[index].product.user?.id;
  const nextUserId = cartStore.cartState[index + 1].product.user?.id;
  return currentUserId !== nextUserId;
}

const countUniqueSellers = computed(() => {
  const uniqueSellers = new Set();
  const cartState = cartStore.cartState;

  cartState.forEach(product => {
    const sellerId = product.product.user?.id;
    if (sellerId) {
      uniqueSellers.add(sellerId);
    }
  });

  return uniqueSellers.size;
});

const decreaseStage = () => {
  orderStore.orderStage = 1
}

const increaseStage = () => {
  if (parseInt(String(countUniqueSellers.value)) !== cartStore.sellerState.length || cartStore.sellerState.length === 0) {
    return
  }
  orderStore.orderStage = 2
}
</script>

<template>
  <div class="sm:mx-auto sm:w-full sm:max-w-md mt-2rem">
    <text-logo />
    <h1 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">Podaj dane do wysyłki i
      metodę płatności</h1>
    <p class="text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">
      Wypełniono: {{ cartStore.sellerState.length }} / {{ parseInt(String(countUniqueSellers)) }}
    </p>
  </div>

  <div class="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
    <div v-if="orderStore.orderStage === 1">
      <div class="sm:mx-auto sm:w-full sm:max-w-2xl">
        <div v-for="(product, index) in cartStore.cartState" :key="product.product.id">
          <p v-if="shouldDisplaySeller(index)" class="text-xl text-black mt-2rem">Sprzedawca:
            {{ product.product.user?.username }}</p>
          <single-smaller-cart-product
              :product="product.product"
              :quantity="product.quantity"
          />
          <purchase-methods-form
              v-if="shouldDisplayPurchaseMethodsForm(index)"
              :id="product.product.user?.id || 0"
              :username="product.product.user?.username || ''"
              :email="product.product.user?.email || ''"
              :average-stars="product.product.user?.averageStars || 0"
          />
        </div>
        <form @submit.prevent="increaseStage" class="mt-2rem">
          <submit-button v-if="parseInt(String(countUniqueSellers)) === cartStore.sellerState.length"/>
        </form>
      </div>
    </div>

    <div v-else-if="orderStore.orderStage === 2">
      <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
        <address-form/>
        <div class="mt-10 text-center text-sm text-gray-500">
          Chcesz zmienić ustawienia zakupu?
          {{ ' ' }}
          <p @click="decreaseStage" class="font-semibold leading-6 text-indigo-600 hover:text-indigo-500">Kliknij
            tutaj!</p>
        </div>
        <other-product-label/>
      </div>
    </div>
  </div>
</template>
