type useFetchType = typeof useFetch

export const useFetchAPI: useFetchType = (path, options = {}) => {
    const auth = useAuthStore()
    const url = useRuntimeConfig().public.NUXT_APP_BASE_URL + path
    return useFetch(url, {
        ...options,
        onResponseError() {
            if (auth.token) {
                auth.logout()
            }
        }
    });
}
