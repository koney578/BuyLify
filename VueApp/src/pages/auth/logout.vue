<script setup lang="ts">
const auth = useAuthStore();
const router = useRouter();
auth.logout();
router.push("/");
</script>

<template>

</template>

