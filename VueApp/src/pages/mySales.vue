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
  <div class="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
    <div class="sm:mx-auto sm:w-full sm:max-w-sm">
      <img class="mx-auto h-auto w-1/2" src="../images/logo-text.png" alt="BuyLify"/>
      <h1 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">Moje ogłoszenia</h1>
    </div>
    <div class="sm:mx-auto sm:w-full sm:max-w-6xl">
      <single-posted-product v-for="postedProduct in postedProducts"
                             :key="postedProduct.id"
                             :id="postedProduct.id"
                             :name="postedProduct.name"
                             :price="postedProduct.price"
                             :description="postedProduct.description"
                             :photo="postedProduct.photo"
                             :category="postedProduct.category"
                             :createdAt="postedProduct.createdAt"
                             :count="postedProduct.count"
                             :user="postedProduct.user"
      />
    </div>
  </div>
</template>