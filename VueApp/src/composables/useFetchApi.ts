type useFetchType = typeof useFetch
const auth = useAuthStore()

export const useFetchAPI: useFetchType = (path, options = {}) => {
    const url = useRuntimeConfig().public.NUXT_APP_BASE_URL + path
    const headers = {
        ...options.headers,
    };
    return useFetch(url, {
        headers, onResponseError() {
            if (auth.token) {
                auth.logout()
            }
        }
    });
}
