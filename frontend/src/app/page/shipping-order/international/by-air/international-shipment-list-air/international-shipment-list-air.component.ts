import { Component } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { InternationalShipment } from 'src/app/model/InternationalShipment';
import { InternationalShippingService } from '../../service/international-shipping.service';

@Component({
  selector: 'app-international-shipment-list-air',
  templateUrl: './international-shipment-list-air.component.html',
  styleUrls: ['./international-shipment-list-air.component.scss'],
  providers:[MessageService]
})
export class InternationalShipmentListAirComponent {

  internationalShipmentByAir!:InternationalShipment[];

  constructor(private internationalShippingService:InternationalShippingService,
    private messageService: MessageService,) { }
  items: MenuItem[] | undefined;

 
  ngOnInit() {
      this.items = [{ label: 'International Shipment',routerLink:'/international-tile'},{ label: 'International Shipment By Air'}];
      this.getAllInternationalShipmentByAir();
    }
    
  
    getAllInternationalShipmentByAir(){
      this.internationalShippingService.getAllInternationalShipmentByAir().subscribe((res:InternationalShipment[])=>{
        this.internationalShipmentByAir=res; 
      },error=>{

      })
    }

    deleteInternationalShipmentById(id:number){
    this.internationalShippingService.deleteInternationalShipmentById(id).subscribe((res:any)=>{
      this.messageService.add({ severity: 'success', summary: 'Success', detail: res.message });
      this.getAllInternationalShipmentByAir();
     
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
    }
}
