import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MenuItem, MessageService } from 'primeng/api';
import { InternationalShipment } from 'src/app/model/InternationalShipment';
import { Location } from 'src/app/model/Location'
import { DatePipe } from '@angular/common';
import { InternationalShippingService } from 'src/app/page/shipping-order/international/service/international-shipping.service';

@Component({
  selector: 'app-view-shipment-air',
  templateUrl: './view-shipment-air.component.html',
  styleUrls: ['./view-shipment-air.component.scss'],
  providers: [MessageService, DatePipe]
})
export class ViewShipmentAirComponent {
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
    originFacility: undefined,
    originLocation: undefined,
    destinationFacility: undefined,
    destinationLocation: undefined
  }
 
  selectedLocation!: Location;
  InternationalShipmentHistory:any;

  constructor(private router: Router,
    private internationalShippingService: InternationalShippingService,
    private messageService: MessageService,
    private route: ActivatedRoute,
    private datePipe: DatePipe) { }

  


  ngOnInit(): void {
    this.iSID = +this.route.snapshot.paramMap.get('id')!;
    this.items = [{ label: 'International Outbound', routerLink: '/international-tile' }, { label: 'International Outbound By Air', routerLink: '/international-shipment-by-air' }, { label: 'View International Outbound By Air' }];
  
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


