import { DestinationEmails, EscalationEmails, OriginEmails } from "./Email";


export interface Location{

    id: number|undefined|null,
    locationName: string|undefined|null,
    type: string|undefined|null,
    originEmail:string|null,
    destinationEmail:string|null,
    originEscalation:string|null,
    destinationEscalation:string|null,
    status: boolean|undefined|null
}