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

  internationalShipmentByAir!:InternationalShipment[];

  constructor(private internationalShippingService:InternationalShippingService) { }
  items: MenuItem[] | undefined;

 
  ngOnInit() {
      this.items = [{ label: 'International Shipment',routerLink:'/international-tile'},{ label: 'International Shipment By Air'}];
      this.getAllInternationalShipmentByAir();
    }
    
  
    getAllInternationalShipmentByAir(){
      this.internationalShippingService.getAllInternationalShipmentByAir({ value: "Pre-Alert Created", user: {}, type:"" }, 0, 10).subscribe((res:any)=>{
        this.internationalShipmentByAir=res.content; 
      },error=>{

      })
    }
}
