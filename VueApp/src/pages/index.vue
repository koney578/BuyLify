<script setup lang="ts">
  const auth = useAuthStore()
</script>

<template>
  <div class="flex items-center max-md:justify-center max-md:flex-col">
    <div class="w-1/2 md:ml-10rem">
      <h1 class="color-amber-600 font-bold text-4xl">Wyszukaj coś dla siebie!</h1>
      <p class="color-amber-600 font-bold text-2xl">U nas najlepszy wybor!</p>
      <label class="">
        <input class="block bg-white color-black p-1rem rounded-3xl max-w-2xl w-full mt-2rem shadow-2xl" value="" placeholder="Wyszukaj produkt">
      </label>
      <search-button />
    </div>
    <div class="w-1/2 mt-5rem">
      <!--      <p class="color-black">prawa</p>-->
        <img src="../images/i-just-wanna-shop.png" alt="I just wanna Shop" class="color-fuchsia min-w-[420px] max-w-3/4 h-auto animate-zoom-in-down"/>
    </div>
  </div>


</template>
