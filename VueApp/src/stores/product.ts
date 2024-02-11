import type { Product } from "~/types"

export const useProductStore = defineStore({
    id: 'productStore',
    state: () => ({
        product: null as Product | null,
    }),
    actions: {
        setProduct(product: Product) {
            this.product = product;
        },
    },
});
