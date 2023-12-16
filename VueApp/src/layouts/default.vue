<script setup lang="ts">
import IcBaselineDensityMedium from "~/icons/IcBaselineDensityMedium.vue";
import MdiAccountSettingsVariant from "~/icons/MdiAccountSettingsVariant.vue";
import MdiLogout from "~/icons/MdiLogout.vue";
import MdiBellRing from "~/icons/MdiBellRing.vue";

const auth = useAuthStore()

interface Notification {
  id: number;
  message: string;
  createdAt: string;
  notificationType: {
    id: number;
    name: string;
  }
  checked: boolean
}

const uncheckedNotificationsCount = ref(0)

const watchNotifications = async () => {
  if (auth.isLoggedIn) {
    const {data: notifications} = await useFetch<Notification[]>('http://localhost:8080/api/notifications', {
      headers: {Authorization: 'Bearer ' + auth.token}
    });

    const uncheckedNotifications = (notifications.value ?? []).filter(notification => !notification.checked)
    uncheckedNotificationsCount.value = uncheckedNotifications.length
    return uncheckedNotificationsCount.value
  }
}

onMounted(() => {
  watchNotifications();

  const intervalId = setInterval(() => {
    watchNotifications();
  }, 60000);  // check notifications every minute

  onUnmounted(() => {
    clearInterval(intervalId);
  });
});



const mobileMenuOpen = ref(false)
</script>

<template>
  <title>BuyLify</title>
  <body class="bg-gray-200 min-h-screen">
  <header class="bg-white p-1 shadow-2xl relative z-[1000]">
    <nav class="items-center flex">
      <ul class="hidden md:flex items-center w-full">
        <li class="color-black p-4 ml-1rem mr-1rem text-2rem">
          <NuxtLink to="/">
            <img src="../images/logo.png" alt="logo" class="color-fuchsia w-16"/>
          </NuxtLink>
        </li>

        <li class="color-black p-4 ml-1rem mr-1rem text-lg hover:text-gray-500">
          <NuxtLink to="/">Strona Główna</NuxtLink>
        </li>
        <li class="color-black p-4 ml-1rem mr-1rem text-lg hover:text-gray-500">
          <NuxtLink to="/productAdd">Dodaj ogłoszenie</NuxtLink>
        </li>
        <li class="color-black p-4 ml-1rem mr-1rem text-lg mr-auto hover:text-gray-500">
          <NuxtLink to="/board">Tablica ogłoszeń</NuxtLink>
        </li>

        <div v-if="auth.isLoggedIn" class="flex">
          <li class="color-black p-4 ml-1rem mr-1rem text-lg hover:text-gray-500">
            <NuxtLink to="/followed-products">Zapisane</NuxtLink>
          </li>
          <li class="color-black p-4 ml-1rem mr-1rem text-lg hover:text-gray-500">
            <NuxtLink to="/myPurchases">Kupione</NuxtLink>
          </li>
          <li class="color-black p-4 ml-1rem mr-1rem text-lg mr-auto hover:text-gray-500">
            <NuxtLink to="/mySales">Wystawione</NuxtLink>
          </li>
          <li class="color-black p-4 ml-1rem mr-1rem text-lg hover:text-gray-500">
            <NuxtLink to="/profile">
              <MdiAccountSettingsVariant />
            </NuxtLink>
          </li>

          <li class="color-black p-4 ml-1rem mr-1rem text-3xl hover:text-gray-500">
            <NuxtLink to="/notifications">
              <div class="w-full h-auto relative">
                <MdiBellRing :uncheckedNotificationCount="uncheckedNotificationsCount"/>
                <div v-if="uncheckedNotificationsCount" class="absolute -right-4 -top-4 inline-flex items-center justify-center w-6 h-6 text-xs font-bold text-white bg-red-500 border-2 border-white rounded-full dark:border-gray-900">{{ uncheckedNotificationsCount }}</div>
              </div>
            </NuxtLink>
          </li>

          <li @click="auth.logout()" class="color-black p-4 ml-1rem mr-1rem text-lg hover:text-gray-500">
            <a>
              <mdi-logout/>
            </a>
          </li>
        </div>

        <template v-else>
          <li class="color-black p-4 ml-1rem mr-1rem text-lg hover:text-gray-500">
            <NuxtLink to="/login">Zaloguj się</NuxtLink>
          </li>
          <li class="color-black p-4 ml-1rem mr-1rem text-lg hover:text-gray-500">
            <NuxtLink to="/register">Załóż Konto</NuxtLink>
          </li>
        </template>

      </ul>

      <div class="flex items-center md:hidden w-full px-4">
        <NuxtLink to="/">
          <img src="../images/logo.png" alt="logo" class="color-fuchsia w-16"/>
        </NuxtLink>

        <div @click="mobileMenuOpen = !mobileMenuOpen" class="relative ml-auto">
          <div class="text-black">
            <IcBaselineDensityMedium/>
          </div>

          <ul v-if="mobileMenuOpen" class="fixed inset-0 top-19 bg-gray-200">
            <li class="color-black p-4 ml-1rem mr-1rem text-lg hover:text-gray-500">
              <NuxtLink to="/">Strona Główna</NuxtLink>
            </li>
            <li class="color-black p-4 ml-1rem mr-1rem text-lg hover:text-gray-500">
              <NuxtLink to="/profile">Ustawienia konta</NuxtLink>
            </li>
            <li class="color-black p-4 ml-1rem mr-1rem text-lg hover:text-gray-500">
              <NuxtLink to="/productAdd">Dodaj ogłoszenie</NuxtLink>
            </li>
            <li class="color-black p-4 ml-1rem mr-1rem text-lg hover:text-gray-500">
              <NuxtLink to="/board">Tablica ogłoszeń</NuxtLink>
            </li>

            <div v-if="auth.isLoggedIn">
              <li class="color-black p-4 ml-1rem mr-1rem text-lg hover:text-gray-500">
                <NuxtLink to="/followed-products">Zapisane</NuxtLink>
              </li>
              <li class="color-black p-4 ml-1rem mr-1rem text-lg hover:text-gray-500">
                <NuxtLink to="/myPurchases">Kupione</NuxtLink>
              </li>
              <li class="color-black p-4 ml-1rem mr-1rem text-lg hover:text-gray-500">
                <NuxtLink to="/mySales">Wystawione</NuxtLink>
              </li>
              <li class="color-black p-4 ml-1rem mr-1rem text-lg hover:text-gray-500">
                <NuxtLink to="/profile">Ustawienia konta</NuxtLink>
              </li>
              <li class="color-black p-4 ml-1rem mr-1rem text-lg hover:text-gray-500">
                <NuxtLink to="/notifications">Powiadomienia</NuxtLink>
              </li>
              <li @click="auth.logout()" class="color-black p-4 ml-1rem mr-1rem text-lg">
                <a>Wyloguj się</a>
              </li>
            </div>

            <template v-else>
              <li class="color-black p-4 ml-1rem mr-1rem text-lg hover:text-gray-500">
                <NuxtLink to="/login">Zaloguj się</NuxtLink>
              </li>
              <li class="color-black p-4 ml-1rem mr-1rem text-lg hover:text-gray-500">
                <NuxtLink to="/register">Załóż Konto</NuxtLink>
              </li>
            </template>

          </ul>
        </div>

      </div>

    </nav>
  </header>
  <section class="min-h-screen flex flex-col">
    <nuxt-page/>
  </section>
  <footer class="bg-gray-50 p-2rem shadow-2xl">
    <p class="color-black">bla bla bla</p>
    <p class="color-black">bla bla bla</p>
    <p class="color-black">bla bla bla</p>
  </footer>
  </body>
</template>
