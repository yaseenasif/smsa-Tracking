import { Permission } from "./Permission";

export interface Role{
    id:number|null|undefined,
    name:string|null|undefined,
    permissions:Permission[]|null|undefined
}