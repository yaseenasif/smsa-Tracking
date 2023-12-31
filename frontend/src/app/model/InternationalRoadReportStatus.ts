export interface InternationalRoadReportStatus {
    id: number | null;
    preAlertNumber: string | null;
    referenceNumber: string | null;
    origin: string | null;
    destination: string | null;
    route: string | null;
    vehicle: string | null;
    shipments: number | null; // no of shipments
    pallets: number | null;  // no of pallets
    occupancy: number | null;
    bags: number | null;  // no of bags
    etd: Date | string | null;
    atd: Date | string | null;
    eta: Date | string | null;
    ata: Date | string | null;
    created: Date | string | null;
    departed: Date | string | null;
    notArrived: Date | string | null;
    heldInCustoms: Date | string | null;
    awaitingPayments: Date | string | null;
    accident: Date | string | null;
    borderDelay: Date | string | null;
    offloadedAtDestination: Date | string | null;
    Cleared: Date | string | null;
    etdVsEta: number | null;
    etaVSAta: number | null;
    leadTime: number | null;
    remarks: string | null
}