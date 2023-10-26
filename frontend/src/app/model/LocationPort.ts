import { Location } from 'src/app/model/Location'

export interface LocationPort{
    id: number|null|undefined,
    location: null|undefined|Location,
    portName: string|null|undefined,
    status: boolean|null|undefined
}
