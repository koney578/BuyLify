<script setup lang="ts">
import {useAuthStore} from "~/stores/auth";
import {useProductStore} from "~/stores/product";

const auth = useAuthStore()
const {data: notifications} = await useFetch<Notification[]>('http://localhost:8080/api/notifications', {
  headers: {Authorization: 'Bearer ' + auth.token}
});

interface Notification {
  id: number;
  message: string;
  createdAt: string;
  notificationType: {
    id: number;
    name: string;
  }
  checked: boolean
}


console.log(notifications)

</script>

<template>
  <div class="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
    <div class="sm:mx-auto sm:w-full sm:max-w-sm">
      <img class="mx-auto h-auto w-1/2" src="../images/logo-text.png" alt="BuyLify"/>
      <h1 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">Powiadomienia</h1>
    </div>
    <div class="sm:mx-auto sm:w-full sm:max-w-6xl">
      <notification v-for="notification in notifications"
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