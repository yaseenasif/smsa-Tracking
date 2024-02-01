import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MenuItem, MessageService } from 'primeng/api';
import { InternationalShipment } from 'src/app/model/InternationalShipment';
import { Location } from 'src/app/model/Location'
import { InternationalShippingService } from 'src/app/page/shipping-order/international/service/international-shipping.service';



@Component({
  selector: 'app-view-air-shipping-for-summary',
  templateUrl: './view-air-shipping-for-summary.component.html',
  styleUrls: ['./view-air-shipping-for-summary.component.scss'],
  providers: [MessageService]
})
export class ViewAirShippingForSummaryComponent {
  items: MenuItem[] | undefined;
  iSID!: number;
  internationalShipment: InternationalShipment = {
    id: null,
    actualWeight: null,
    ata: null,
    attachments: null,
    carrier: null,
    destinationCountry: null,
    driverContact: null,
    driverName: null,
    flightNumber: null,
    numberOfBags: null,
    numberOfPallets: null,
    numberOfShipments: null,
    originCountry: null,
    overageAWBs: null,
    overages: null,
    preAlertNumber: null,
    received: null,
    referenceNumber: null,
    refrigeratedTruck: false,
    remarks: null,
    sealNumber: null,
    shipmentMode: null,
    shortageAWBs: null,
    shortages: null,
    status: null,
    tagNumber: null,
    totalShipments: null,
    type: 'By Air',
    vehicleNumber: null,
    vehicleType: null,
    routeNumber: null,
    etd: null,
    eta: null,
    atd: null,
    trip: null,
    preAlertType: null,
    transitTimeTaken: null,
    originFacility: null,
    originLocation: null,
    destinationFacility: null,
    destinationLocation: null
  }
 
  selectedLocation!: Location;
  InternationalShipmentHistory:any;

  constructor(private router: Router,
    private internationalShippingService: InternationalShippingService,
    private messageService: MessageService,
    private route: ActivatedRoute) { }

  


  ngOnInit(): void {
    this.iSID = +this.route.snapshot.paramMap.get('id')!;
    this.items = [{ label: 'International Inbound By Air', routerLink: '/international-summary-by-air' }, { label: 'View International Shipment By Air' }];
  
    this.getInternationalShipmentHistoryByInternationalShipmentId(this.iSID);
        // Now that you have the responses, you can proceed with the next steps
    this.getInternationalShipmentById(this.iSID);
   
  }

  getInternationalShipmentHistoryByInternationalShipmentId(id:number){
    this.internationalShippingService.getInternationalShipmentHistoryByInternationalShipmentId(id).subscribe((res:any)=>{
      this.InternationalShipmentHistory=res;
    },(error:any)=>{
      if(error.error.body){
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      }else{
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
      }   
    })
  }
 

  getInternationalShipmentById(id: number) {

    this.internationalShippingService.getInternationalShipmentByID(id).subscribe((res: InternationalShipment) => {
      this.internationalShipment = res;
    }, error => {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
  }
}
