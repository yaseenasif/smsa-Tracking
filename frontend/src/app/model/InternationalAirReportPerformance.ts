export interface InternationalAirReportPerformance{
    id:number;
    preAlertNumber:string| null | undefined ;
    referenceNumber: string| null | undefined ;
    origin: string| null | undefined ;
    destination: string| null | undefined ;
    route:string| null | undefined ;
    flight:string | null | undefined;
    actualTimeDeparture :Date | string | null | undefined ;
    actualTimeArrival :Date | string | null | undefined ;
    cleared :Date | string | null | undefined ;
    totalTransitTime:number| null | undefined;
    totalLeadTime:number| null | undefined;
}