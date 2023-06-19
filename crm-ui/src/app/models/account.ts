export interface Account {
    accountType: string;
    agentId: number;
    agentName: string;
    billingCity: string;
    billingCountry: string;
    billingStreet: string;
    billingPostalCode: string;
    billingProvinceState: string;
    currency: string;
    currencyDescription: string;
    industry: string;
    id: number,
    lastChangeAction: string;
    lastChangeDescription: string;
    lastChangeTimestamp: Date;
    lastChangeUserName: string;
    name: string;
    phone: string;
    shippingAddressSameAsBilling: boolean;
    shippingCity: string;
    shippingCountry: string;
    shippingPostalCode: string;
    shippingProvinceState: string;
    shippingStreet: string;
    webSite: string;
}
  