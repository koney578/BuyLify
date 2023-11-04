<script setup lang="ts">
const email = ref('')

const resetPassword = async () => {

  if (!email.value) {
    console.error('Wszystkie pola są wymagane')
    return
  }
  // TODO Gdy nie ma maila w bazie danych wywalic blad

  const router = useRouter()
  const data = await $fetch('http://localhost:8080/api/resetPassword', {
    method: 'POST',
    body: email.value
  }).catch(err => console.error(err.data))
  await router.push('/login')
}
</script>

<template>

  <div class="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
    <div class="sm:mx-auto sm:w-full sm:max-w-sm">
      <img class="mx-auto h-10 w-auto" src="https://tailwindui.com/img/logos/mark.svg?color=indigo&shade=600"
           alt="Your Company"/>
      <h2 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">Zresetuj hasło</h2>
    </div>

    <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
      <form @submit.prevent="resetPassword" class="space-y-6" action="" method="POST">
        <div>
          <label for="email" class="block text-sm font-medium leading-6 text-gray-900">Adres email</label>
          <div class="mt-2">
            <input v-model="email" id="email" name="email" type="email" autocomplete="email" required=""
                   class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white p-0.5rem"/>
          </div>
        </div>
        <div>
          <button type="submit"
                  class="flex w-full justify-center rounded-md bg-indigo-600  px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">
           Zresetuj hasło
          </button>
        </div>
      </form>

      <p class="mt-10 text-center text-sm text-gray-500">
        Przypomniałeś sobie hasło?
        {{ ' ' }}
        <a href="/login" class="font-semibold leading-6 text-indigo-600 hover:text-indigo-500">Zaloguj się</a>
      </p>
      <p class="mt-10 text-center text-sm text-gray-500">
        Nie masz Konta?
        {{ ' ' }}
        <a href="/register" class="font-semibold leading-6 text-indigo-600 hover:text-indigo-500">Załóż konto</a>
      </p>
    </div>
  </div>
</template>