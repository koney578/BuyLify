<script setup lang="ts">
const auth = useAuthStore()
if (!auth.isLoggedIn) {
  const router = useRouter()
  router.push('/auth/login')
}
</script>

<template>
  <NuxtPage />
</template>
