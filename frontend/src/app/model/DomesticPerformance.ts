export interface DomesticPerformance{
    id:number;
    preAlertNumber:string|null;
    referenceNumber:string|null;
    origin:string|null;
    destination:string|null;
    route:string|null;
    vehicle:string|null;
    shipments:number|null; // no of shipments
    pallets:number|null; // no of pallets
    occupancy:string|null;
    bags:number|null;
    planedEtd:Date|string|null;
    planedEta:Date|string|null;
    atd:Date|string|null;
    ata:Date|string|null;
    planedEtdVsAtd:number|null;
    planedEtaVsAta:number|null;
    transitTime:number|null;
}