<script setup lang="ts">

const user = reactive({
  username: '',
  email: '',
  password: '',
  password2: ''
})

const register = async () => {

  if (!user.username || !user.email || !user.password || !user.password2) {
    console.error('Wszystkie pola są wymagane')
    return
  }

  if (user.password !== user.password2) {
    console.error('Hasło i powtórzone hasło nie są takie same')
    return
  }
  const router = useRouter()
  const data = await $fetch('http://localhost:8080/api/register', {
    method: 'POST',
    body: user
  }).catch(err => console.error(err.data))
  await router.push('/login')
}
</script>

<template>

  <div class="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
    <div class="sm:mx-auto sm:w-full sm:max-w-sm">
      <img class="mx-auto h-10 w-auto" src="https://tailwindui.com/img/logos/mark.svg?color=indigo&shade=600"
           alt="Your Company"/>
      <h2 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">Zarejestruj się</h2>
    </div>

    <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
      <form @submit.prevent="register" class="space-y-6" action="" method="POST">
        <div>
          <label for="username" class="block text-sm font-medium leading-6 text-gray-900">Nazwa użytkownika</label>
          <div class="mt-2">
            <input v-model="user.username" id="username" name="username" type="text" autocomplete="" required=""
                   class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white p-0.5rem"/>
          </div>
        </div>

        <div>
          <label for="email" class="block text-sm font-medium leading-6 text-gray-900">Adres email</label>
          <div class="mt-2">
            <input v-model="user.email" id="email" name="email" type="email" autocomplete="email" required=""
                   class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white p-0.5rem"/>
          </div>
        </div>

        <div>
          <div class="flex items-center justify-between">
            <label for="password" class="block text-sm font-medium leading-6 text-gray-900">Hasło</label>

          </div>
          <div class="mt-2">
            <input v-model="user.password" id="password" name="password" type="password" autocomplete="new-password" required=""
                   class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white p-0.5rem"/>
          </div>
        </div>

        <div>
          <div class="flex items-center justify-between">
            <label for="password2" class="block text-sm font-medium leading-6 text-gray-900">Powtórz Hasło</label>

          </div>
          <div class="mt-2">
            <input v-model="user.password2" id="password2" name="password2" type="password" autocomplete="new-password" required=""
                   class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white p-0.5rem"/>
          </div>
        </div>

        <div>
          <button type="submit"
                  class="flex w-full justify-center rounded-md bg-indigo-600  px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">
            Zarejestruj się
          </button>
        </div>
      </form>

      <p class="mt-10 text-center text-sm text-gray-500">
        Posiadasz konto?
        {{ ' ' }}
        <a href="/login" class="font-semibold leading-6 text-indigo-600 hover:text-indigo-500">Zaloguj się</a>
      </p>
    </div>
  </div>
</template>