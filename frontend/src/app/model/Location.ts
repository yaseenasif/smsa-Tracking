import { Country } from "./Country";
import { DestinationEmails, EscalationEmails, OriginEmails } from "./Email";
import { Facility } from "./Facility";


export interface Location{

    id: number|undefined|null;
    locationName: string|undefined|null;
    type: string|undefined|null;
    originEmail:string[]|string|null;
    destinationEmail:string[]|string|null;
    originEscalation:any;
    destinationEscalation:any;
    status: boolean|undefined|null;
    facility: Facility|undefined|null;
    country: Country|undefined|null;
   
}