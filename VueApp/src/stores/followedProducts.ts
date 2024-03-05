import type {Product} from "~/types";
import {$fetchAPI} from "~/composables/$fetchApi";

const auth = useAuthStore()
export const useFollowedProducts = defineStore({
    id: 'followedProductsStore',
    state: () => ({
        followedProducts: [] as Product[],
    }),
    actions: {
        async fetchFollowedProducts() {
            if (auth.isLoggedIn) {
                let fetchedProducts: Product[] = []
                try {
                    const {data} = await useFetchAPI<Product[]>('/api/followed-products', {
                        headers: {Authorization: 'Bearer ' + auth.token}
                    })
                    fetchedProducts = data.value || []
                } catch (error) {
                    console.error('Error fetching saved products', error)
                }

                this.followedProducts = fetchedProducts
            }
        },

        async followProduct(followProduct: {id: number; product: Product}) {
            await $fetchAPI('/api/followed-products', {
                method: 'POST',
                body: followProduct,
                headers: {Authorization: 'Bearer ' + auth.token}
            }).then(() => {
                this.followedProducts.push(followProduct.product)
            }).catch(err => console.error(err.data))
        },

        async unfollowProduct(followProduct: {id: number}) {
            await $fetchAPI('/api/followed-products/' + followProduct.id, {
                method: 'DELETE',
                headers: {Authorization: 'Bearer ' + auth.token}
            }).then(() => {
                this.followedProducts = this.followedProducts.filter(product => product.id !== followProduct.id);
            }).catch(err => console.error(err.data))
        }
    }
});
