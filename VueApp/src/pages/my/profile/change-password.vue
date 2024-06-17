<script setup lang="ts">
const newPassword = ref('')
const oldPassword = ref('')
const repeatedPassword = ref('')
const passwordError = ref('')

function validatePassword() {
  if (newPassword.value !== "") {
    if (newPassword.value.length < 8) {
      passwordError.value = "Hasło musi mieć co najmniej 8 znaków.";
      return false;
    } else if (!/[A-Z]/.test(newPassword.value)) {
      passwordError.value = "Hasło musi zawierać co najmniej jedną dużą literę.";
      return false;
    } else if (!/\d/.test(newPassword.value)) {
      passwordError.value = "Hasło musi zawierać co najmniej jedną cyfrę.";
      return false;
    } else if (newPassword.value !== repeatedPassword.value) {
      passwordError.value = "Hasła nie są takie same.";
      return false;
    } else {
      passwordError.value = "";
      return true;
    }
  } else {
    passwordError.value = "";
    return false;
  }
}

watch(newPassword, () => {
  validatePassword()
});

watch(repeatedPassword, () => {
  validatePassword()
});

const changePassword = async () => {
  if (!validatePassword()) {
    return
  }

  const body = {
    oldPassword: oldPassword.value,
    newPassword: newPassword.value,
  }

  console.log(body)
  const auth = useAuthStore()

  await $fetchAPI('/api/users/password-reset', {
    method: 'POST',
    body: body,
    headers: {Authorization: 'Bearer ' + auth.token}
  }).then(async () => {
    const router = useRouter()
    await router.push('/auth/logout')
  })
}

const rejectChanges = () => {
  const router = useRouter()
  router.push('/my/profile')
}


</script>

<template>
  <div class="w-2/3 m-auto mt-2rem">
    <form @submit.prevent="changePassword">
      <div class="space-y-14">
        <div class="border-b border-gray-900/10 pb-12">
          <h2 class="text-base font-semibold leading-7 text-gray-900">Zmiana hasła</h2>
          <p class="mt-1 text-sm leading-6 text-gray-600">Wpisz aktualne hasło i nowe na które chcesz zmienić.</p>
        </div>

        <div class="relative border-b border-gray-900/10 pb-12">
          <div class="mt-10 grid grid-cols-1 gap-x-6 gap-y-8 sm:grid-cols-6">

            <div class="sm:col-span-3">
              <label for="current-password" class="block text-sm font-medium leading-6 text-gray-900">Stare hasło</label>
              <div class="mt-2">
                <input v-model="oldPassword" type="password" name="current-password" id="current-password" required autocomplete="current-password"
                       class="block w-full rounded-md border-0 px-2 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white"
                />
              </div>
            </div>
          </div>
          <div class="mt-10 grid grid-cols-1 gap-x-6 gap-y-8 sm:grid-cols-6">

            <div class="sm:col-span-3">
              <label for="password" class="block text-sm font-medium leading-6 text-gray-900">Hasło</label>
              <div class="mt-2">
                <input v-model="newPassword" type="password" name="password" id="password"
                       class="block w-full rounded-md border-0 px-2 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white"
                />
              </div>
            </div>

            <div class="sm:col-span-3">
              <label for="surname" class="block text-sm font-medium leading-6 text-gray-900">Powtórz hasło</label>
              <div class="mt-2">
                <input v-model="repeatedPassword" type="password" name="repeatedPassword" id="repeatedPassword"
                       class="block w-full rounded-md border-0 px-2 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white"
                />
              </div>
            </div>
          </div>

          <div v-if="passwordError" class="font-semibold text-rose-600 absolute bottom-1 left-0">
            {{ passwordError }}
          </div>

        </div>
      </div>


      <div class="mt-6 flex items-center justify-end gap-x-6 mb-2rem">
        <button type="button" @click="rejectChanges" class="text-sm font-semibold leading-6 text-gray-900">Odrzuć</button>
        <button type="submit"
                class="rounded-md bg-indigo-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">
          Zapisz
        </button>
      </div>
    </form>
  </div>
</template>
