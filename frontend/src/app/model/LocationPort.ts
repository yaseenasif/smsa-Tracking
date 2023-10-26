interface Location{
    id: number|undefined|null,
    locationName: string|undefined|null,
    status: boolean|undefined|null
  }


export interface LocationPort{
    id: number|null|undefined,
    location: null|undefined|Location,
    portName: string|null|undefined,
    status: boolean|null|undefined
}

