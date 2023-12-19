<script setup lang="ts">

const auth = useAuthStore()
const productStore = useProductStore()
const product = productStore.product
const orderStore = useOrderStore()
orderStore.orderStage = 3

const methods = orderStore.methods
const address = orderStore.address

const order = {
  idPaymentMethod: methods?.idPaymentMethod,
  idDeliveryMethod: methods?.idDeliveryMethod,
  idProduct: methods?.idProduct,
  productQuantity: methods?.productQuantity,
  address: {
    name: address?.name,
    surname: address?.surname,
    phoneNumber: address?.phoneNumber,
    email: address?.email,
    country: address?.country,
    city: address?.city,
    street: address?.street,
    houseUnitNumber: address?.houseUnitNumber,
    postalCode: address?.postalCode,
  },
}


const buyProduct = async () => {
  const router = useRouter()

  console.log(ifBid.value)
  if (ifBid.value) {

    console.log({
      price: bidPrice.value,
      idProduct: product?.id,
    })

    const data = await $fetch('http://localhost:8080/api/bids', {
      method: 'POST',
      body: {
        price: bidPrice.value,
        idProduct: product?.id,
      },
      headers: {Authorization: 'Bearer ' + auth.token}
    }).catch(err => console.error(err.data))
  } else {
    console.log(order)
    const data = await $fetch('http://localhost:8080/api/orders', {
      method: 'POST',
      body: order,
      headers: {Authorization: 'Bearer ' + auth.token}
    }).catch(err => console.error(err.data))
  }


  await router.push('/board')
}


interface Bid {
  id: number | null | undefined;
  price: string | null | undefined;
  createdAt: string | null | undefined;
  product: {
    name: string;
    price: string;
    description: string;
    photo: string;
    category: {
      id: number;
      name: string;
    },
    createdAt: string;
    count: 1,
    user: {
      id: number;
      username: string;
      email: string;
      averageStars: number | null;
    },
    id: number;
  } | null | undefined,
}


const bid = reactive<Bid>({
  id: null,
  price: null,
  createdAt: null,
  product: null
})
const ifBid = ref(false)

if (product) {
  try {
    const {data: fetchedBid} = await useFetch<Bid>(
        'http://localhost:8080/api/bids/winning/' + product.id,
        {
          headers: {Authorization: 'Bearer ' + auth.token},
        }
    );
    bid.id = fetchedBid.value?.id
    bid.product = fetchedBid.value?.product
    bid.createdAt = fetchedBid.value?.createdAt
    bid.price = String(fetchedBid.value?.price || product?.price)

    if (product?.auctionEndsAt) {
      ifBid.value = true
    }
  } catch (err: any) {
    console.error(err.data);
    ifBid.value = false
  }
} else {
  ifBid.value = false
}

const newBidPrice = ref(bid.price)
const bidPrice = ref()

const setBidPrice = async () => {
  if (newBidPrice.value && bid.price && +newBidPrice.value <= (+bid.price)) {
    return
  }
  bidPrice.value = newBidPrice.value
}

const formattedTime = ref('loading ...')

const calculateAuctionTime = () => {
  if (product?.auctionEndsAt) {
    const auctionEndTime = Date.parse(product.auctionEndsAt); // Parsowanie daty z product.auctionEndsAt
    const currentTime = Date.now(); // Aktualny czas w milisekundach
    const auctionTime = auctionEndTime - currentTime;
    if (auctionTime < 0) {
      return "Aukcja zakończona"
    }
    return formatTimeDifference(auctionTime);
  }
  return 0;
}

const formatTimeDifference = (milliseconds: number) => {
  const seconds = Math.floor(milliseconds / 1000);
  const minutes = Math.floor(seconds / 60);
  const hours = Math.floor(minutes / 60);
  const days = Math.floor(hours / 24);

  const remainingHours = hours % 24;
  const remainingMinutes = minutes % 60;
  const remainingSeconds = seconds % 60;

  formattedTime.value = `${days}d ${remainingHours}h ${remainingMinutes}m ${remainingSeconds}s`;
  return formattedTime;
};

onMounted(() => {
  calculateAuctionTime();

  const intervalId = setInterval(() => {
    calculateAuctionTime();
  }, 1000);

  onUnmounted(() => {
    clearInterval(intervalId);
  });
});



</script>

<template>
  <div class="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
    <div class="sm:mx-auto sm:w-full sm:max-w-sm">
      <img class="mx-auto h-auto w-1/2" src="../images/logo-text.png" alt="BuyLify"/>
      <h1 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">Jesteś krok od swojego produktu!</h1>
    </div>

    <div class="mt-2rem">
      <div class="flex mt-1rem text-gray-900 px-8 py-4 bg-gray-100 shadow-xl">
        <div class="w-full">
          <img
              :src="product?.photo"
              alt="Główne zdjęcie"
              class=" h-auto w-1/2"
          />

          <div v-if="ifBid" class="mt-2rem">
            <form @submit.prevent="setBidPrice">
              <label for="new-bid-price" class="block text-sm font-medium leading-6 text-gray-900">
                Podbij cenę (w zł)
              </label>
              <div class="mt-2">
                <!--                  <div v-if="productNameError" class="font-semibold text-rose-600">-->
                <!--                    {{ productNameError }}-->
                <!--                  </div>-->
                <input v-model="newBidPrice" id="new-bid-price" name="new-bid-price" type="text"
                       autocomplete="new-bid-price"
                       required=""
                       placeholder="40"
                       class="block rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white p-0.5rem"/>
              </div>

              <div class="mt-2rem">
                <button type="submit"
                        class="flex justify-center rounded-md bg-indigo-600  px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">
                  Zatwierdź
                </button>
              </div>
            </form>
          </div>



        </div>
        <div class="w-full">


          <div class="grid justify-items-center">
            <p class="text-xl italic ml-1rem">Wybrano: {{ order?.productQuantity }}</p>

            <div v-if="ifBid" class="grid justify-items-center">
              <p class="text-xl italic ml-1rem">Aktualna cena aukcyjna: {{ bid.price || product?.price }} zł</p>
              <p class="text-xl italic ml-1rem">Ilość produktu w ogłoszeniu: {{ product?.count }}</p>
              <p v-if="bidPrice" class="text-xl font-bold italic ml-1rem">Podbijasz cenę do: {{ bidPrice }} zł</p>
              <p class="text-2xl italic ml-1rem mt-1rem">Pozostały czas aukcji: {{ calculateAuctionTime() }} </p>
              <p class="text-2xl italic ml-1rem mt-1rem">Łączny koszt: {{ (bidPrice || bid.price || product?.price) * order?.productQuantity }} zł</p>
            </div>
            <div v-else class="grid justify-items-center">
              <p class="text-xl italic ml-1rem">Cena jednostkowa za produkt: {{ product?.price }} zł</p>
              <p class="text-xl italic ml-1rem">Ilość produktu w ogłoszeniu: {{ product?.count }}</p>
              <p class="text-2xl italic ml-1rem mt-1rem">Łączny koszt: {{ product?.price * order?.productQuantity }} zł</p>
            </div>

          </div>
          <div class="grid justify-between mt-2rem">
            <p class="text-2xl">Nazwa: {{ product?.name }}</p>
            <p class="text-2xl mt-2">Opis: {{ product?.description }}</p>
          </div>

        </div>
      </div>
    </div>

    <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
      <form @submit.prevent="buyProduct" class="space-y-6" action="#" method="POST">
        <submit-button />
      </form>

      <other-product-label />

    </div>
  </div>
</template>
