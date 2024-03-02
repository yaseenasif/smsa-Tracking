
export interface InternationalShipment {
  id: number | null | undefined,
  originCountry: string | null | undefined,
  originFacility: string|null|undefined,
  originLocation: string|null|undefined,
  // originPort: string | null | undefined,
  refrigeratedTruck: boolean | null | undefined,//Refrigerated Vehicle
  type: string | null | undefined,
  shipmentMode: string | null | undefined,
  preAlertNumber: string | null | undefined,
  destinationCountry: string | null | undefined,
  destinationFacility: string|null|undefined,
  destinationLocation: string|null|undefined,
  // destinationPort: string | null | undefined,
  carrier: string | null | undefined,
  etd: Date | string | null | undefined,
  eta: Date | string | null | undefined,
  atd: Date | string | null | undefined,
  flightNumber: number | null | undefined,
  numberOfShipments: number | null | undefined,
  numberOfBoxes:number|null|undefined,
  actualWeight: number | null | undefined,
  driverName: string | null | undefined,
  driverContact: string | null | undefined,
  referenceNumber: string | null | undefined,//Master CONS
  vehicleType: string | null | undefined,
  numberOfPallets: number | null | undefined,
  numberOfBags: number | null | undefined,
  vehicleNumber: string | null | undefined,
  tagNumber:string[]|string|null,//Security Tag
  sealNumber: number | null | undefined,
  attachments: string | null | undefined,
  status: string | null | undefined,
  remarks: string | null | undefined,
  routeNumber: string | null | undefined,
  ata: Date | string | null | undefined,
  totalShipments: number | null | undefined,
  overages: number | null | undefined,
  overageAWBs: string | null | undefined,
  received: number | null | undefined,
  shortages: number | null | undefined,
  shortageAWBs: string | null | undefined,
  trip: number | null | undefined,
  preAlertType:string|null,
  transitTimeTaken:number|null,
  damage:number|null,
  damageAwbs:string|null,
  numberOfPalletsReceived:number|null,
  numberOfBagsReceived:number|null
}

export interface Time {
  hour: string | null | undefined,
  minute: string | null | undefined,
  nano: number | null | undefined,
  second: string | null | undefined
}
