export enum AssignedTo {
    MINE_ONLY = 'Mine only',
    ANYBODY = 'Anybody',
    NOT_ASSIGNED = 'Not assigned'
}

export const AssignedToMap = Object.keys(AssignedTo).map((name) => {
    return {
        name,
        value: AssignedTo[name as keyof typeof AssignedTo],
    };
});