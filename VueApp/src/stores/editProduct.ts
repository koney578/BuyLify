import type {PostedProduct} from "~/types";

export const useEditProductStore = defineStore({
    id: 'EditProductStore',
    state: () => ({
        product: null as PostedProduct | null,
    }),
    actions: {
        setEditProduct(product: PostedProduct) {
            this.product = product;
        },
    },
});
