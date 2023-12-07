<script setup lang="ts">
import {
  Dialog,
  DialogPanel,
  RadioGroup,
  RadioGroupLabel,
  RadioGroupOption,
  TransitionChild,
  TransitionRoot,
} from '@headlessui/vue'
import {XMarkIcon} from '@heroicons/vue/24/outline'
import {StarIcon} from '@heroicons/vue/20/solid'
import {useAuthStore} from "~/stores/auth";

interface Category {
  id: number,
  name: string,
}

interface Product {
  id: number,
  name: string,
  price: number,
  count: number,
  description: string,
  category: Category,
  createdAt: Date,
  closeModal: Function,
  photo: any,
}

const props = defineProps<Product>()

const router = useRouter()
const routeToBuyProduct = () => {
  const productCategory = JSON.stringify(props.category)
  const productCreatedAt = JSON.stringify(props.createdAt)
  router.push({
    path: '/buyProduct',
    query: {
      productId: props.id,
      productName: props.name,
      productPrice: props.price,
      productDescription: props.description,
      productCategory: productCategory,
      productCreatedAt: productCreatedAt,
      productPhoto: props.photo,
    }
  })
}


// const auth = useAuthStore()
//
//
// const product = {
//   id: props.id,
//   name: props.name,
//   price: props.price,
//   count: props.count,
//   description: props.description,
//   category: props.category,
//   createdAt: props.createdAt,
//   photo: props.photo,
// }
//
//
// const buyProduct = async () => {
//   const router = useRouter()
//
//   const data = await $fetch('http://localhost:8080/api/buy', {
//     method: 'POST',
//     body: product,
//     headers: {Authorization: 'Bearer ' + auth.token}
//   }).catch(err => console.error(err.data))
//
//   await router.push('/board')
// }
// }


</script>

<template>
  <TransitionRoot as="template" :show=true>
    <Dialog as="div" class="relative z-10">
      <TransitionChild as="template" enter="ease-out duration-300" enter-from="opacity-0" enter-to="opacity-100"
                       leave="ease-in duration-200" leave-from="opacity-100" leave-to="opacity-0">
        <div class="fixed inset-0 hidden bg-gray-500 bg-opacity-75 transition-opacity md:block"/>
      </TransitionChild>

      <div class="fixed inset-0 z-10 w-screen overflow-y-auto">
        <div class="flex min-h-full items-stretch justify-center text-center md:items-center md:px-2 lg:px-4">
          <TransitionChild as="template" enter="ease-out duration-300"
                           enter-from="opacity-0 translate-y-4 md:translate-y-0 md:scale-95"
                           enter-to="opacity-100 translate-y-0 md:scale-100" leave="ease-in duration-200"
                           leave-from="opacity-100 translate-y-0 md:scale-100"
                           leave-to="opacity-0 translate-y-4 md:translate-y-0 md:scale-95">
            <DialogPanel
                class="flex w-full transform text-left text-base transition md:my-8 md:max-w-2xl md:px-4 lg:max-w-4xl">
              <div
                  class="relative flex w-full items-center overflow-hidden bg-white px-4 pb-8 pt-14 shadow-2xl sm:px-6 sm:pt-8 md:p-6 lg:p-8">
                <button type="button"
                        class="absolute right-4 top-4 text-gray-400 hover:text-gray-500 sm:right-6 sm:top-8 md:right-6 md:top-6 lg:right-8 lg:top-8"
                        @click="props.closeModal()">
                  <span class="sr-only">Close</span>
                  <XMarkIcon class="h-6 w-6" aria-hidden="true"/>
                </button>

                <div class="grid w-full grid-cols-1 items-start gap-x-6 gap-y-8 sm:grid-cols-12 lg:gap-x-8">
                  <div class="aspect-h-3 aspect-w-2 overflow-hidden rounded-lg bg-gray-100 sm:col-span-4 lg:col-span-5">
                    <img :src="props.photo" alt="" class="object-cover object-center"/>
                  </div>
                  <div class="sm:col-span-8 lg:col-span-7">
                    <h2 class="text-2xl font-bold text-gray-900 sm:pr-12">{{ props.name }}</h2>

                    <section aria-labelledby="information-heading" class="mt-2">
                      <h3 id="information-heading" class="sr-only">Product information</h3>

                      <p class="text-2xl text-gray-900">{{ props.price }} zł</p>
                    </section>

                    <section aria-labelledby="options-heading" class="mt-10">
                      <h3 id="options-heading" class="sr-only">Product options</h3>

                      <form @submit.prevent="routeToBuyProduct">
                        <div>
                          <h4 class="text-sm font-medium text-gray-900">Ilość: </h4>
                          <p class="text-2xl text-gray-900">{{ props.count }}</p>
                        </div>

                        <div class="mt-10">
                          <div class="items-center justify-between">
                            <h4 class="text-sm font-medium text-gray-900">Opis produktu</h4>
                            <p class="text-2xl text-gray-900">{{ props.description }}</p>
                          </div>
                        </div>

                        <button type="submit"
                                class="mt-6 flex w-full items-center justify-center rounded-md border border-transparent bg-indigo-600 px-8 py-3 text-base font-medium text-white hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2">
                          Add to bag
                        </button>
                      </form>
                    </section>
                  </div>
                </div>
              </div>
            </DialogPanel>
          </TransitionChild>
        </div>
      </div>
    </Dialog>
  </TransitionRoot>
</template>