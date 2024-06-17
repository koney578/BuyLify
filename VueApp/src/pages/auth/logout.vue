<script setup lang="ts">
const auth = useAuthStore();
const router = useRouter();
const cart = useCartStore();
cart.clearCart();

auth.logout();
router.push("/");
</script>

<template>

</template>

