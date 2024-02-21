import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MenuItem, MessageService } from 'primeng/api';
import { DomesticShipment } from 'src/app/model/DomesticShipment';
import { DatePipe } from '@angular/common';
import { DomesticShippingService } from 'src/app/page/shipping-order/domestic/service/domestic-shipping.service';

@Component({
  selector: 'app-view-domestic-shipping-for-summary',
  templateUrl: './view-domestic-shipping-for-summary.component.html',
  styleUrls: ['./view-domestic-shipping-for-summary.component.scss'],
  providers:[MessageService]
})
export class ViewDomesticShippingForSummaryComponent {
  items: MenuItem[] | undefined;
  resultArray:{overagesAWBs:string|undefined,shortagesAWBs:string|undefined,securityTag:string|undefined}[]=[]

  domesticShipment:DomesticShipment={
    originFacility: null,
    originLocation: null,
    refrigeratedTruck: false,
    destinationFacility: null,
    destinationLocation: null,
    routeNumber: null,
    numberOfShipments: null,
    weight: null,
    // etd: null,
    // eta: null,
    atd: null,
    driverName: null,
    driverContact: null,
    referenceNumber: null,
    vehicleType: null,
    numberOfPallets: null,
    numberOfBags: null,
    vehicleNumber: null,
    tagNumber: null,
    // sealNumber: null,
    status: null,
    remarks: null,
    ata: null,
    totalShipments: null,
    overages: null,
    overagesAwbs: null,
    received: null,
    shortages: null,
    shortagesAwbs: null,
    attachments: null,
    preAlertNumber: undefined,
    transitTimeTaken: null,
    preAlertType: null,
    originCountry: undefined,
    destinationCountry: undefined,
    numberOfBoxes: undefined
  };

  domesticShipmentId!:number;
  domesticShipmentHistory:any;

  constructor(
    private domesticShipmentService:DomesticShippingService,
    private router:Router,
    private messageService: MessageService,
    private route:ActivatedRoute) { }
  


  
  ngOnInit(): void {
    this.domesticShipmentId = +this.route.snapshot.paramMap.get('id')!;
    this.domesticShipmentById(this.domesticShipmentId);
    this.getDomesticShipmentHistoryByDomesticShipmentId(this.domesticShipmentId);
    this.items = [{ label: 'Domestic Inbound',routerLink:'/domestic-summary'},{ label: 'View Domestic Inbound'}];
  }

  getDomesticShipmentHistoryByDomesticShipmentId(id:number){
    this.domesticShipmentService.getDomesticShipmentHistoryByDomesticShipmentId(id).subscribe((res:any)=>{
      this.domesticShipmentHistory=res;
    },(error:any)=>{
      if(error.error.body){
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      }else{
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
      }   
    })
  }

  

   domesticShipmentById(id:number){
    this.domesticShipmentService.getDomesticShipmentById(id).subscribe((res:DomesticShipment)=>{

      this.domesticShipment=res;
      this.makeModelForTable();
    },(error:any)=>{
      if(error.error.body){
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      }else{
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
      }         
    })
   }
   makeModelForTable() {
    const overagesAWBsArray = this.domesticShipment.overagesAwbs!.split(',');
    const shortagesAWBsArray = this.domesticShipment.shortagesAwbs!.split(',');
    var securityTagArray!:any;
    if(typeof this.domesticShipment.tagNumber == "string"){
     securityTagArray = this.domesticShipment.tagNumber!.split(',');
    }else{
       securityTagArray = this.domesticShipment.tagNumber
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
