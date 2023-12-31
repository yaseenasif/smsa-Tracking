import { InputNumberInputEvent } from "primeng/inputnumber";

export interface DomesticShipment{
    originFacility: string|null|undefined,
    originLocation: string|null|undefined,
    refrigeratedTruck: boolean,
    destinationFacility: string|null|undefined,
    destinationLocation: string|null|undefined,
    routeNumber: string|null|undefined,
    numberOfShipments: number|null|undefined,
    weight: number|null|undefined,
    preAlertNumber:string|null|undefined,
    etd: Date|string|null|undefined,
    eta: Date|string|null|undefined,
    atd: Date|string|null|undefined,
    driverName: string|null|undefined,
    driverContact: string|null|undefined,
    referenceNumber: string|null|undefined,
    vehicleType: string|null|undefined,
    numberOfPallets: number|null|undefined,
    numberOfBags: number|null|undefined,
    vehicleNumber: string|null|undefined,
    tagNumber: number|null|undefined,
    sealNumber: number|null|undefined,
    status: string|null|undefined,
    remarks: string|null|undefined,
    ata: Date|string|null|undefined,
    totalShipments: number|null|undefined,
    overages: string|null|undefined,
    overagesAwbs: string|null|undefined,
    received: string|null|undefined,
    shortages: string|null|undefined,
    shortagesAwbs: string|null|undefined,
    attachments: string|null|undefined,
    transitTimeTaken:number|null,
    preAlertType:string|null
}
