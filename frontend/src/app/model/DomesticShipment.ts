import { InputNumberInputEvent } from "primeng/inputnumber";
import { Country } from "./Country";
import { Facility } from "./Facility";
import { Routes } from "./ShipmentRoutes";
import { Driver } from "./Driver";
import { VehicleType } from "./VehicleType";
import { Location } from "./Location";


export interface DomesticShipment{
    originCountry: Country|null|undefined,
    destinationCountry: Country|null|undefined,
    originFacility: Facility|null|undefined,
    originLocation: Location|null|undefined,
    refrigeratedTruck: boolean,//Refrigerated Vehicle
    destinationFacility: Facility|null|undefined,
    destinationLocation: Location|null|undefined,
    route: Routes|null|undefined,
    numberOfShipments: number|null|undefined,
    weight: number|null|undefined,
    preAlertNumber:string|null|undefined,
    // etd: Date|string|null|undefined,
    // eta: Date|string|null|undefined,
    atd: Date|string|null|undefined,
    driver: Driver|null|undefined,
    driverContact: string|null|undefined,
    referenceNumber: string|null|undefined,//Master CONS
    vehicleType: VehicleType|null|undefined,
    numberOfPallets: number|null|undefined,
    numberOfBags: number|null|undefined,
    vehicleNumber: string|null|undefined,
    tagNumber: string[]|string|null;//security tag
    // sealNumber: number|null|undefined,
    status: string|null|undefined,
    remarks: string|null|undefined,
    ata: Date|string|null|undefined,
    totalShipments: number|null|undefined,
    overages: string|null|undefined,
    overagesAwbs: string|null|undefined,
    numberOfBoxes:number|null|undefined,
    received: string|null|undefined,
    shortages: string|null|undefined,
    shortagesAwbs: string|null|undefined,
    attachments: string|null|undefined,
    transitTimeTaken:number|null,
    preAlertType:string|null
}
