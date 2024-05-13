import { Country } from "./Country";
import { DestinationEmails, EscalationEmails, OriginEmails } from "./Email";
import { Facility } from "./Facility";


export interface Location{

    id: number|undefined|null;
    locationName: string|undefined|null;
    type: string|undefined|null;
    originEmail:string[]|string|null;
    destinationEmail:string[]|string|null;
    originEscalationLevel1:string[]|string|null;
    originEscalationLevel2:string[]|string|null;
    originEscalationLevel3:string[]|string|null;
    destinationEscalationLevel1:string[]|string|null;
    destinationEscalationLevel2:string[]|string|null;
    destinationEscalationLevel3:string[]|string|null;
    status: boolean|undefined|null;
    facility: Facility|undefined|null;
    country: Country|undefined|null;
   
}