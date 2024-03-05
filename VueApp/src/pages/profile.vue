<script setup>
const auth = useAuthStore()

const user = reactive({
  username: '',
  // description: '',  TODO obecnie user nie posiada opisu, mozna dodac w przyszlosci, bo jest juz template
  name: '',
  surname: '',
  email: '',
  phoneNumber: '',
})

const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
function validateEmail() {
  return emailRegex.test(user.email);
}

function validatePhoneNumber() {
  return user.phoneNumber.length === 9;
}

function validateUsername() {
  return user.username.length > 5;
}

const rejectChanges = () => {
  const router = useRouter()
  router.push('/')
}


const ifSubscribedVariable = ref(true)


const ifSubscribed = () => {
  if (auth.user.roles.some(role => role.authority === 'premium')) { // TODO potestowac czy dziala po uproszczeniu ifa
    ifSubscribedVariable.value = true
  } else {
    ifSubscribedVariable.value = false
  }

}

ifSubscribed()



const subscribe = async () => {
  await $fetchAPI('/api/users/type/premium', {
    method: 'PATCH',
    headers: {Authorization: 'Bearer ' + auth.token}
  }).then(() => {
    auth.user.roles = [{
      authority: 'premium'
    }]
    const router = useRouter()
    router.push('/')

  }).catch(err => console.error(err.data))
}

const editProfile = async () => {
  if (user.username === '' || !validateUsername()) {
    user.username = auth.user.username
  }

  if (user.name === '') {
    user.name = auth.user.name
  }

  if (user.surname === '') {
    user.surname = auth.user.surname
  }

  if (user.email === '' || !validateEmail()) {
    user.email = auth.user.email
  }

  if (user.phoneNumber === '' || !validatePhoneNumber()) {
    user.phoneNumber = auth.user.phoneNumber
  }

  const router = useRouter()

  await $fetchAPI('/api/users', {
    method: 'PUT',
    body: user,
    headers: {Authorization: 'Bearer ' + auth.token}
  }).then(() => {

    if (auth.user.username !== user.username) {
      auth.logout()
    } else {
      auth.user.username = user.username
      auth.user.name = user.name
      auth.user.surname = user.surname
      auth.user.phoneNumber = user.phoneNumber
      auth.user.email = user.email
    }
  }).catch(err => console.error(err.data))

  await router.push('/')


}

</script>

<template>
  <div class="w-2/3 m-auto mt-2rem">
    <form @submit.prevent="editProfile">
      <div class="space-y-12">
        <div class="border-b border-gray-900/10 pb-12">
          <h2 class="text-base font-semibold leading-7 text-gray-900">Profil</h2>
          <p class="mt-1 text-sm leading-6 text-gray-600">Te informacje są wyświetlane publicznie, więc uważaj co udostępniasz.</p>

          <div class="mt-10 grid grid-cols-1 gap-x-6 gap-y-8 sm:grid-cols-6">
            <div class="sm:col-span-4">
              <label for="username" class="block text-sm font-medium leading-6 text-gray-900">Nazwa użytkownika</label>
              <div class="mt-2">
                <div
                    class="flex rounded-md shadow-sm ring-1 ring-inset ring-gray-300 focus-within:ring-2 focus-within:ring-inset focus-within:ring-indigo-600 sm:max-w-md">
                  <span class="flex select-none items-center px-2 text-gray-500 sm:text-sm">buylify.com/</span>
                  <input v-model="user.username" type="text" name="username" id="username" autocomplete="username"
                         class="block flex-1 border-0 bg-transparent py-1.5 pl-1 text-gray-900 placeholder:text-gray-400 focus:ring-0 sm:text-sm sm:leading-6 bg-white"
                         :placeholder=auth.user.username />
                </div>
              </div>
            </div>

            <div class="col-span-full">
              <label for="about" class="block text-sm font-medium leading-6 text-gray-900">Opis</label>
              <div class="mt-2">
              <textarea id="about" name="about" rows="3" placeholder="To nie działa, tylko na pokaz, mozna w przyszlosci podłączyć"
                        class="block w-full rounded-md border-0 px-2 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white"/>
              </div>
              <p class="mt-3 text-sm leading-6 text-gray-600">Napisz kilka zdań o sobie.</p>
            </div>

          </div>
        </div>

        <div class="border-b border-gray-900/10 pb-12">
          <h2 class="text-base font-semibold leading-7 text-gray-900">Dane osobowe</h2>
          <p class="mt-1 text-sm leading-6 text-gray-600">Uzupełnij by przyjemniej korzystać z serwisu.</p>

          <div class="mt-10 grid grid-cols-1 gap-x-6 gap-y-8 sm:grid-cols-6">
            <div class="sm:col-span-3">
              <label for="first-name" class="block text-sm font-medium leading-6 text-gray-900">Imię</label>
              <div class="mt-2">
                <input v-model="user.name" type="text" name="first-name" id="first-name" autocomplete="name"
                       class="block w-full rounded-md border-0 px-2 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white"
                      :placeholder="auth.user.name"
                />
              </div>
            </div>

            <div class="sm:col-span-3">
              <label for="surname" class="block text-sm font-medium leading-6 text-gray-900">Nazwisko</label>
              <div class="mt-2">
                <input v-model="user.surname" type="text" name="surname" id="surname" autocomplete="surname"
                       class="block w-full rounded-md border-0 px-2 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white"
                      :placeholder="auth.user.surname"
                />
              </div>
            </div>

            <div class="sm:col-span-3">
              <label for="email" class="block text-sm font-medium leading-6 text-gray-900">Adres email</label>
              <div class="mt-2">
                <input v-model="user.email" id="email" name="email" type="email" autocomplete="email"
                       class="block w-full rounded-md border-0 px-2 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white"
                       :placeholder="auth.user.email"
                />
              </div>
            </div>

            <div class="sm:col-span-3">
              <label for="phoneNumber" class="block text-sm font-medium leading-6 text-gray-900">Numer telefonu</label>
              <div class="mt-2">
                <input v-model="user.phoneNumber" type="text" name="phoneNumber" id="phoneNumber" autocomplete="phoneNumber"
                       class="block w-full rounded-md border-0 px-2 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 bg-white"
                       :placeholder="auth.user.phoneNumber"
                />
              </div>
            </div>

          </div>
        </div>
        <div v-if="!ifSubscribedVariable" class="border-b border-gray-900/10 pb-12">
          <div class="sm:mx-auto sm:w-full sm:max-w-sm">
            <h1 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">Subskrypcja</h1>
          </div>
          <div class="">
            <button type="button" @click="subscribe"
                    class="flex w-full mt-2rem justify-center rounded-md bg-indigo-600 px-6 py-3 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">
              Kup premium
            </button>
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
