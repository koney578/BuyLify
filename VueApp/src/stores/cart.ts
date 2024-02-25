import type { Product, CartStoreState, SellerItem } from "~/types"

export const useCartStore = defineStore({
    id: 'cartStore',
    state: (): CartStoreState => ({
        cartState: [],
        sellerState: [],
    }),
    actions: {
        addProductToCart(product: Product, quantity: number = 1): void {
            const existingItem = this.cartState.find(item => item.product.id === product.id);

            if (existingItem) {
                existingItem.quantity += quantity;
            } else {
                this.cartState.push({ product, quantity });
            }
        },

        removeProductFromCart(productId: number): void {
            this.cartState = this.cartState.filter(item => item.product.id !== productId);
        },

        updateProductQuantity(productId: number, newQuantity: number): void {
            const cartItem = this.cartState.find(item => item.product.id === productId);

            if (cartItem) {
                cartItem.quantity = newQuantity;
            }
        },

        clearCart(): void {
            this.cartState = []
        },

        sortCartByUserId(): void {
            this.cartState.sort((a, b) => {
                const userIdA = a.product.user?.id || 0;
                const userIdB = b.product.user?.id || 0;

                return userIdA - userIdB;
            });
        },

        addSeller(seller: SellerItem): void {
            const existingItem = this.sellerState.find(item => item.user.id === seller.user.id);

            if (existingItem) {
                return;
            } else {
                this.sellerState.push(seller);
            }
        },

        getNumberCompletedSeller(): number {
            return this.sellerState.length
        }
    },
});
