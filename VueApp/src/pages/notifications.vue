<script setup lang="ts">
const notificationStore = useNotificationStore()
notificationStore.fetchNotifications()
</script>

<template>
  <div class="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
    <div class="sm:mx-auto sm:w-full sm:max-w-sm">
      <img class="mx-auto h-auto w-1/2" src="../images/logo-text.png" alt="BuyLify"/>
      <h1 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">Powiadomienia</h1>
    </div>
    <div class="sm:mx-auto sm:w-full sm:max-w-6xl">
      <notification v-for="notification in notificationStore.notificationsList"
                    :key="notification.id"
                    :id="notification.id"
                    :message="notification.message"
                    :createdAt="notification.createdAt"
                    :notificationType="notification.notificationType"
                    :checked="notification.checked"
      />
    </div>
  </div>
</template>
