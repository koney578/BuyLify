import type { Address, Methods, ComplexOrder } from "~/types"

export const useOrderStore = defineStore({
    id: 'productStore',
    state: () => ({
        order: null as ComplexOrder | null,
        address: null as Address | null,
        methods: null as Methods | null,
        orderStage: null as Number | null,
    }),
    actions: { // TODO naprawić metody i używać ich zamiast przypisywania danych bezposrednio
        setOrder(order: ComplexOrder) {
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
