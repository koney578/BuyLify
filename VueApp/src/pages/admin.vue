<script setup lang="ts">

import {useAuthStore} from "~/stores/auth";
import {Listbox, ListboxButton, ListboxLabel, ListboxOption, ListboxOptions} from "@headlessui/vue";
import {CheckIcon, ChevronUpDownIcon} from "@heroicons/vue/20/solid";

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

interface notificationType {
  id: number;
  name: string;
}

const noNotification = {
  id: -1,
  name: 'Brak powiadomień'
}

const {data: notificationTypes} = await useFetch<notificationType[]>('http://localhost:8080/api/notification-types', {
  headers: {Authorization: 'Bearer ' + auth.token}
});

const selected = ref<notificationType>(notificationTypes.value?.[0] ?? noNotification)

watch(selected, (newValue) => {
  newNotification.notificationTypeId = newValue.id
})


const newCategory = reactive({
  name: '',
})

const newPaymentMethod = reactive({
  name: '',
})

const newDeliveryMethod = reactive({
  name: '',
})

const newNotificationType = reactive({
  name: '',
})

const newNotification = reactive({
  message: '',
  notificationTypeId: 0,
  userId: 0,
})

let categoryNameError = '';
let paymentMethodNameError = '';
let deliveryMethodError = '';
let notificationTypeError = '';
let messageNotificationError = '';

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

function validateNotificationType() {
  if (newNotificationType.name !== "") {
    if (newDeliveryMethod.name.length < 3) {
      notificationTypeError = "Nazwa musi składać się przynajmniej z 3 znaków!";
      return false;
    } else {
      notificationTypeError = '';
      return true;
    }
  } else {
    notificationTypeError = '';
  }
}

function validateMessageNotification() {
  if (newNotification.message !== "") {
    if (newNotification.message.length < 3) {
      messageNotificationError = "Wiadomość musi składać się przynajmniej z 3 znaków!";
      return false;
    } else {
      messageNotificationError = '';
      return true;
    }
  } else {
    messageNotificationError = '';
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

watch(newNotificationType, () => {
  validateNotificationType()
});

watch(newNotification, () => {
  validateMessageNotification()
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


const sendNotification = async () => {
  if (!validateMessageNotification()) {
    console.error('To pole jest nie prawidłowe')
    return
  }

  const router = useRouter()

  const data = await $fetch('http://localhost:8080/api/notifications', {
    method: 'POST',
    body: newNotification,
    headers: {Authorization: 'Bearer ' + auth.token}
  }).catch(err => console.error(err.data))


  await router.push('')
}

const addNotificationType = async () => {
  if (!validateMessageNotification()) {
    console.error('To pole jest nie prawidłowe')
    return
  }

  const router = useRouter()

  const data = await $fetch('http://localhost:8080/api/notification-types', {
    method: 'POST',
    body: newNotification,
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
            Wpisz nazwę metody płatności
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


    <div class="sm:mx-auto sm:w-full sm:max-w-sm">
      <h1 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">Dodaj typ powiadomienia</h1>
    </div>

    <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">

      <form @submit.prevent="addNotificationType" class="space-y-6" action="#" method="POST">
        <div>
          <label for="notification-name" class="block text-sm font-medium leading-6 text-gray-900">
            Wpisz nazwę sposobu dostawy
          </label>
          <div class="mt-2">
            <div v-if="notificationTypeError" class="font-semibold text-rose-600">
              {{ notificationTypeError }}
            </div>
            <input v-model="newNotificationType.name" id="notification-name" name="notification-name" type="text"
                   autocomplete="notification-name"
                   required=""
                   placeholder="typ powiadomienia - bez backendu nie zadziala"
                   class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white p-0.5rem"/>
          </div>
        </div>

        <submit-button />

      </form>
    </div>


    <div class="sm:mx-auto sm:w-full sm:max-w-sm">
      <h1 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">Nadaj powiadomienie do użytkowników</h1>
    </div>

    <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">

      <form @submit.prevent="sendNotification" class="space-y-6" action="#" method="POST">
        <div>
          <label for="notification-message" class="block text-sm font-medium leading-6 text-gray-900">
            Wpisz treść powiadomienia
          </label>
          <div class="mt-2">
            <div v-if="messageNotificationError" class="font-semibold text-rose-600">
              {{ messageNotificationError }}
            </div>
            <input v-model="newNotification.message" id="notification-message" name="notification-message" type="text"
                   autocomplete="notification-message"
                   required=""
                   placeholder="treść powiadomienia"
                   class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white p-0.5rem"/>
          </div>


          <div>
            <label for="notification-user" class="block text-sm font-medium leading-6 text-gray-900">
              Wpisz id usera do którego ma dotrzeć powiadomienie
            </label>
            <div class="mt-2">
<!--              <div v-if="messageNotificationError" class="font-semibold text-rose-600">-->
<!--                {{ messageNotificationError }}-->
<!--              </div>-->
              <input v-model="newNotification.userId" id="notification-user" name="notification-user" type="text"
                     autocomplete="notification-user"
                     required=""
                     placeholder="81"
                     class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white p-0.5rem"/>
            </div>
          </div>


          <Listbox as="div" v-model="selected">
            <ListboxLabel class="block mt-1rem text-sm font-medium leading-6 text-gray-900">Typ powiadomienia</ListboxLabel>
            <div class="relative mt-2">
              <ListboxButton
                  class=" relative w-full cursor-default rounded-md bg-white py-1.5 pl-3 pr-10 text-left text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 focus:outline-none focus:ring-2 focus:ring-indigo-500 sm:text-sm sm:leading-6">
              <span class="flex items-center">
                <span class="block truncate">{{ selected.name }}</span>
              </span>
                <span class="pointer-events-none absolute inset-y-0 right-0 ml-3 flex items-center pr-2">
                <ChevronUpDownIcon class="h-5 w-5 text-gray-400" aria-hidden="true"/>
              </span>
              </ListboxButton>

              <transition leave-active-class="transition ease-in duration-100" leave-from-class="opacity-100"
                          leave-to-class="opacity-0">
                <ListboxOptions
                    class="absolute z-10 mt-1 max-h-56 w-full overflow-auto rounded-md bg-white py-1 text-base shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none sm:text-sm">
                  <option disabled value="" class="px-2 py-1">Wybierz powiadomienie</option>
                  <ListboxOption as="template" v-for="notificationType in notificationTypes" :value="notificationType" :key="notificationType.id"
                                 v-slot="{ active, selected }">
                    <li :class="[active ? 'bg-indigo-600 text-white' : 'text-gray-900', 'relative cursor-default select-none py-2 pl-3 pr-9']">
                      <div class="flex items-center">
                        <span :class="[selected ? 'font-semibold' : 'font-normal', 'block truncate']">{{ notificationType.name }}</span>
                      </div>
                      <span v-if="selected"
                            :class="[active ? 'text-white' : 'text-indigo-600', 'absolute inset-y-0 right-0 flex items-center pr-4']">
                      <CheckIcon class="h-5 w-5" aria-hidden="true"/>
                    </span>
                    </li>
                  </ListboxOption>
                </ListboxOptions>
              </transition>
            </div>
          </Listbox>



        </div>

        <submit-button />

      </form>
    </div>

  </div>
</template>
