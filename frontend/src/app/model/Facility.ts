import { Country } from "./Country";

export interface Facility{
    id: number|null|undefined,
    name:string|null|undefined,
    country:Country|null|undefined
}