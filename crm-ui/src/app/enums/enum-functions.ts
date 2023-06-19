export function getEnumKeyByEnumValue(myEnum: any, enumValue: number | string): string {
    let keys = Object.keys(myEnum).filter((x) => myEnum[x] == enumValue);
    return keys.length > 0 ? keys[0] : '';
}

// return the human-friendly value of an Enum, given its computer-friendly key value
// this assumes the Enum is named with 'name' and 'value'
export function getEnumValueByEnumKey(myEnum: any, enumKey: string): string {
    return myEnum.find(x => x.name === enumKey).value;
}