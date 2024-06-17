import type {Product} from "~/types";

export const useBoardProductsStore = defineStore("boardProducts", () => {
    const auth = useAuthStore()
    const boardProducts = ref<Product[]>([])
    const category = ref(0)
    const minPrice = ref('')
    const maxPrice = ref('')
    const name = ref('')
    const page = ref(1)
    const size = ref(3) // You can change here number of elements on page
    const totalElements = ref(0)

    const fetchBoardProducts = async () => {
        const {data: filteredProducts} = await useFetchAPI<Product[]>('/api/products', {
            headers: {Authorization: 'Bearer ' + auth.token},
            query: {
                category: category.value,
                minPrice: minPrice.value,
                maxPrice: maxPrice.value,
                name: name.value,
                page: page.value,
                size: size.value
            },
        });
        if (filteredProducts.value) {
            totalElements.value = filteredProducts.value.totalElements
            boardProducts.value = filteredProducts.value.content
        }

    }


    return {
        boardProducts,
        category,
        fetchBoardProducts,
        minPrice,
        maxPrice,
        name,
        page,
        size,
        totalElements
    }

});
