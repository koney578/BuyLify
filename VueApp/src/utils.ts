import type {Order, Product, SortedOrder} from "~/types";

export const formatDateTime = (dateTimeString: string) => {
    const options: Intl.DateTimeFormatOptions = {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit'
    };
    const dateTime = new Date(dateTimeString);
    return dateTime.toLocaleString('pl-PL', options);
};

export const calculateAuctionTime = (auctionEndsAt: string) => {
    if (auctionEndsAt) {
        const auctionTime = calculateAuctionTimeExpired(auctionEndsAt);
        if (auctionTime < 0) {
            return "Aukcja zakończona"
        }
        return formatTimeDifference(auctionTime);
    }
    return ''
}

const formatTimeDifference = (milliseconds: number) => {
    const seconds = Math.floor(milliseconds / 1000);
    const minutes = Math.floor(seconds / 60);
    const hours = Math.floor(minutes / 60);
    const days = Math.floor(hours / 24);

    const remainingHours = hours % 24;
    const remainingMinutes = minutes % 60;
    const remainingSeconds = seconds % 60;

    return `${days}d ${remainingHours}h ${remainingMinutes}m ${remainingSeconds}s`;
};

export const calculateAuctionTimeExpired = (auctionEndsAt: string) => {
    if (auctionEndsAt) {
        const auctionEndTime = Date.parse(auctionEndsAt);
        const currentTime = Date.now();
        return auctionEndTime - currentTime;
    }
    return 0;
}

export function sortAndGroupOrders(orders: Order[]): SortedOrder[] {
    // Sort orders by orderId
    orders.sort((a, b) => a.orderId - b.orderId);

    // Group orders by orderId
    const groupedOrders: Record<number, Product[]> = {};

    orders.forEach(order => {
        if (!groupedOrders[order.orderId]) {
            groupedOrders[order.orderId] = [];
        }
        groupedOrders[order.orderId].push(order.product);
    });

    // Convert the grouped orders to an array of SortedOrders
    return Object.keys(groupedOrders).map(orderId => ({
        orderId: parseInt(orderId, 10),
        products: groupedOrders[orderId]
    }));
}
