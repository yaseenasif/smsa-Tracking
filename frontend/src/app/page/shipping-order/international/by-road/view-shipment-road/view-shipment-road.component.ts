import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MenuItem, MessageService } from 'primeng/api';
import { InternationalShipment } from 'src/app/model/InternationalShipment';
import { DatePipe } from '@angular/common';
import { InternationalShippingService } from 'src/app/page/shipping-order/international/service/international-shipping.service';

@Component({
  selector: 'app-view-shipment-road',
  templateUrl: './view-shipment-road.component.html',
  styleUrls: ['./view-shipment-road.component.scss'],
  providers: [MessageService, DatePipe]
})
export class ViewShipmentRoadComponent {
  items: MenuItem[] | undefined;
  iSID!: number;
  resultArray:{overagesAWBs:string|undefined,shortagesAWBs:string|undefined,securityTag:string|undefined}[]=[]
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
    destinationLocation: null,
    numberOfBoxes: undefined
  }


  constructor(private router: Router,
    private internationalShippingService: InternationalShippingService,
    private messageService: MessageService,
    private route: ActivatedRoute) { }

  InternationalShipmentHistory:any


  ngOnInit(): void {
    this.iSID = +this.route.snapshot.paramMap.get('id')!;
    this.items = [{ label: 'International Outbound', routerLink: '/international-tile' }, { label: 'International Outbound By Road', routerLink: '/international-shipment-by-road' }, { label: 'View International Outbound By Road' }];
        // Now that you have the responses, you can proceed with the next steps
        this.getInternationalShipmentById(this.iSID);
        this.getInternationalShipmentHistoryByInternationalShipmentId(this.iSID);
  }



  getInternationalShipmentById(id: number) {

    this.internationalShippingService.getInternationalShipmentByID(id).subscribe((res: InternationalShipment) => {
      this.internationalShipment = res;
      this.makeModelForTable()
    }, error => {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
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

  makeModelForTable() {
    let overagesAWBsArray!:any;
    let shortagesAWBsArray!:any;
    let securityTagArray!:any;
    if( this.internationalShipment.overageAWBs != null){
     overagesAWBsArray = this.internationalShipment.overageAWBs!.split(',');
    }else{
      overagesAWBsArray=[]
    }
    if(this.internationalShipment.shortageAWBs !=null){
     shortagesAWBsArray = this.internationalShipment.shortageAWBs!.split(',');
    }else{
      shortagesAWBsArray=[]
    }
    if(typeof this.internationalShipment.tagNumber == "string"){
     securityTagArray = this.internationalShipment.tagNumber!.split(',');
    }else{
       securityTagArray = []
    }
   

    // Determine the maximum length among the three arrays
    const maxLength = Math.max(
      overagesAWBsArray.length,
      shortagesAWBsArray.length,
      securityTagArray!.length
    );

    // Create an array to store objects
   

    // Loop through the arrays to create objects
    for (let i = 0; i < maxLength; i++) {
      const obj: any = {};
      if (i < overagesAWBsArray.length) {
        obj.overagesAWBs = overagesAWBsArray[i];
      }
      if (i < shortagesAWBsArray.length) {
        obj.shortagesAWBs = shortagesAWBsArray[i];
      }
      if (i < securityTagArray!.length) {
        obj.securityTag = securityTagArray![i];
      }
      this.resultArray.push(obj);
    }
  }

}




