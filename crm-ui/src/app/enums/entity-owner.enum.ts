export enum EntityOwner {
    ALL = 'All',
    MINE_ONLY = 'Mine only',
    NOT_ASSIGNED = 'Not assigned'
}

export const EntityOwnersMap = Object.keys(EntityOwner).map((name) => {
    return {
        name,
        value: EntityOwner[name as keyof typeof EntityOwner],
    };
});