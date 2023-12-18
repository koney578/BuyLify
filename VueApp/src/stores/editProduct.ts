interface Product {
    id: number;
    name: string;
    price: string;
    count: string;
    description: string;
    category: Category;
    createdAt: string;
    auctionEndsAt: Date | string | null;
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