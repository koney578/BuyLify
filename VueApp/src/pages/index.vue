<script setup lang="ts">
const auth = useAuthStore()
if (!auth.isLoggedIn) {
  const router = useRouter()
  router.push('/auth/login')
}
</script>

<template>
  <div class="flex items-center max-md:justify-center max-md:flex-col">
    <div class="w-1/2 md:ml-10rem">
      <h1 class="color-amber-600 font-bold text-4xl">Znajdź coś dla siebie!</h1>
      <p class="color-amber-600 font-bold text-2xl">U nas masz najlepszy wybor!</p>

      <div class="max-w-2xl w-full mt-2rem flex">
        <NuxtLink to="/auctions">
          <button
              class="justify-center rounded-md bg-indigo-600 px-6 py-3 text-lg font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">
            Licytuj
          </button>
        </NuxtLink>
        <NuxtLink to="/shopping">
          <button type="submit"
                  class="justify-center rounded-md bg-indigo-600 px-6 py-3 ml-2rem text-lg font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">
            Kup teraz
          </button>
        </NuxtLink>
      </div>
    </div>
    <div class="w-1/2 mt-5rem">
        <img src="../images/i-just-wanna-shop.png" alt="I just wanna Shop" class="color-fuchsia min-w-[420px] max-w-3/4 h-auto animate-zoom-in-down"/>
    </div>
  </div>
</template>
