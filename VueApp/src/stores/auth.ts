import type { ComplexUser } from "~/types";
// import {$fetchAPI} from "~/composables/$fetchApi";

const emptyUser: ComplexUser = {
    id: null,
    username: null,
    name: null,
    surname: null,
    phoneNumber: null,
    email: null,
    registrationDate: null,
    roles: null
}

export const useAuthStore = defineStore('auth', () => {
    // const token = useLocalStorage('auth: token', {token: '', expiresDate: new Date().toISOString()})
    const token = useLocalStorage('auth: token', '')
    const user = useLocalStorage('auth: user', emptyUser)

    const login = async (username: string, password: string) => {
        const data = await $fetchAPI<{ token: string, user: ComplexUser }>('/api/login', {
            method: 'POST',
            body: { username, password }
        })
        user.value = data.user

        // const currentDate = new Date();
        // const newDate = new Date(currentDate.getTime() + 2 * 60 * 60 * 1000) // two hours expires
        token.value = data.token
        // token.value = {
        //     token: data.token,
        //     expiresDate: newDate.toISOString()
        // }

    }

    const register = async (username: string, password: string, email: string) => {
        await $fetchAPI('/api/register', { // TODO register niech nie zwraca ani usera ani tokena
            method: 'POST',
            body: { username, password, email }
        })
    }

    const logout = () => {
        // token.value = {
        //     token: '',
        //     expiresDate: new Date().toISOString(),
        // }
        token.value = ''
        user.value = emptyUser
    }

    const isLoggedIn = computed(() => token.value !== '')

    // const ifTokenExpired = () => {
    //     if (token.value.token) {
    //         if (new Date().toISOString() >= token.value.expiresDate) {
    //             logout()
    //         }
    //     }
    // }

    return {
        login,
        logout,
        register,
        isLoggedIn,
        // ifTokenExpired,
        token,
        user
    }
})

if (import.meta.hot) {
    import.meta.hot.accept(acceptHMRUpdate(useAuthStore, import.meta.hot))
}
