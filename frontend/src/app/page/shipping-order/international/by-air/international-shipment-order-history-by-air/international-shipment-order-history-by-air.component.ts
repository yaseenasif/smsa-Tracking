import { Component } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { InternationalShippingService } from '../../service/international-shipping.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-international-shipment-order-history-by-air',
  templateUrl: './international-shipment-order-history-by-air.component.html',
  styleUrls: ['./international-shipment-order-history-by-air.component.scss'],
  providers:[MessageService]
})
export class InternationalShipmentOrderHistoryByAirComponent {
  constructor(private internationalShippingService: InternationalShippingService,
    private messageService: MessageService,
    private route:ActivatedRoute) { }

  internationalShipmentId:any;
  InternationalShipment:any;
  InternationalShipmentHistory:any=[{status:"Cleared",processTime:"10/16/2022 15:45",locationCode:"KSA GW",user:"9590",remarks:"Load Cleared and Forward"},
  {status:"Cleared",processTime:"10/16/2022 15:45",locationCode:"KSA GW",user:"9590",remarks:"Load Cleared and Forward"},
  {status:"Cleared",processTime:"10/16/2022 15:45",locationCode:"KSA GW",user:"9590",remarks:"Load Cleared and Forward"},
  {status:"Cleared",processTime:"10/16/2022 15:45",locationCode:"KSA GW",user:"9590",remarks:"Load Cleared and Forward"},
  {status:"Cleared",processTime:"10/16/2022 15:45",locationCode:"KSA GW",user:"9590",remarks:"Load Cleared and Forward"},
  {status:"Cleared",processTime:"10/16/2022 15:45",locationCode:"KSA GW",user:"9590",remarks:"Load Cleared and Forward"},
  {status:"Cleared",processTime:"10/16/2022 15:45",locationCode:"KSA GW",user:"9590",remarks:"Load Cleared and Forward"},
  {status:"Cleared",processTime:"10/16/2022 15:45",locationCode:"KSA GW",user:"9590",remarks:"Load Cleared and Forward"},
  {status:"Cleared",processTime:"10/16/2022 15:45",locationCode:"KSA GW",user:"9590",remarks:"Load Cleared and Forward"},
  {status:"Cleared",processTime:"10/16/2022 15:45",locationCode:"KSA GW",user:"9590",remarks:"Load Cleared and Forward"},];
  items: MenuItem[] | undefined;


  ngOnInit() {
    this.internationalShipmentId = +this.route.snapshot.paramMap.get('id')!;
    this.getInternationalShipmentByID(this.internationalShipmentId);

    this.getInternationalShipmentHistoryByInternationalShipmentId(this.internationalShipmentId);
      this.items = [{ label: 'International Shipment',routerLink:'/international-tile'},{ label: 'International Shipment By Air',routerLink:'/international-shipment-by-air'},{ label: 'International Shipment History By Air'}];
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

  getInternationalShipmentByID(id:any){
    this.internationalShippingService.getInternationalShipmentByID(id).subscribe((res:any)=>{
      this.InternationalShipment = res;
    },(error:any)=>{
      if(error.error.body){
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      }else{
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
      }   
    })
  }

}
