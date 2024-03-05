export const useSlideMenuStore = defineStore("slideMenu", () => {
    const isMenuOpen = ref(false);

    const switchSlideMenuState = () => {
        isMenuOpen.value = !isMenuOpen.value;
    }

    return {
        switchSlideMenuState,
        isMenuOpen,
    }

});
