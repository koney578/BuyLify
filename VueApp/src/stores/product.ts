interface Product {
    id: number;
    name: string;
    price: number;
    count: number;
    description: string;
    category: Category;
    createdAt: Date;
    photo: any;
}

interface Category {
    id: number,
    name: string,
}

interface Address {
    name: string;
    surname: string;
    phoneNumber: string;
    email: string;
    country: string;
    city: string;
    street: string;
    houseUnitNumber: string;
    postalCode: string;
}

export const useProductStore = defineStore({
    id: 'productStore',
    state: () => ({
        product: null as Product | null,
        address: null as Address | null,
    }),
    actions: {
        setProduct(product: Product) {
            this.product = product;
        },
        // setAddress(address: Address) {
        //     this.address = address;
        // },
    },
});