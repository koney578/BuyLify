interface User {
    id: number | null;
    username: string | null;
    name: string | null;
    surname: string | null;
    phoneNumber: string | null;
    email: string | null;
    registrationDate: string | null;
    roles: any
}

const emptyUser: User = {
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
        // <User>(emptyUser)

    const login = async (username: string, password: string) => {
        const data = await $fetch<{ token: string, user: User }>('http://localhost:8080/api/login', {
            method: 'POST',
            body: { username, password }
        })

        user.value = data.user
        token.value = data.token
    }

    const register = async (username: string, password: string, email: string) => {
        const data = await $fetch<{ token: string, user: User }>('http://localhost:8080/api/register', {
            method: 'POST',
            body: { username, password, email }
        })

        // token.value = data.token
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
