<script setup lang="ts">
// import type {Order} from "~/types";

const route = useRoute()
const auth = useAuthStore()
const {data: opinions} = await useFetchAPI<any[]>('/api/opinions/' + route.params.id, {
  headers: {Authorization: 'Bearer ' + auth.token}
});

watchEffect(() => {
  if (opinions.value) {
    console.log(opinions.value)
  }
})
</script>

<template>
  <p class="text-2xl color-black">{{ opinions }}</p>

</template>
