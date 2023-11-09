<script setup lang="ts">
import IcBaselineDensityMedium from "~/icons/IcBaselineDensityMedium.vue";

const auth = useAuthStore()

const mobileMenuOpen = ref(false)
</script>

<template>
  <body class="bg-gray-200 min-h-screen">
  <header class="bg-white p-1 shadow-2xl">
    <nav class="items-center flex">
      <ul class="hidden md:flex items-center w-full">
        <li class="color-black p-4 ml-1rem mr-1rem text-2rem">
          <NuxtLink to="/">
            <img src="../images/logo.png" alt="logo" class="color-fuchsia w-16"/>
          </NuxtLink>
        </li>

        <li class="color-black p-4 ml-1rem mr-1rem text-lg"><NuxtLink to="/">Strona Główna</NuxtLink></li>
        <li class="color-black p-4 ml-1rem mr-1rem text-lg"><NuxtLink to="/profile">Ustawienia konta</NuxtLink></li>
        <li class="color-black p-4 ml-1rem mr-1rem text-lg mr-auto"><NuxtLink to="/announcement">Ogloszenie</NuxtLink></li>

        <template v-if="!auth.isLoggedIn">
          <li class="color-black p-4 ml-1rem mr-1rem text-lg"><NuxtLink to="/login">Zaloguj się</NuxtLink></li>
          <li class="color-black p-4 ml-1rem mr-1rem text-lg"><NuxtLink to="/register">Załóż Konto</NuxtLink></li>
        </template>

        <li v-else @click="auth.logout()" class="color-black p-4 ml-1rem mr-1rem text-lg">
          <a>Wyloguj się</a>
        </li>
      </ul>
      <div class="flex items-center md:hidden w-full px-4">
        <NuxtLink to="/">
          <img src="../images/logo.png" alt="logo" class="color-fuchsia w-16"/>
        </NuxtLink>

        <div @click="mobileMenuOpen = !mobileMenuOpen" class="relative ml-auto">
          <div class="text-black">
            <IcBaselineDensityMedium />
          </div>

          <ul v-if="mobileMenuOpen" class="fixed inset-0 top-19 bg-gray-100">
            <li class="color-black p-4 ml-1rem mr-1rem text-lg hover:text-primary"><NuxtLink to="/">Strona Główna</NuxtLink></li>
            <li class="color-black p-4 ml-1rem mr-1rem text-lg"><NuxtLink to="/profile">Ustawienia konta</NuxtLink></li>
            <li class="color-black p-4 ml-1rem mr-1rem text-lg mr-auto"><NuxtLink to="/announcement">Ogloszenie</NuxtLink></li>

            <template v-if="!auth.isLoggedIn">
              <li class="color-black p-4 ml-1rem mr-1rem text-lg"><NuxtLink to="/login">Zaloguj się</NuxtLink></li>
              <li class="color-black p-4 ml-1rem mr-1rem text-lg"><NuxtLink to="/register">Załóż Konto</NuxtLink></li>
            </template>

            <li v-else @click="auth.logout()" class="color-black p-4 ml-1rem mr-1rem text-lg">
              <a>Wyloguj się</a>
            </li>
          </ul>
        </div>

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
