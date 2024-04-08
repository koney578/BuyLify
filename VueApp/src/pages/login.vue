<script setup lang="ts">

const user = reactive({
  username: '',
  password: '',
})

const passwordError = ref('')
const loginError = ref('')

function validatePassword() {
  if (user.password !== "") {
    if (user.password.length < 8) {
      passwordError.value = "Hasło musi mieć co najmniej 8 znaków.";
      return false;
    } else if (!/[A-Z]/.test(user.password)) {
      passwordError.value = "Hasło musi zawierać co najmniej jedną dużą literę.";
      return false;
    } else if (!/\d/.test(user.password)) {
      passwordError.value = "Hasło musi zawierać co najmniej jedną cyfrę.";
      return false;
    } else {
      passwordError.value = "";
      return true;
    }
  }
}

watch(user, () => {
  validatePassword()
});

const login = async () => {
  if (!user.username || !user.password) {
    console.error('Wszystkie pola są wymagane')
    return
  }

  if (!validatePassword()) {
    console.error(passwordError)
    return
  }

  const router = useRouter()
  const auth = useAuthStore()
  await auth.login(user.username, user.password)
      .catch(
          async err => {
            console.error(err.data)
            loginError.value = 'Nie udana próba logowania! Spróbuj ponownie!'
            const errorTime = (ms: number) => new Promise(resolve => setTimeout(resolve, ms));
            await errorTime(2000)
            loginError.value = ''
          }
      )

  if (auth.token) {
    await router.push('/')
  }
}
</script>

<template>
  <div class="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
    <div class="sm:mx-auto sm:w-full sm:max-w-sm">
      <img class="mx-auto h-auto w-1/2" src="../images/logo-text.png" alt="BuyLify"/>
      <h1 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">Zaloguj się</h1>
    </div>

    <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
      <form @submit.prevent="login" class="space-y-6" action="#" method="POST">
        <div>
          <label for="username" class="block text-sm font-medium leading-6 text-gray-900">Nazwa użytkownika</label>
          <div class="mt-2">
            <input v-model="user.username" id="username" name="username" type="text" autocomplete="username" required
                   class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white p-0.5rem"/>
          </div>
        </div>

        <div>
          <div class="flex items-center justify-between">
            <label for="password" class="block text-sm font-medium leading-6 text-gray-900">Hasło</label>
            <div class="text-sm">
              <NuxtLink to="/reset-password" class="font-semibold text-indigo-600 hover:text-indigo-500">
                Zapomniałeś hasła?
              </NuxtLink>
            </div>
          </div>
          <div class="mt-2">
            <div v-if="passwordError" class="font-semibold text-rose-600">
              {{ passwordError }}
            </div>
            <input v-model="user.password" id="password" name="password" type="password" autocomplete="current-password"
                   required
                   class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white p-0.5rem"/>
          </div>
        </div>

        <div>
          <div v-if="loginError" class="font-semibold animate-rubber-band text-rose-600 m-1rem">
            {{ loginError }}
          </div>
          <button type="submit"
                  class="flex w-full justify-center rounded-md bg-indigo-600  px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">
            Zaloguj się
          </button>
        </div>
      </form>

      <p class="mt-10 text-center text-sm text-gray-500">
        Nie masz konta?
        {{ ' ' }}
        <NuxtLink
            to="/register" class="font-semibold leading-6 text-indigo-600 hover:text-indigo-500">Zarejestruj się
        </NuxtLink>
      </p>
    </div>
  </div>
</template>
