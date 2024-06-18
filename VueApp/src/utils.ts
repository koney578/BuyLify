import type {Address, Order, Product, SoldProduct, SortedOrder, SortedSoldProduct, SumQuantity} from "~/types";

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
    // Sort sold products by orderId
    orders.sort((a, b) => a.orderId - b.orderId);

    // Group sold products by orderId
    const groupedSoldProducts: Record<number, { products: Product[], statusName: string, sellerName: string }> = {};

    orders.forEach(orders => {
        if (!groupedSoldProducts[orders.orderId]) {
            groupedSoldProducts[orders.orderId] = {
                products: [],
                statusName: orders.statusName,
                sellerName: orders.sellerName
            };
        }
        groupedSoldProducts[orders.orderId].products.push(orders.product);
    });

    // Convert the grouped sold products to an array of SortedSoldProducts
    return Object.keys(groupedSoldProducts).map(orderId => ({
        orderId: parseInt(orderId, 10),
        products: groupedSoldProducts[orderId].products,
        statusName: groupedSoldProducts[orderId].statusName,
        sellerName: groupedSoldProducts[orderId].sellerName
    }));
}

export function sortAndGroupSoldProducts(soldProducts: SoldProduct[]): SortedSoldProduct[] {
    // Sort sold products by orderId
    soldProducts.sort((a, b) => a.orderId - b.orderId);

    // Group sold products by orderId
    const groupedSoldProducts: Record<number, { products: Product[], address: Address, statusId: number, sumQuantity: SumQuantity }> = {};

    soldProducts.forEach(soldProduct => {
        if (!groupedSoldProducts[soldProduct.orderId]) {
            groupedSoldProducts[soldProduct.orderId] = {
                products: [],
                address: soldProduct.address,
                statusId: soldProduct.statusId,
                sumQuantity: soldProduct.sumQuantity,
            };
        }
        soldProduct.product.price = soldProduct.sumQuantity.sumPrice;
        soldProduct.product.count = soldProduct.sumQuantity.productQuantity;
        groupedSoldProducts[soldProduct.orderId].products.push(soldProduct.product);
    });

    // Convert the grouped sold products to an array of SortedSoldProducts
    return Object.keys(groupedSoldProducts).map(orderId => ({
        orderId: parseInt(orderId, 10),
        products: groupedSoldProducts[orderId].products,
        address: groupedSoldProducts[orderId].address,
        statusId: groupedSoldProducts[orderId].statusId,
        sumQuantity: groupedSoldProducts[orderId].sumQuantity,
    }));
}
