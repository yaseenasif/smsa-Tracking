import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MenuItem, MessageService } from 'primeng/api';
import { DomesticShipment } from 'src/app/model/DomesticShipment';

import { DatePipe } from '@angular/common';

import { DomesticShippingService } from 'src/app/page/shipping-order/domestic/service/domestic-shipping.service';

@Component({
  selector: 'app-view-shipment',
  templateUrl: './view-shipment.component.html',
  styleUrls: ['./view-shipment.component.scss'],
  providers:[MessageService,DatePipe]
})
export class ViewShipmentComponent {
  items: MenuItem[] | undefined;

  domesticShipment:DomesticShipment={
    originFacility: null,
    originLocation: null,
    refrigeratedTruck: false,
    destinationFacility: null,
    destinationLocation: null,
    routeNumber: null,
    numberOfShipments: null,
    weight: null,
    etd: null,
    eta: null,
    atd: null,
    driverName: null,
    driverContact: null,
    referenceNumber: null,
    vehicleType: null,
    numberOfPallets: null,
    numberOfBags: null,
    vehicleNumber: null,
    tagNumber: null,
    sealNumber: null,
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
    preAlertType: null
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
    this.items = [{ label: 'Domestic Shipment',routerLink:'/domestic-shipping'},{ label: 'View Domestic Shipment'}];
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
    
    },(error:any)=>{
      if(error.error.body){
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      }else{
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
      }         
    })
   }
}



