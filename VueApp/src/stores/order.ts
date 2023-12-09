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

interface Methods {
    idPaymentMethod: number;
    idDeliveryMethod: number;
    idProduct: number | undefined;
    productQuantity: number;
}

interface Order {
    idPaymentMethod: number;
    idDeliveryMethod: number;
    idProduct: number | undefined;
    productQuantity: number;
    address: Address;
}

export const useOrderStore = defineStore({
    id: 'productStore',
    state: () => ({
        order: null as Order | null,
        address: null as Address | null,
        methods: null as Methods | null,
        orderStage: null as Number | null,
    }),
    actions: { // TODO naprawić metody i używać ich zamiast przypisywania danych bezposrednio
        setOrder(order: Order) {
            this.order = order;
        },
        setAddress(address: Address) {
            this.address = address;
        },
        setMethods(methods: Methods) {
            this.methods = methods
        },
        setOrderStage(orderStage: number) {
            this.orderStage = orderStage
        }
    },
});