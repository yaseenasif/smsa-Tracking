import { Component } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { InternationalShippingService } from '../../service/international-shipping.service';
import { InternationalShipment } from 'src/app/model/InternationalShipment';

@Component({
  selector: 'app-international-shipping-list',
  templateUrl: './international-shipping-list.component.html',
  styleUrls: ['./international-shipping-list.component.scss'],
  providers:[MessageService]
})
export class InternationalShippingListComponent {
  internationalShipmentByRoad!:InternationalShipment[];

  constructor(private internationalShippingService:InternationalShippingService,
    private messageService: MessageService,) { }
  items: MenuItem[] | undefined;

 

  ngOnInit() {
      this.items = [{ label: 'International Shipment',routerLink:'/international-tile'},{ label: 'International Shipment By Road'}];
      this.getAllInternationalShipmentByRoad();
  }
  

  getAllInternationalShipmentByRoad(){
    this.internationalShippingService.getAllInternationalShipmentByRoad().subscribe((res:InternationalShipment[])=>{
      this.internationalShipmentByRoad=res;
    },error=>{
    })
   }

   deleteInternationalShipmentById(id:number){
    this.internationalShippingService.deleteInternationalShipmentById(id).subscribe((res:any)=>{
      this.messageService.add({ severity: 'success', summary: 'Success', detail: res.message });
      this.getAllInternationalShipmentByRoad();     
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
    }
}
