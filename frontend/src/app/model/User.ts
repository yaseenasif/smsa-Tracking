import { Role } from "./Role";
import { Location } from "./Location";

export interface User{
    id:number|null|undefined,
    email:string|null|undefined,
    name:string|null|undefined,
    password:string|null|undefined,
    roles:Role[]|null|undefined,
    locations:Location[]|null|undefined
}