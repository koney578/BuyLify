export interface Notification {
    id: number;
    message: string;
    createdAt: string;
    notificationType: {
        id: number;
        name: string;
    }
    checked: boolean
}

export interface Category {
    id: number;
    name: string;
}

export interface Address {
    id: number;
    name: string;
    surname: string;
    phoneNumber: string;
    email: string;
    country: string;
    city: string;
    street: string;
    houseUnitNumber: string;
    postalCode: string;
}

export interface Order {
    id: number;
    address: Address;
    paymentMethod: {
        id: number;
        name: string
    };
    product: Product;
    productQuantity: number;
    orderStatus: {
        id: number;
        name: string
    };
    totalValue: number;
    createAt: string;
    deliveryMethod: {
        id: number;
        name: string
    };
}

export interface OpinionModal {
    closeModal: Function;
    order: Order;
}

export interface CloseProduct {
    closeModal: Function;
    averageStars: number | null;
}

export interface User {
    id: number;
    username: string | null;
    email: string | null;
    averageStars: number | null;
}

export interface ComplexUser {
    id: number | null;
    username: string | null;
    name: string | null;
    surname: string | null;
    phoneNumber: string | null;
    email: string | null;
    registrationDate: string | null;
    roles: any;
}

export interface Product {
    id: number;
    name: string;
    category: Category;
    price: number;
    count: number;
    description: string;
    photo: string;
    createdAt: string;
    user: User | null;
}

export interface Methods {
    idPaymentMethod: number;
    idDeliveryMethod: number;
    idProduct: number | undefined;
    productQuantity: number | string;
}

export interface DeliveryMethod {
    id: number;
    name: string;
}

export interface PaymentMethod {
    id: number;
    name: string;
}
