<script setup lang="ts">

import {useAuthStore} from "~/stores/auth";

const auth = useAuthStore()

const address = reactive({
  name: '',
  surname: '',
  phoneNumber: '',
  email: '',
  country: '',
  city: '',
  street: '',
  houseUnitNumber:'',
  postalCode: ''
})

const addressAdd = async () => {

  if (!address.name || !address.surname || !address.phoneNumber || !address.email || !address.country || !address.city
      || !address.houseUnitNumber || !address.postalCode) {
    console.error('Wszystkie pola są wymagane')
    return
  }
  const router = useRouter()
  const data = await $fetch('http://localhost:8080/api/addresses', {
    method: 'POST',
    body: address,
    headers: {Authorization: 'Bearer ' + auth.token}
  }).catch(err => console.error(err.data))
  console.log(address)

  await router.push('/')
}

</script>

<template>

</template>

<style scoped>

</style>