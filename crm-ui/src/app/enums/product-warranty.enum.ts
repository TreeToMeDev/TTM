export enum ProductWarranty {
    NONE = 'None',
    SIX_MONTHS = '6 Months',
    ONE_YEAR = '1 Year',
    TWO_YEARS = '2 Years'
}

export const ProductWarrantiesMap = Object.keys(ProductWarranty).map((name) => {
    return {
        name,
        value: ProductWarranty[name as keyof typeof ProductWarranty],
    };
});