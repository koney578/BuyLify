<script setup lang="ts">

interface Notification {
  id: number;
  message: string;
  createdAt: string;
  notificationType: {
    id: number;
    name: string;
  }
  isChecked: boolean
}

const props = defineProps<Notification>()

const formatDateTime = (dateTimeString: string) => {
  const options: Intl.DateTimeFormatOptions = {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit'
  };
  const dateTime = new Date(dateTimeString);
  return dateTime.toLocaleString('pl-PL', options);
};

</script>

<template>
  <div class="mt-2rem">
    <div class="flex mt-1rem text-gray-900 px-8 py-4 bg-gray-100 shadow-xl">
      <div class="w-full">

        <div class="grid justify-items-center">
          <p class="text-3xl">{{ props.notificationType.name }}</p>
        </div>

        <div class="flex mt-2rem justify-between">
          <p class="text-xl italic mr-1rem">Wiadomość: {{ props.message }} </p>
        </div>

        <div class="flex mt-2rem justify-between">
          <div class="text-xl mr-2rem flex">Data stworzenia: <p class="italic ml-1">{{ formatDateTime(props.createdAt) }} </p></div>
        </div>

        <div v-if="!props.isChecked" class="w-full flex mt-2rem justify-center">
          <button type="submit"
                  class="flex w-1/4 justify-center rounded-md bg-indigo-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
                  @click=""
          >
            Odczytałem
          </button>
        </div>

        <div v-else class="w-full flex mt-2rem justify-center">
          <button type="submit"
                  class="flex w-1/4 justify-center rounded-md bg-indigo-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
                  @click=""
          >
            Odznacz
          </button>
        </div>

      </div>
    </div>
  </div>
</template>