<script setup lang="ts">

</script>

<template>
  <div class="flex items-center h-lg">
    <div class="w-1/2 ml-10rem relative">
      <h1 class="color-amber-600 font-bold text-4xl">Wyszukaj coś dla siebie!</h1>
      <p class="color-amber-600 font-bold text-2xl">U nas najlepszy wybor!</p>
      <label class="relative">
        <input class="bg-white color-black p-1rem rounded-3xl w-2xl mt-2rem shadow-2xl" value="" placeholder="Wpisz cos sobie byku">
      </label>
      <div class="w-2xl">
        <button type="submit"
                class="flex justify-center rounded-md bg-indigo-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600 mt-0.5rem ml-auto">
          Wyszukaj
        </button>
      </div>
    </div>
    <div class="w-1/2 mt-5rem">
<!--      <p class="color-black">prawa</p>-->
      <img src="../images/i-just-wanna-shop.png"  alt="I just wanna Shop" class="color-fuchsia w-3/4"/>
    </div>
  </div>


</template>
