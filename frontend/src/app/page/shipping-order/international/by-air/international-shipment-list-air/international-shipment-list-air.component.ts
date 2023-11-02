import { Component } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { InternationalShipment } from 'src/app/model/InternationalShipment';
import { InternationalShippingService } from '../../service/international-shipping.service';

@Component({
  selector: 'app-international-shipment-list-air',
  templateUrl: './international-shipment-list-air.component.html',
  styleUrls: ['./international-shipment-list-air.component.scss']
})
export class InternationalShipmentListAirComponent {

  internationalShipmentByRoad!:InternationalShipment[];

  constructor(private internationalShippingService:InternationalShippingService) { }
  items: MenuItem[] | undefined;

 
  ngOnInit() {
      this.items = [{ label: 'International Shipment',routerLink:'/international-tile'},{ label: 'International Shipment By Air'}];
      this.getAllInternationalShipmentByAir();
    }
    
  
    getAllInternationalShipmentByAir(){
      this.internationalShippingService.getAllInternationalShipmentByAir().subscribe((res:InternationalShipment[])=>{
        this.internationalShipmentByRoad=res; 
      },error=>{

      })
    }
}
