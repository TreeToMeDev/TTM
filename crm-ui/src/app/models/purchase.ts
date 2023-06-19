export interface Purchase {
  accountId: number,
  id: number,
  productCode: string,
  purchaseDate: Date | null,
  serialNo: string,
  warrantyEndDate: Date | null
}
