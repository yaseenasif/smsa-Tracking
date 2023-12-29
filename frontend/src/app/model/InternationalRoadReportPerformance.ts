export interface InternationalRoadReportPerformance {
  id:number|null;
  preAlertNumber: string | null;
  referenceNumber: string | null;
  origin: string | null;
  destination: string | null;
  route: string | null;
  vehicleType: string | null;
  actualTimeDeparture: Date | string | null;
  actualTimeArrival: Date | string | null;
  offloaded: Date | string | null;
  totalTransitTime: number | null;
  totalLeadTime: number | null;
}
