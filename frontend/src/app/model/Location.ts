import { DestinationEmails, EscalationEmails, OriginEmails } from "./Email";


export interface Location{

    id: number|undefined|null,
    locationName: string|undefined|null,
    type: string|undefined|null,
    originEmail:string[]|string|null,
    destinationEmail:string[]|string|null,
    originEscalation:any,
    destinationEscalation:any,
    status: boolean|undefined|null
}