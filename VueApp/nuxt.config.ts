// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
    srcDir: "src/",
    ssr: false,
    devtools: {enabled: true},
    runtimeConfig: {
        public: {
            baseURL: 'http://localhost:8080/',
        },
    },
    modules: [
      '@unocss/nuxt',
      '@nuxt/ui',
      '@tailwindcss/forms',
      '@pinia/nuxt',
      '@vueuse/nuxt',
    ],

    pinia: {
        autoImports: [
            'defineStore',
            'acceptHMRUpdate'
        ]
    },
})
