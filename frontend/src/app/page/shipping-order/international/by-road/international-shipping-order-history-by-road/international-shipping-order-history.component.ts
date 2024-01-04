import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MenuItem, MessageService } from 'primeng/api';
import { InternationalShippingService } from '../../service/international-shipping.service';

@Component({
  selector: 'app-international-shipping-order-history',
  templateUrl: './international-shipping-order-history.component.html',
  styleUrls: ['./international-shipping-order-history.component.scss'],
  providers:[MessageService]
})
export class InternationalShippingOrderHistoryComponent {
  constructor(private internationalShippingService: InternationalShippingService,
    private messageService: MessageService,
    private route:ActivatedRoute) { }

  internationalShipmentId:any;
  InternationalShipment:any;


  InternationalShipmentHistory:any
  items: MenuItem[] | undefined;


  ngOnInit() {
    this.internationalShipmentId = +this.route.snapshot.paramMap.get('id')!;
    this.getInternationalShipmentByID(this.internationalShipmentId);

    this.getInternationalShipmentHistoryByInternationalShipmentId(this.internationalShipmentId);
      this.items = [{ label: 'International Shipment',routerLink:'/international-tile'},{ label: 'International Shipment By Road',routerLink:'/international-shipment-by-road'},{ label: 'International Shipment History By Road'}];
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
