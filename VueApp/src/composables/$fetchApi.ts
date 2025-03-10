type $fetchType = typeof $fetch

export const $fetchAPI: $fetchType = (path, opts) => {
    const url = useRuntimeConfig().public.NUXT_APP_BASE_URL + path
    return $fetch(url, opts);
}
