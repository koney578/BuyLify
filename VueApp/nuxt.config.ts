// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
    srcDir: "src/",
    ssr: false,
    devtools: {enabled: true},
    modules: [
        '@unocss/nuxt',
        '@nuxt/ui',
        '@tailwindcss/forms',
        '@pinia/nuxt',
        '@vueuse/nuxt',
        'nuxt-primevue',
    ],

    runtimeConfig: {
        public: {
            NUXT_APP_BASE_URL: process.env.BASEURL
        }
    },

    pinia: {
        autoImports: [
            'defineStore',
            'acceptHMRUpdate',
        ]
    },
})
