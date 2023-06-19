import { DealItem } from "../models/deal-item";

export interface Deal {
    accountId: number;
    accountName: string;
    agentId: number;
    agentName: string;
    amount: number;
    contactId: number;
    contactName: string;
    dueDate: Date;
    id: number;
    name: string;
    stage: string;
}
