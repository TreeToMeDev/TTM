export enum UploadEntityType {
    CONTACTS = 'Contacts',
}

export const UploadEntityTypeMap = Object.keys(UploadEntityType).map((name) => {
    return {
        name,
        value: UploadEntityType[name as keyof typeof UploadEntityType],
    };
});