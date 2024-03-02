import { InputNumberInputEvent } from "primeng/inputnumber";

export interface DomesticShipment{
    originCountry: string|null|undefined,
    destinationCountry: string|null|undefined,
    originFacility: string|null|undefined,
    originLocation: string|null|undefined,
    refrigeratedTruck: boolean,//Refrigerated Vehicle
    destinationFacility: string|null|undefined,
    destinationLocation: string|null|undefined,
    routeNumber: string|null|undefined,
    numberOfShipments: number|null|undefined,
    weight: number|null|undefined,
    preAlertNumber:string|null|undefined,
    // etd: Date|string|null|undefined,
    // eta: Date|string|null|undefined,
    atd: Date|string|null|undefined,
    driverName: string|null|undefined,
    driverContact: string|null|undefined,
    referenceNumber: string|null|undefined,//Master CONS
    vehicleType: string|null|undefined,
    numberOfPallets: number|null|undefined,
    numberOfBags: number|null|undefined,
    vehicleNumber: string|null|undefined,
    tagNumber: string[]|string|null;//security tag
    // sealNumber: number|null|undefined,
    routeNumberId:number|null|undefined
    status: string|null|undefined,
    remarks: string|null|undefined,
    ata: Date|string|null|undefined,
    totalShipments: number|null|undefined,
    overages: number|null|undefined,
    overagesAwbs: string|null|undefined,
    numberOfBoxes:number|null|undefined,
    received: number|null|undefined,
    shortages: number|null|undefined,
    shortagesAwbs: string|null|undefined,
    attachments: string|null|undefined,
    transitTimeTaken:number|null,
    preAlertType:string|null,
    damage:number|null,
    damageAwbs:string|null,
    numberOfPalletsReceived:number|null,
    numberOfBagsReceived:number|null;
}
