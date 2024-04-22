<script setup>
import {Dialog, DialogPanel, DialogTitle, TransitionChild, TransitionRoot} from '@headlessui/vue'

const menuStore = useSlideMenuStore();
const auth = useAuthStore();
</script>

<template>
  <TransitionRoot as="template" :show="menuStore.isMenuOpen">
    <Dialog as="div" class="relative z-10" @close="menuStore.switchSlideMenuState()">
      <TransitionChild as="template" enter="ease-in-out duration-500" enter-from="opacity-0" enter-to="opacity-100"
                       leave="ease-in-out duration-500" leave-from="opacity-100" leave-to="opacity-0">
        <div class="fixed inset-0 bg-gray-500 bg-opacity-75 transition-opacity"/>
      </TransitionChild>

      <div class="fixed inset-0 overflow-hidden">
        <div class="absolute inset-0 overflow-hidden">
          <div class="pointer-events-none fixed inset-y-0 right-0 flex max-w-full pl-10">
            <TransitionChild as="template" enter="transform transition ease-in-out duration-500 sm:duration-700"
                             enter-from="translate-x-full" enter-to="translate-x-0"
                             leave="transform transition ease-in-out duration-500 sm:duration-700"
                             leave-from="translate-x-0" leave-to="translate-x-full">
              <DialogPanel class="pointer-events-auto relative w-screen max-w-md mt-25">
                <div class="flex h-full flex-col overflow-y-scroll bg-white py-6 shadow-xl">
                  <div class="px-4 sm:px-6">
                    <DialogTitle class="text-base font-semibold leading-6 text-gray-900">Nawigacja</DialogTitle>
                  </div>
                  <div class="relative mt-6 flex-1 px-4 sm:px-6">
                    <ul class="items-center w-full">
                      <li class="color-black p-4 ml-1rem mr-1rem text-lg hover:text-gray-500">
                        <NuxtLink to="/">Strona Główna</NuxtLink>
                      </li>
                      <div v-if="auth.isLoggedIn">
                        <li class="color-black p-4 ml-1rem mr-1rem text-lg hover:text-gray-500">
                          <NuxtLink to="/product-add">Dodaj ogłoszenie</NuxtLink>
                        </li>
                        <li class="color-black p-4 ml-1rem mr-1rem text-lg mr-auto hover:text-gray-500">
                          <NuxtLink to="/board">Tablica ogłoszeń</NuxtLink>
                        </li>
                        <li class="color-black p-4 ml-1rem mr-1rem text-lg mr-auto hover:text-gray-500">
                          <NuxtLink to="/auctions">Aukcje</NuxtLink>
                        </li>
                        <li class="color-black p-4 ml-1rem mr-1rem text-lg hover:text-gray-500">
                          <NuxtLink to="/followed-products">Zapisane</NuxtLink>
                        </li>
                        <li class="color-black p-4 ml-1rem mr-1rem text-lg hover:text-gray-500">
                          <NuxtLink to="/my-purchases">Kupione</NuxtLink>
                        </li>
                        <li class="color-black p-4 ml-1rem mr-1rem text-lg mr-auto hover:text-gray-500">
                          <NuxtLink to="/my-sales">Wystawione</NuxtLink>
                        </li>
                      </div>

                      <div v-else>
                        <li class="color-black p-4 ml-1rem mr-1rem text-lg hover:text-gray-500">
                          <NuxtLink to="/login">Zaloguj się</NuxtLink>
                        </li>
                        <li class="color-black p-4 ml-1rem mr-1rem text-lg hover:text-gray-500">
                          <NuxtLink to="/register">Załóż Konto</NuxtLink>
                        </li>
                      </div>
                    </ul>
                  </div>
                </div>
              </DialogPanel>
            </TransitionChild>
          </div>
        </div>
      </div>
    </Dialog>
  </TransitionRoot>
</template>
