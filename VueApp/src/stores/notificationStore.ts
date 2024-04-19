import type { Notification } from "~/types";

export const useNotificationStore = defineStore("notificationstore", () => {
    const uncheckedNotificationsCount = ref(0)
    const notificationsList = ref<Notification[]>([])


    const fetchNotifications = async () => {
        const auth = useAuthStore()
        const {data: notifications} = await useFetchAPI<Notification[]>('/api/notifications', {
            headers: {Authorization: 'Bearer ' + auth.token}
        });

        if (notifications.value) {
            notificationsList.value = notifications.value
        }

        const uncheckedNotifications = (notifications.value ?? []).filter(notification => !notification.checked)
        uncheckedNotificationsCount.value = uncheckedNotifications.length
        return uncheckedNotificationsCount.value
    }



    return {
        fetchNotifications,
        uncheckedNotificationsCount,
        notificationsList,
    }

});
