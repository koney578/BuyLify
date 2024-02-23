import type { Product } from "~/types";

export const useEditProductStore = defineStore({
    id: 'EditProductStore',
    state: () => ({
        product: null as Product | null,
    }),
    actions: {
        setEditProduct(product: Product) {
            this.product = product;
        },
    },
});
