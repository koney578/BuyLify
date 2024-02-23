import type { ComplexUser } from "~/types";

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
    const token = useLocalStorage('auth: token', '')
    const user = useLocalStorage('auth: user', emptyUser)

    const login = async (username: string, password: string) => {
        const data = await $fetch<{ token: string, user: ComplexUser }>('http://localhost:8080/api/login', {
            method: 'POST',
            body: { username, password }
        })
        user.value = data.user
        token.value = data.token
    }

    const register = async (username: string, password: string, email: string) => {
        await $fetch('http://localhost:8080/api/register', { // TODO register niech nie zwraca ani usera ani tokena
            method: 'POST',
            body: { username, password, email }
        })
    }

    const logout = () => {
        token.value = ''
        user.value = emptyUser
    }

    const isLoggedIn = computed(() => token.value !== '')

    return {
        login,
        logout,
        register,
        isLoggedIn,
        token,
        user,
    }
})

if (import.meta.hot) {
    import.meta.hot.accept(acceptHMRUpdate(useAuthStore, import.meta.hot))
}
