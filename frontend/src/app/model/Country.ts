import { Facility } from "./Facility";

export interface Country{
    id: number|null|undefined,
    name:string|null|undefined,
    facilities:Facility[]|null|undefined
}