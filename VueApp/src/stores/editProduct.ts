interface Product {
    id: number;
    name: string;
    price: number;
    count: number;
    description: string;
    category: Category;
    createdAt: string;
    photo: any;
    user: {
        id: number;
        username: string | null;
        email: string | null;
        averageStars: number | null;
    }
}

interface Category {
    id: number;
    name: string;
}

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