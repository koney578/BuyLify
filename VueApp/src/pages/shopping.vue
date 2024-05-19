<script setup lang="ts">
const auth = useAuthStore()
if (!auth.isLoggedIn) {
  const router = useRouter()
  router.push('/')
}
</script>

<template>
<NuxtPage />
</template>
