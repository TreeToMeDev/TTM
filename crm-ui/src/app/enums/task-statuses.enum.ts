export enum TaskStatus {
    NEW = "New",
    COMPLETE = "Complete"
}

export const TaskStatusesMap = Object.keys(TaskStatus).map((name) => {
    return {
        name,
        value: TaskStatus[name as keyof typeof TaskStatus],
    };
});