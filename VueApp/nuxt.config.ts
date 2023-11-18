// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
    srcDir: "src/",
    ssr: false,
    devtools: {enabled: true},
    modules: [
        '@unocss/nuxt',
        '@nuxt/ui',
        '@tailwindcss/forms',
        '@pinia/nuxt'
    ],

    pinia: {
        autoImports: [
            'defineStore',
            'acceptHMRUpdate'
        ]
    },
})
