<script setup lang="ts">

</script>

<template>
  <body class="bg-gray-200 min-h-screen">
  <header class="bg-white p-3 shadow-2xl">
    <nav class="items-center flex">
      <p class="color-black text-3rem font-semibold italic">BuyLify</p>
      <div class="flex w-full">
        <ul class="flex ml-2rem w-1/2">
          <li class="color-black p-4 ml-1rem mr-1rem text-2rem"><a href="/">Strona Główna</a></li>
          <li class="color-black p-4 ml-1rem mr-1rem text-2rem"><a href="/profile">Ustawienia konta</a></li>
          <li class="color-black p-4 ml-1rem mr-1rem text-2rem">bla bla</li>
        </ul>
        <ul class="flex w-1/2 justify-end">
          <li class="color-black p-4 ml-1rem mr-1rem text-2rem"><a href="/login">Zaloguj się</a></li>
          <li class="color-black p-4 ml-1rem mr-1rem text-2rem"><a href="/register">Załóż Konto</a></li>
        </ul>
      </div>

    </nav>
  </header>
  <section class="min-h-screen flex flex-col">
    <nuxt-page/>
  </section>
  <footer class="bg-amber-200 p-2rem">
    <p class="color-black">bla bla bla</p>
    <p class="color-black">bla bla bla</p>
    <p class="color-black">bla bla bla</p>
  </footer>
  </body>
</template>
