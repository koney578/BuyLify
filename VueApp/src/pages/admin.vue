<script setup lang="ts">
const auth = useAuthStore()
const router = useRouter()
if (auth.user.roles) {
  const adminRole = { authority: "administrator" }
  const ifAdmin = auth.user.roles.some((item: { authority: string; }) => item.authority === adminRole.authority)

  if (!ifAdmin) {
    router.push('/')
  }
}
else {
  router.push('/')
}

</script>

<template>
  <NuxtPage />
</template>
