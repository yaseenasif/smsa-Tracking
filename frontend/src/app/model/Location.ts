import { DestinationEmails, EscalationEmails, OriginEmails } from "./Email";


export interface Location{

    id: number|undefined|null,
    locationName: string|undefined|null,
    type: string|undefined|null,
    originEmailsList:OriginEmails[]|null,
    destinationEmailsList:DestinationEmails[]|null,
    escalationEmailsList:EscalationEmails[]|null,
    status: boolean|undefined|null
}