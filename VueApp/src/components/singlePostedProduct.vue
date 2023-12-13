<script setup lang="ts">

import {useEditProductStore} from "~/stores/editProduct";

interface Category {
  id: number;
  name: string
}

interface User {
  id: number;
  username: string | null;
  email: string | null;
  averageStars: number | null;
}

interface PostedProduct {
  id: number;
  name: string;
  price: number;
  count: number;
  description: string;
  photo: any;
  category: Category;
  createdAt: string;
  user: User;
}

const props = defineProps<PostedProduct>()
// console.log(props)

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
}
const editProductStore = useEditProductStore()
const router = useRouter()
const editProduct = () => {
  editProductStore.setEditProduct(props)

  router.push('/edit-product')
}


</script>

<template>
  <div class="mt-2rem">
    <div class="flex mt-1rem text-gray-900 px-8 py-4 bg-gray-100 shadow-xl">
      <div class="w-1/2">
        <img
            :src="props.photo"
            alt="Główne zdjęcie"
            class="h-auto w-3/4"
        />
      </div>
      <div class="w-full">

        <div class="grid justify-items-center">
          <p class="text-3xl">{{ props.name }}</p>
        </div>

        <div class="flex mt-1rem justify-between">
          <div class="text-xl mr-2rem flex">Cena: <p class="italic ml-1">{{ props.price }} zł</p></div>
          <div class="text-xl ml-2rem flex">Ilość: <p class="italic ml-1">{{ props.count }}</p></div>
        </div>

        <div class="flex mt-2rem justify-between">
          <div class="text-xl flex mr-2rem">Kategoria: <p class="italic ml-1">{{ props.category.name }}</p></div>
          <div class="text-xl flex ml-2rem">Dodano: <p class="italic ml-1">{{formatDateTime(props.createdAt)}}</p></div>
        </div>

        <div class="flex mt-1rem justify-between">
          <div class="text-xl flex">Opis: <p class="italic ml-1 text-justify">{{ props.description }}</p></div>
        </div>

        <div class="flex mt-2rem justify-between">
          <p class="text-xl italic mr-1rem">Ocena: {{ props.user.averageStars }} </p>
        </div>


        <div class="w-full flex mt-2rem justify-center">
          <button type="submit"
                  class="flex w-1/4 justify-center rounded-md bg-indigo-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
                  @click="editProduct()"
          >
            Edytuj ogłoszenie
          </button>
        </div>

      </div>
    </div>
  </div>
</template>