interface User {

}

export const useAuthStore = defineStore('auth', () => {
    const token = useLocalStorage('auth: token', '')

    const login = async (username: string, password: string) => {
        const data = await $fetch<{ token: string, user: User }>('http://localhost:8080/api/login', {
            method: 'POST',
            body: { username, password }
        })

        token.value = data.token
    }

    const register = async (username: string, password: string, email: string) => {
        const data = await $fetch<{ token: string, user: User }>('http://localhost:8080/api/register', {
            method: 'POST',
            body: { username, password, email }
        })

        token.value = data.token
    }

    const logout = () => {
        token.value = ''
    }

    const isLoggedIn = computed(() => token.value !== '')

    return {
        login,
        logout,
        register,
        isLoggedIn,
        token,
    }
})

if (import.meta.hot) {
    import.meta.hot.accept(acceptHMRUpdate(useAuthStore, import.meta.hot))
}
