import type {Product} from "~/types";

export const useBoardProductsStore = defineStore("boardProducts", () => {
    const auth = useAuthStore()
    const boardProducts = ref<Product[]>([])
    const category = ref(0)
    const minPrice = ref('')
    const maxPrice = ref('')
    const name = ref('')

    const fetchBoardProducts = async () => { // TODO ZROBIC REFACTOR DO JEDNEGO ZAPYTANIA
        if (!category.value) {
            const {data: products} = await useFetchAPI<Product[]>('/api/products', {
                headers: {Authorization: 'Bearer ' + auth.token}
            });
            if (products.value) {
                boardProducts.value = products.value
            }
        }

        else {
            const {data: filteredProducts} = await useFetchAPI<Product[]>('/api/products', {
                headers: {Authorization: 'Bearer ' + auth.token},
                query: {
                    category: category.value,
                    minPrice: minPrice.value,
                    maxPrice: maxPrice.value,
                    name: name.value
                }
            });
            if (filteredProducts.value) {
                boardProducts.value = filteredProducts.value
            }
            // console.log(category.value)
            // console.log(filteredProducts.value)
        }
    }



    return {
        boardProducts,
        category,
        fetchBoardProducts,
        minPrice,
        maxPrice,
        name
    }

});
