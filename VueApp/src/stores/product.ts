interface Product {
    id: number;
    name: string;
    price: number;
    count: number;
    description: string;
    category: Category | null;
    createdAt: string;
    auctionEndsAt: string | null;
    photo: any;
}

interface Category {
    id: number;
    name: string;
}

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