export enum DealStage {
    APPT_SCHEDULED = "Appointment Scheduled",
    QUALIFIED = "Qualified to Buy",
    PRESENTATION_SCHEDULED = "Presentation Scheduled",
    DECISION_MAKER = "Decision Maker Involved",
    CONTRACT_SENT = "Contract Sent",
    CLOSED_WON = "Closed Won! :)",
    CLOSED_LOST = "Closed Lost :("
}

export const DealStagesMap = Object.keys(DealStage).map((name) => {
    return {
        name,
        value: DealStage[name as keyof typeof DealStage],
    };
});