<script setup lang="ts">

const cartStore = useCartStore()
const props = defineProps<{id: number}>()

const removeProductFromCart = () => {
  cartStore.removeProductFromCart(props.id)
}
</script>

<template>
  <div class="items-center h-22rem flex ml-1rem">
    <Icon name="mdi:cart-remove" class="text-5rem text-red-500" @click="removeProductFromCart"/>

  </div>
</template>
