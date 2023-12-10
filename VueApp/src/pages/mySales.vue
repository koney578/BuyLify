<script setup lang="ts">
import {useAuthStore} from "~/stores/auth";
import {useProductStore} from "~/stores/product";

const auth = useAuthStore()
const productStore = useProductStore()
const {data: postedProducts} = await useFetch<any[]>('http://localhost:8080/api/products/posted', {
  headers: {Authorization: 'Bearer ' + auth.token}
});

console.log(postedProducts)
</script>

<template>

</template>