import { Role } from "./Role";

export interface User{
    id:number|null|undefined,
    email:string|null|undefined,
    location:string|null|undefined,
    name:string|null|undefined,
    password:string|null|undefined,
    roles:Role[]|null|undefined
}