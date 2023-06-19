export enum ContactSource {
    REFERRAL = 'Referral',
    FILE = 'File',
    MANUAL = 'Manual Entry'
}

export const ContactSourceMap = Object.keys(ContactSource).map((name) => {
    return {
        name,
        value: ContactSource[name as keyof typeof ContactSource],
    };
});

export const ContactSourceKey =  function getKeyByValue(value: string) {
    const indexOfS = Object.values(ContactSource).indexOf(value as unknown as ContactSource);
    const key = Object.keys(ContactSource)[indexOfS];

    return key;
}