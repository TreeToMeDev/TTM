export enum CSVFileStatus {
    ERROR = 'ERROR',
    UPLOADED = 'UPLOADED',
    POSTED = 'POSTED'
}

export const CSVFileStatusMap = Object.keys(CSVFileStatus).map((name) => {
    return {
        name,
        value: CSVFileStatus[name as keyof typeof CSVFileStatus],
    };
});