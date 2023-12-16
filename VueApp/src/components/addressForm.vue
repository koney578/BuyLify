<script setup lang="ts">


const auth = useAuthStore()
const orderStore = useOrderStore()

const address = reactive({
  name: '',
  surname: '',
  phoneNumber: '',
  email: '',
  country: '',
  city: '',
  street: '',
  houseUnitNumber: '',
  postalCode: ''
})

const user = auth.user
if (user.name) {
  address.name = user.name
}

if (user.surname) {
  address.surname = user.surname
}

if (user.phoneNumber) {
  address.phoneNumber = user.phoneNumber
}

if (user.email) {
  address.email = user.email
}


const addAddress = () => {

  if (!address.name || !address.surname || !address.phoneNumber || !address.email || !address.country || !address.city
      || !address.houseUnitNumber || !address.postalCode) {
    console.error('Wszystkie pola są wymagane')
    return
  }

  orderStore.address = address

  const router = useRouter()

  router.push('/confirmation')
}

</script>

<template>
  <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
    <form @submit.prevent="addAddress" class="space-y-6" action="#" method="POST">
      <div>
        <label for="address-name" class="block text-sm font-medium leading-6 text-gray-900">
          Imię
        </label>
        <div class="mt-2">
<!--          <div v-if="productNameError" class="font-semibold text-rose-600">-->
<!--            {{ productNameError }}-->
<!--          </div>-->
          <input v-model="address.name" id="address-name" name="name" type="text" autocomplete="address-name"
                 required=""
                 placeholder=""
                 class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white p-0.5rem"/>
        </div>
      </div>


      <div>
        <label for="address-surname" class="block text-sm font-medium leading-6 text-gray-900">Nazwisko</label>
<!--        <div v-if="productCountError" class="font-semibold text-rose-600">-->
<!--          {{ productCountError }}-->
<!--        </div>-->
        <div class="mt-2 rounded-md shadow-sm">
          <input v-model="address.surname" type="text" name="surname" id="address-surname" autocomplete="address-surname"
                 class="block w-full rounded-md border-0 text-gray-900 ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white p-0.5rem"
                 placeholder=""
          />
        </div>
      </div>

      <div>
        <label for="address-phone" class="block text-sm font-medium leading-6 text-gray-900">Numer Telefonu</label>
        <!--        <div v-if="productCountError" class="font-semibold text-rose-600">-->
        <!--          {{ productCountError }}-->
        <!--        </div>-->
        <div class="mt-2 rounded-md shadow-sm">
          <input v-model="address.phoneNumber" type="text" name="phone" id="address-phone" autocomplete="address-phone"
                 class="block w-full rounded-md border-0 text-gray-900 ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white p-0.5rem"
                 placeholder=""
          />
        </div>
      </div>

      <div>
        <label for="address-email" class="block text-sm font-medium leading-6 text-gray-900">Adres email</label>
        <!--        <div v-if="productCountError" class="font-semibold text-rose-600">-->
        <!--          {{ productCountError }}-->
        <!--        </div>-->
        <div class="mt-2 rounded-md shadow-sm">
          <input v-model="address.email" type="text" name="email" id="address-email" autocomplete="address-email"
                 class="block w-full rounded-md border-0 text-gray-900 ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white p-0.5rem"
                 placeholder=""
          />
        </div>
      </div>

      <div>
        <label for="address-country" class="block text-sm font-medium leading-6 text-gray-900">Kraj</label>
        <!--        <div v-if="productCountError" class="font-semibold text-rose-600">-->
        <!--          {{ productCountError }}-->
        <!--        </div>-->
        <div class="mt-2 rounded-md shadow-sm">
          <input v-model="address.country" type="text" name="country" id="address-country" autocomplete="address-country"
                 class="block w-full rounded-md border-0 text-gray-900 ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white p-0.5rem"
                 placeholder="Polska"
          />
        </div>
      </div>

      <div>
        <label for="address-city" class="block text-sm font-medium leading-6 text-gray-900">Miasto</label>
        <!--        <div v-if="productCountError" class="font-semibold text-rose-600">-->
        <!--          {{ productCountError }}-->
        <!--        </div>-->
        <div class="mt-2 rounded-md shadow-sm">
          <input v-model="address.city" type="text" name="city" id="address-city" autocomplete="address-city"
                 class="block w-full rounded-md border-0 text-gray-900 ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white p-0.5rem"
                 placeholder="Kraków"
          />
        </div>
      </div>

      <div>
        <label for="address-street" class="block text-sm font-medium leading-6 text-gray-900">Ulica</label>
        <!--        <div v-if="productCountError" class="font-semibold text-rose-600">-->
        <!--          {{ productCountError }}-->
        <!--        </div>-->
        <div class="mt-2 rounded-md shadow-sm">
          <input v-model="address.street" type="text" name="street" id="address-street" autocomplete="address-street"
                 class="block w-full rounded-md border-0 text-gray-900 ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white p-0.5rem"
                 placeholder="Piękna"
          />
        </div>
      </div>

      <div>
        <label for="address-houseUnitNumber" class="block text-sm font-medium leading-6 text-gray-900">Numer domu</label>
        <!--        <div v-if="productCountError" class="font-semibold text-rose-600">-->
        <!--          {{ productCountError }}-->
        <!--        </div>-->
        <div class="mt-2 rounded-md shadow-sm">
          <input v-model="address.houseUnitNumber" type="text" name="house unit" id="address-houseUnitNumber" autocomplete="address-houseUnitNumber"
                 class="block w-full rounded-md border-0 text-gray-900 ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white p-0.5rem"
                 placeholder="2"
          />
        </div>
      </div>

      <div>
        <label for="address-postalCode" class="block text-sm font-medium leading-6 text-gray-900">Kod pocztowy</label>
        <!--        <div v-if="productCountError" class="font-semibold text-rose-600">-->
        <!--          {{ productCountError }}-->
        <!--        </div>-->
        <div class="mt-2 rounded-md shadow-sm">
          <input v-model="address.postalCode" type="text" name="postal code" id="address-postalCode" autocomplete="address-postalCode"
                 class="block w-full rounded-md border-0 text-gray-900 ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white p-0.5rem"
                 placeholder="00-000"
          />
        </div>
      </div>

      <submit-button />

    </form>
  </div>
</template>