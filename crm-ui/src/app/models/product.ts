export interface Product {
    availableDate: Date;
    description: string;
    id: number,
    lastTimestamp: Date;
    lastUser: string;
    measurement: string;
    measurementAmount: number;
    measurementDescription: string;
    price: number;
    productCode: string;
    quantityOnHand: number;
    warrantyDuration: string;
}
