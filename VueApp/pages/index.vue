<script setup lang="ts">

</script>

<template>
  <div class="flex items-center max-md:justify-center max-md:flex-col">
    <div class="w-1/2 md:ml-10rem">
      <h1 class="color-amber-600 font-bold text-4xl">Wyszukaj coś dla siebie!</h1>
      <p class="color-amber-600 font-bold text-2xl">U nas najlepszy wybor!</p>
      <label class="">
        <input class="block bg-white color-black p-1rem rounded-3xl max-w-2xl w-full mt-2rem shadow-2xl" value="" placeholder="Wpisz cos sobie byku">
      </label>
      <div class="max-w-2xl w-full">
        <button type="submit"
                class="flex justify-center rounded-md bg-indigo-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600 mt-0.5rem ml-auto">
          Wyszukaj
        </button>
      </div>
    </div>
    <div class="w-1/2 mt-5rem">
      <!--      <p class="color-black">prawa</p>-->
        <img src="../images/i-just-wanna-shop.png"  alt="I just wanna Shop" class="color-fuchsia min-w-[420px] w-full max-w-3/4 h-auto animate-zoom-in-down"/>
    </div>

  </div>


</template>
