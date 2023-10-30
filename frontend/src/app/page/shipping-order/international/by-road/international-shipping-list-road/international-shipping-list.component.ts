import { Component } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { InternationalShippingService } from '../../service/international-shipping.service';
import { InternationalShipment } from 'src/app/model/InternationalShipment';

@Component({
  selector: 'app-international-shipping-list',
  templateUrl: './international-shipping-list.component.html',
  styleUrls: ['./international-shipping-list.component.scss']
})
export class InternationalShippingListComponent {
  internationalShipmentByRoad!:InternationalShipment[];

  constructor(private internationalShippingService:InternationalShippingService) { }
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
}
