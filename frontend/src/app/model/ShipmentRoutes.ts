import { Driver } from "./Driver";
import { Vehicle } from "./VehicleType";

export interface Routes{
  id: number|undefined|null,
  destination: string|undefined|null,
  driver: string|undefined|null,
  eta: Date|string|null,
  etd: Date|string|null,
  origin: string|undefined|null,
  route: string|undefined|null,
  remarks: string|undefined|null,
  durationLimit: number|undefined|null,
  drivers: Driver[]|undefined|null,
  vehicles:Vehicle[]|undefined|null
}
