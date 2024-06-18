<script setup lang="ts">
import type {Opinion} from "~/types";

const route = useRoute()
const auth = useAuthStore()
const router = useRouter()
const {data: opinions} = await useFetchAPI<Opinion[]>('/api/opinions/' + route.params.id, {
  headers: {Authorization: 'Bearer ' + auth.token}
});

const username = ref('')
watchEffect(() => {
  if (opinions.value) {
    username.value = opinions.value[0]?.product?.user?.username || ''
  }
})
</script>

<template>
  <div class="flex w-full">
    <p v-if="username" class="text-black text-3xl mx-auto mt-1rem">Opinie o użytkowniku {{ username }}</p>
    <div v-else class="text-black text-3xl mx-auto mt-1rem">
      <p>Brak opinii o tym użytkowniku</p>
      <p @click="router.go(-1)" class="mt-10 text-center text-sm text-gray-500 font-semibold leading-6 text-indigo-600 hover:text-indigo-500">Kliknij tutaj by wrócić!</p>
    </div>
  </div>
  <single-opinion v-for="opinion in opinions" :key="opinion.id"
                  :id="opinion.id"
                  :product="opinion.product"
                  :stars="opinion.stars"
                  :created-at="opinion.createdAt"
                  :description="opinion.description"
                  :user-id="opinion.userId"
                  :username="opinion.username"
                  class="mt-2rem mb-2rem"
  />
</template>
