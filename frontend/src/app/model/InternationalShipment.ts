
export interface InternationalShipment {
  id: number | null | undefined,
  originCountry: string | null | undefined,
  originFacility: string|null|undefined,
  originLocation: string|null|undefined,
  // originPort: string | null | undefined,
  refrigeratedTruck: boolean | null | undefined,//Vehicle
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
  actualWeight: number | null | undefined,
  driverName: string | null | undefined,
  driverContact: string | null | undefined,
  referenceNumber: string | null | undefined,//Master CONS
  vehicleType: string | null | undefined,
  numberOfPallets: number | null | undefined,
  numberOfBags: number | null | undefined,
  vehicleNumber: string | null | undefined,
  tagNumber: number | null | undefined,
  sealNumber: number | null | undefined,
  attachments: string | null | undefined,
  status: string | null | undefined,
  remarks: string | null | undefined,
  routeNumber: string | null | undefined,
  ata: Date | string | null | undefined,
  totalShipments: number | null | undefined,
  overages: string | null | undefined,
  overageAWBs: string | null | undefined,
  received: string | null | undefined,
  shortages: string | null | undefined,
  shortageAWBs: string | null | undefined,
  trip: number | null | undefined,
  preAlertType:string|null,
  transitTimeTaken:number|null
}

export interface Time {
  hour: string | null | undefined,
  minute: string | null | undefined,
  nano: number | null | undefined,
  second: string | null | undefined
}
