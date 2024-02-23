<script setup lang="ts">
import { Dialog, DialogPanel, DialogTitle, TransitionChild, TransitionRoot } from '@headlessui/vue'
import type { OpinionModal } from "~/types"

const props = defineProps<OpinionModal>()
const auth = useAuthStore()
const opinion = reactive({
  description: '',
  stars: 0,
  receiverId: props.order.product.user?.id,
  productId: props.order.product.id
})

const setRating = (rating: number) => {
  opinion.stars = rating
}

const addOpinion = async () => {
  if (!(opinion.stars)) {
    console.error('Musisz zaznaczyc ilosc gwiazdek!')
    return
  }

  await $fetch('http://localhost:8080/api/opinions', {
    method: 'POST',
    body: opinion,
    headers: {Authorization: 'Bearer ' + auth.token}
  }).catch(err => console.error(err.data))

  props.closeModal()
}
</script>

<template>
  <TransitionRoot as="template" :show="true">
    <Dialog as="div" class="relative z-10">
      <TransitionChild as="template" enter="ease-out duration-300" enter-from="opacity-0" enter-to="opacity-100"
                       leave="ease-in duration-200" leave-from="opacity-100" leave-to="opacity-0">
        <div class="fixed inset-0 bg-gray-500 bg-opacity-75 transition-opacity"/>
      </TransitionChild>

      <div class="fixed inset-0 z-10 w-screen overflow-y-auto">
        <div class="flex min-h-full items-end justify-center p-4 text-center sm:items-center sm:p-0">
          <TransitionChild as="template" enter="ease-out duration-300"
                           enter-from="opacity-0 translate-y-4 sm:translate-y-0 sm:scale-95"
                           enter-to="opacity-100 translate-y-0 sm:scale-100" leave="ease-in duration-200"
                           leave-from="opacity-100 translate-y-0 sm:scale-100"
                           leave-to="opacity-0 translate-y-4 sm:translate-y-0 sm:scale-95">
            <DialogPanel
                class="relative transform overflow-hidden rounded-lg bg-white text-left shadow-xl transition-all sm:my-8 sm:w-full sm:max-w-lg">
              <div class="bg-white px-4 pb-4 pt-5 sm:p-6 sm:pb-4">
                <div class="sm:flex sm:items-start">
                  <div
                      class="mx-auto flex h-12 w-12 flex-shrink-0 items-center justify-center rounded-full bg-yellow-100 sm:mx-0 sm:h-10 sm:w-10">
                    <Icon name="heroicons:star" class="h-6 w-6 text-yellow-500" aria-hidden="true" />
                  </div>
                  <div class="mt-3 text-center sm:ml-4 sm:mt-0 sm:text-left">
                    <DialogTitle as="h3" class="text-base font-semibold leading-6 text-gray-900">Oceń produkt
                      {{ props.order.product.name }}
                    </DialogTitle>
                    <div class="mt-2">
                      <p class="text-sm text-gray-500">Twoja opinia będzie publiczna i widoczna dla sprzedającego i
                        innych użytkowników</p>
                    </div>
                  </div>
                  <div class="flex items-center">
                    <Icon name="heroicons:star" v-for="rating in [0, 1, 2, 3, 4]" :key="rating" @click="setRating(rating + 1)"
                          :class="[opinion.stars > rating ? 'text-yellow-500' : 'text-gray-200', 'h-5 w-5 flex-shrink-0']"
                          aria-hidden="true"
                    />
                  </div>
                </div>
                <textarea v-model="opinion.description" id="description" name="description" type="text"
                          autocomplete="description"
                          required
                          placeholder="Dodatkowa treść do opinii"
                          class="h-[auto] block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white p-0.5rem mt-1rem"/>
              </div>


              <div class="bg-gray-50 px-4 py-3 sm:flex sm:flex-row-reverse sm:px-6">
                <button type="button"
                        class="inline-flex w-full justify-center rounded-md bg-yellow-500 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-yellow-400 sm:ml-3 sm:w-auto"
                        @click="addOpinion()">Wyślij
                </button>
                <button type="button"
                        class="mt-3 inline-flex w-full justify-center rounded-md bg-white px-3 py-2 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50 sm:mt-0 sm:w-auto"
                        @click="props.closeModal()" ref="cancelButtonRef">Powrót
                </button>
              </div>
            </DialogPanel>
          </TransitionChild>
        </div>
      </div>
    </Dialog>
  </TransitionRoot>
</template>
