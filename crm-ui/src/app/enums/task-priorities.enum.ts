export enum TaskPriority {
    LOW = "Low",
    MEDIUM = "Medium",
    HIGH = "High"
}

export const TaskPrioritiesMap = Object.keys(TaskPriority).map((name) => {
    return {
        name,
        value: TaskPriority[name as keyof typeof TaskPriority],
    };
});