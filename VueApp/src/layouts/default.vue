<script setup lang="ts">

const auth = useAuthStore()
const menuStore = useSlideMenuStore()
const cartStore = useCartStore()
const notificationStore = useNotificationStore()
const followedProductsStore = useFollowedProducts()



const watchNotifications = async () => {
  if (auth.isLoggedIn) {
    await notificationStore.fetchNotifications()
  }
}

onMounted(() => {
  watchNotifications();
  followedProductsStore.fetchFollowedProducts();

  const intervalId = setInterval(() => {
    watchNotifications();
  }, 60000);  // check notifications every minute

  onUnmounted(() => {
    clearInterval(intervalId);
  });
});
</script>

<template>
  <title>BuyLify</title>
  <body class="bg-gray-200 min-h-screen">
  <header class="bg-white p-4 shadow-2xl relative z-[1000]">
    <nav class="items-center flex">
      <NuxtLink to="/">
        <logo />
      </NuxtLink>

      <ul class="ml-auto flex">
        <div v-if="auth.isLoggedIn" class="flex">
          <li class="color-black p-4 md:mx-1rem text-lg hover:text-gray-500">
            <NuxtLink to="/shopping/cart">
              <div class="w-full h-auto relative">
                <Icon name="ic:outline-shopping-cart-checkout" :uncheckedNotificationCount="notificationStore.uncheckedNotificationsCount"
                      class="relative inline-flex items-center text-2rem text-black hover:text-gray-500"/>
                <div v-if="cartStore.cartState.length"
                     class="absolute -right-4 -top-4 inline-flex items-center justify-center w-6 h-6 text-xs font-bold text-white bg-red-500 border-2 border-white rounded-full dark:border-gray-900">
                  {{ cartStore.cartState.length }}
                </div>
              </div>
            </NuxtLink>
          </li>

          <li class="color-black p-4 md:mx-1rem text-lg hover:text-gray-500">
            <NuxtLink to="/my/notifications">
              <div class="w-full h-auto relative">
                <Icon name="mdi:bell-ring-outline" :uncheckedNotificationCount="notificationStore.uncheckedNotificationsCount"
                      class="relative inline-flex items-center text-2rem text-black hover:text-gray-500"/>
                <div v-if="notificationStore.uncheckedNotificationsCount"
                     class="absolute -right-4 -top-4 inline-flex items-center justify-center w-6 h-6 text-xs font-bold text-white bg-red-500 border-2 border-white rounded-full dark:border-gray-900">
                  {{ notificationStore.uncheckedNotificationsCount }}
                </div>
              </div>
            </NuxtLink>
          </li>

          <li class="color-black p-4 md:mx-1rem text-lg hover:text-gray-500">
            <NuxtLink to="/my/profile">
              <Icon name="mdi:account-settings-variant" class="text-2rem"/>
            </NuxtLink>
          </li>

          <li class="color-black p-4 md:mx-1rem text-lg hover:text-gray-500">
            <NuxtLink to="/auth/logout">
              <Icon name="mdi:logout" class="text-2rem" />
            </NuxtLink>
          </li>
        </div>

        <li v-if="!menuStore.isMenuOpen" class="color-black p-4 md:mx-1rem text-lg hover:text-gray-500">
          <Icon name="mdi:menu" class="text-2rem" @click="menuStore.switchSlideMenuState()"/>
        </li>
        <li v-else class="color-black p-4 mx-1rem text-lg hover:text-gray-500">
          <Icon name="heroicons:x-mark" class="text-2rem  animate-bounce-in-up" @click="menuStore.switchSlideMenuState()"/>
        </li>

      </ul>
    </nav>
  </header>

  <slide-menu class=""/>
  <section class="min-h-screen flex flex-col">
    <NuxtPage />
  </section>
  <footer class="bg-gray-50 p-2rem shadow-2xl">
    <!--    <p class="color-black">bla bla bla</p>-->
    <!--    <p class="color-black">bla bla bla</p>-->
    <!--    <p class="color-black">bla bla bla</p>-->
  </footer>
  </body>
</template>
