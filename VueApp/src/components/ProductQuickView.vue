<script setup lang="ts">
import {
  Dialog,
  DialogPanel,
  TransitionChild,
  TransitionRoot,
} from '@headlessui/vue'
import {StarIcon, XMarkIcon} from '@heroicons/vue/24/outline'

interface CloseProduct {
  closeModal: Function;
  averageStars: number;
}

const auth = useAuthStore()
const productStore = useProductStore()
const props = defineProps<CloseProduct>()

const router = useRouter()
const routeToBuyProduct = () => {
  router.push('/buyProduct')
}

interface Bid {
  id: number | null | undefined;
  price: string | null | undefined;
  createdAt: string | null | undefined;
  username: string | null | undefined;
}


const product = productStore.product;
const bid = reactive<Bid>({
  id: null,
  price: null,
  createdAt: null,
  username: null
})
const ifBid = ref(false)

if (product?.auctionEndsAt) {
  try {
    const {data: fetchedBid} = await useFetch<Bid>(
        'http://localhost:8080/api/bids/winning/' + product.id,
        {
          headers: {Authorization: 'Bearer ' + auth.token},
        }
    );
    bid.id = fetchedBid.value?.id
    bid.username = fetchedBid.value?.username
    bid.createdAt = fetchedBid.value?.createdAt
    bid.price = fetchedBid.value?.price

    if (bid.id) {
      ifBid.value = true
    }
    // Now you can safely use bid and product here
  } catch (err: any) {
    console.error(err.data);
    ifBid.value = false
    // Handle the error appropriately
  }
} else {
  // Handle the case where product is null
  console.error('Product is null');
  ifBid.value = false
}


const route = useRoute()

const isFollowedProducts = route.path.includes('/followed-products')
const ifFollowed = ref(isFollowedProducts)

const unFollowProduct = async () => {
  const auth = useAuthStore()
  const followProduct = {
    id: productStore.product?.id
  }

  const data = await $fetch('http://localhost:8080/api/followed-products/' + followProduct.id, {
    method: 'DELETE',
    headers: {Authorization: 'Bearer ' + auth.token}
  }).then(() => {
    props.closeModal()
  }).catch(err => console.error(err.data))
}


const followProduct = async () => {
  const auth = useAuthStore()
  const followProduct = {
    id: productStore.product?.id
  }

  const data = await $fetch('http://localhost:8080/api/followed-products', {
    method: 'POST',
    body: followProduct,
    headers: {Authorization: 'Bearer ' + auth.token}
  }).then(() => {
    props.closeModal()
  }).catch(err => console.error(err.data))
}

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
                    <img :src="productStore.product?.photo" alt="" class="object-cover object-center"/>
                  </div>
                  <div class="sm:col-span-8 lg:col-span-7">
                    <h2 class="text-2xl font-bold text-gray-900 sm:pr-12">{{ productStore.product?.name }}</h2>

                    <section aria-labelledby="information-heading" class="mt-2">
                      <h3 id="information-heading" class="sr-only">Product information</h3>
                      <div v-if="ifBid">
                        <h4 class="text-sm font-medium text-gray-900">Cena w licytacji:  </h4>
                        <p  class="text-2xl text-gray-900">{{ bid?.price }} zł</p>
                        <div class="mt-10">
                          <h4 class="text-sm font-medium text-gray-900">W licytacji prowadzi:  </h4>
                          <p  class="text-2xl text-gray-900">{{ bid.username }}</p>
                        </div>
                      </div>
                      <p v-else class="text-2xl text-gray-900">{{ productStore.product?.price }} zł</p>

                    </section>

                    <section aria-labelledby="options-heading" class="mt-10">
                      <h3 id="options-heading" class="sr-only">Product options</h3>

                      <form @submit.prevent="routeToBuyProduct">
                        <div>
                          <h4 class="text-sm font-medium text-gray-900">Ilość: </h4>
                          <p class="text-2xl text-gray-900">{{ productStore.product?.count }}</p>
                        </div>

                        <div class="mt-10">
                          <div class="items-center justify-between">
                            <h4 class="text-sm font-medium text-gray-900">Opis produktu: </h4>
                            <p class="text-2xl text-gray-900">{{ productStore.product?.description }}</p>
                          </div>
                        </div>

                        <div class="mt-2rem">
                          <h4 class="text-sm font-medium text-gray-900">Ocena sprzedającego: </h4>
                          <div class="flex mt-1rem">
                            <p class="text-2xl text-gray-900 mr-2">{{ props.averageStars || 0 }}</p>
                            <StarIcon class="h-7 w-7 text-yellow-500" aria-hidden="true"/>
                          </div>

                        </div>

                        <div v-if="ifBid">
                          <div class="flex w-full">
                            <button type="submit"
                                    class="mt-6 flex w-1/2 mr-1rem items-center justify-center rounded-md border border-transparent bg-indigo-600 px-8 py-3 text-base font-medium text-white hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2">
                              Kup teraz
                            </button>

                            <button type="submit"
                                    class="mt-6 flex w-1/2 ml-1rem items-center justify-center rounded-md border border-transparent bg-indigo-600 px-8 py-3 text-base font-medium text-white hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2">
                              Licytuj
                            </button>
                          </div>
                          <div v-if="!ifFollowed" class="flex w-full">
                            <button type="button"
                                    class="mt-6 flex w-full items-center justify-center rounded-md border border-transparent bg-indigo-600 px-8 py-3 text-base font-medium text-white hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"
                                    @click="followProduct()" ref="cancelButtonRef">Zapisz na później
                            </button>
                          </div>
                          <div v-else class="flex w-full">
                            <button type="button"
                                    class="mt-6 flex w-full items-center justify-center rounded-md border border-transparent bg-indigo-600 px-8 py-3 text-base font-medium text-white hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"
                                    @click="unFollowProduct()" ref="cancelButtonRef">Usuń z obserwowanych
                            </button>
                          </div>
                        </div>

                        <div v-else class="flex w-full">
                          <div class="flex w-full">
                            <button type="submit"
                                    class="mt-6 flex w-1/2 mr-1rem items-center justify-center rounded-md border border-transparent bg-indigo-600 px-8 py-3 text-base font-medium text-white hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2">
                              Kup teraz
                            </button>
                            <div v-if="!ifFollowed">
                              <button type="button"
                                      class="mt-6 flex w-full ml-1rem items-center justify-center rounded-md border border-transparent bg-indigo-600 px-8 py-3 text-base font-medium text-white hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"
                                      @click="followProduct()" ref="cancelButtonRef">Zapisz na później
                              </button>
                            </div>
                            <div v-else class="flex w-full">
                              <button type="button"
                                      class="mt-6 flex w-full ml-1rem items-center justify-center rounded-md border border-transparent bg-indigo-600 px-8 py-3 text-base font-medium text-white hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"
                                      @click="unFollowProduct()" ref="cancelButtonRef">Usuń z obserwowanych
                              </button>
                            </div>
                          </div>
                        </div>

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