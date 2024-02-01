import { Role } from "./Role";
import { Location } from "./Location";

export interface User{
    id:number|null|undefined,
    email:string|null|undefined,
    name:string|null|undefined,
    password:string|null|undefined,
    roles:Role[]|null|undefined,
    status:boolean|null|undefined,
    locations:Location[]|null|undefined;
    domesticOriginLocations:Location[]|null|undefined;
    domesticDestinationLocations:Location[]|null|undefined;
    internationalAirOriginLocation:Location[]|null|undefined;
    internationalAirDestinationLocation:Location[]|null|undefined;
    internationalRoadOriginLocation:Location[]|null|undefined;
    internationalRoadDestinationLocation:Location[]|null|undefined;
}