export interface Task {
    accountId: number
    accountName: string // RO
    agentId: number
    agentName: string
    contactId: number
    contactName: string // RO
    description: string
    dueDate: Date
    notes: string
    priority: string
    status: string
}
  