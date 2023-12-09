<script setup lang="ts">

import {useAuthStore} from "~/stores/auth";

const auth = useAuthStore()
const router = useRouter()

if (auth.user.roles) {
  const adminRole = { authority: "administrator" }
  const ifAdmin = auth.user.roles.some((item: { authority: string; }) => item.authority === adminRole.authority)

  if (!ifAdmin) {
    router.push('/')
  }
}
else {
  router.push('/')
}

const newCategory = reactive({
  name: '',
})

const newPaymentMethod = reactive({
  name: '',
})

const newDeliveryMethod = reactive({
  name: '',
})

let categoryNameError = '';
let paymentMethodNameError = '';
let deliveryMethodError = '';

function validateCategoryName() {
  if (newCategory.name !== "") {
    if (newCategory.name.length < 3) {
      categoryNameError = "Nazwa musi składać się przynajmniej z 3 znaków!";
      return false;
    } else {
      categoryNameError = '';
      return true;
    }
  } else {
    categoryNameError = '';
  }
}

function validatePaymentMethodName() {
  if (newPaymentMethod.name !== "") {
    if (newPaymentMethod.name.length < 3) {
      paymentMethodNameError = "Nazwa musi składać się przynajmniej z 3 znaków!";
      return false;
    } else {
      paymentMethodNameError = '';
      return true;
    }
  } else {
    paymentMethodNameError = '';
  }
}

function validateDeliveryMethodName() {
  if (newDeliveryMethod.name !== "") {
    if (newDeliveryMethod.name.length < 3) {
      deliveryMethodError = "Nazwa musi składać się przynajmniej z 3 znaków!";
      return false;
    } else {
      deliveryMethodError = '';
      return true;
    }
  } else {
    deliveryMethodError = '';
  }
}

watch(newCategory, () => {
  validateCategoryName()
});

watch(newPaymentMethod, () => {
  validatePaymentMethodName()
});

watch(newDeliveryMethod, () => {
  validateDeliveryMethodName()
});


const addCategory = async () => {
  if (!validateCategoryName()) {
    console.error('To pole jest nie prawidłowe')
    return
  }

  const router = useRouter()

  const data = await $fetch('http://localhost:8080/api/categories', {
    method: 'POST',
    body: newCategory,
    headers: {Authorization: 'Bearer ' + auth.token}
  }).catch(err => console.error(err.data))

  await router.push('')
}

const addPaymentMethod = async () => {
  if (!validatePaymentMethodName()) {
    console.error('To pole jest nie prawidłowe')
    return
  }

  const router = useRouter()

  const data = await $fetch('http://localhost:8080/api/payment-methods', {
    method: 'POST',
    body: newPaymentMethod,
    headers: {Authorization: 'Bearer ' + auth.token}
  }).catch(err => console.error(err.data))

  await router.push('')
}

const addDeliveryMethod = async () => {
  if (!validateDeliveryMethodName()) {
    console.error('To pole jest nie prawidłowe')
    return
  }

  const router = useRouter()

  const data = await $fetch('http://localhost:8080/api/delivery-methods', {
    method: 'POST',
    body: newDeliveryMethod,
    headers: {Authorization: 'Bearer ' + auth.token}
  }).catch(err => console.error(err.data))

  await router.push('')
}
</script>

<template>
  <div class="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
    <div class="sm:mx-auto sm:w-full sm:max-w-sm">
      <img class="mx-auto h-auto w-1/2" src="../images/logo-text.png" alt="BuyLify"/>
      <h1 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">Dodaj kategorię</h1>
    </div>

    <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">

      <form @submit.prevent="addCategory" class="space-y-6" action="#" method="POST">
        <div>
          <label for="category-name" class="block text-sm font-medium leading-6 text-gray-900">
            Wpisz nazwę kategorii
          </label>
          <div class="mt-2">
            <div v-if="categoryNameError" class="font-semibold text-rose-600">
              {{ categoryNameError }}
            </div>
            <input v-model="newCategory.name" id="category-name" name="category-name" type="text"
                   autocomplete="category-name"
                   required=""
                   placeholder="Kategoria"
                   class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white p-0.5rem"/>
          </div>
        </div>

        <submit-button />

      </form>
    </div>


    <div class="sm:mx-auto sm:w-full sm:max-w-sm">
      <h1 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">Dodaj metodę
        płatności</h1>
    </div>

    <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">

      <form @submit.prevent="addPaymentMethod" class="space-y-6" action="#" method="POST">
        <div>
          <label for="payment-name" class="block text-sm font-medium leading-6 text-gray-900">
            Wpisz nazwę metody
          </label>
          <div class="mt-2">
            <div v-if="paymentMethodNameError" class="font-semibold text-rose-600">
              {{ paymentMethodNameError }}
            </div>
            <input v-model="newPaymentMethod.name" id="payment-name" name="payment-name" type="text"
                   autocomplete="payment-name"
                   required=""
                   placeholder="Metoda płatności"
                   class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white p-0.5rem"/>
          </div>
        </div>

        <submit-button />

      </form>
    </div>


    <div class="sm:mx-auto sm:w-full sm:max-w-sm">
      <h1 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">Dodaj sposób dostawy</h1>
    </div>

    <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">

      <form @submit.prevent="addDeliveryMethod" class="space-y-6" action="#" method="POST">
        <div>
          <label for="delivery-name" class="block text-sm font-medium leading-6 text-gray-900">
            Wpisz nazwę sposobu dostawy
          </label>
          <div class="mt-2">
            <div v-if="deliveryMethodError" class="font-semibold text-rose-600">
              {{ deliveryMethodError }}
            </div>
            <input v-model="newDeliveryMethod.name" id="delivery-name" name="delivery-name" type="text"
                   autocomplete="delivery-name"
                   required=""
                   placeholder="Sposób dostawy"
                   class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white p-0.5rem"/>
          </div>
        </div>

        <submit-button />

      </form>
    </div>

  </div>
</template>
