export interface Routes{
  id: number|undefined|null,
  destination: string|undefined|null,
  driver: string|undefined|null,
  eta: Date|string|null,
  etd: Date|string|null,
  origin: string|undefined|null,
  routeNumber: string|undefined|null,
  durationLimit: number|undefined|null
}
