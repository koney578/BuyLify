import type {Product} from "~/types";

export const useBoardProductsStore = defineStore("boardProducts", () => {
    const auth = useAuthStore()
    const boardProducts = ref<Product[]>([])
    const category = ref(0)

    const fetchBoardProducts = async () => {
        if (!category.value) {
            const {data: products} = await useFetchAPI<Product[]>('/api/products', {
                headers: {Authorization: 'Bearer ' + auth.token}
            });
            if (products.value) {
                boardProducts.value = products.value
            }
        }

        else {
            console.log('dupa')
            const {data: filteredProducts} = await useFetchAPI<Product[]>('/api/products', {
                headers: {Authorization: 'Bearer ' + auth.token},
                query: {category: category.value}
            });
            if (filteredProducts.value) {
                boardProducts.value = filteredProducts.value
            }
            console.log(category.value)
            console.log(filteredProducts.value)
        }
    }



    return {
        boardProducts,
        category,
        fetchBoardProducts,
    }

});
