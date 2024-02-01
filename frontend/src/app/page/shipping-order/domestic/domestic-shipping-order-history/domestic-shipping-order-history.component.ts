import { Component } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { DomesticShippingService } from '../service/domestic-shipping.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-domestic-shipping-order-history',
  templateUrl: './domestic-shipping-order-history.component.html',
  styleUrls: ['./domestic-shipping-order-history.component.scss'],
  providers:[MessageService]
})
export class DomesticShippingOrderHistoryComponent {
  domesticShipmentId:any;
  constructor(private domesticShippingService:DomesticShippingService,
    private messageService: MessageService,
    private route:ActivatedRoute) { }
  domesticShipmentHistory:any;
  items: MenuItem[] | undefined;

  domesticShipment:any;


  ngOnInit() {
    this.domesticShipmentId = +this.route.snapshot.paramMap.get('id')!;
    this.getDomesticShipmentByID(this.domesticShipmentId);

    this.getDomesticShipmentHistoryByDomesticShipmentId(this.domesticShipmentId);
      this.items = [{ label: 'Domestic Outbound',routerLink:'/domestic-shipping'},{ label: 'Domestic Shipping History'}];
  }

  getDomesticShipmentHistoryByDomesticShipmentId(id:number){
    this.domesticShippingService.getDomesticShipmentHistoryByDomesticShipmentId(id).subscribe((res:any)=>{
      this.domesticShipmentHistory=res;
    },(error:any)=>{
      if(error.error.body){
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      }else{
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
      }   
    })
  }

  getDomesticShipmentByID(id:any){
    this.domesticShippingService.getDomesticShipmentById(id).subscribe((res:any)=>{
      this.domesticShipment = res;
    },(error:any)=>{
      if(error.error.body){
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      }else{
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
      }   
    })
  }

}

