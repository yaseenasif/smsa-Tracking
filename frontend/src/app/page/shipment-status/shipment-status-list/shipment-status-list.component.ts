import { Component } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { ShipmentStatus } from 'src/app/model/ShipmentStatus';
import { ShipmentStatusService } from '../service/shipment-status.service';

@Component({
  selector: 'app-shipment-status-list',
  templateUrl: './shipment-status-list.component.html',
  styleUrls: ['./shipment-status-list.component.scss'],
  providers:[MessageService]
})
export class ShipmentStatusListComponent {
  constructor(private shipmentStatusService:ShipmentStatusService,private messageService:MessageService) { }
  shipmentStatus!:ShipmentStatus[];
  items: MenuItem[] | undefined;
  visible: boolean = false;
  sSID!:number

  ngOnInit() {
      this.items = [{ label: 'Shipment Status'}];
      this.getAllShipmentStatus();
  }
   getAllShipmentStatus(){
    this.shipmentStatusService.getALLShipmentStatus().subscribe((res:ShipmentStatus[])=>{
      this.shipmentStatus=res; 
    },error=>{
    })
   }
   deleteShipmentStatusByID(id:number){
 
    this.shipmentStatusService.deleteShipmentStatusByID(id).subscribe(()=>{
      this.getAllShipmentStatus();
      this.visible = false;
       this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Shipment Status is successfully deleted' });
    },(error)=>{
    
      this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Shipment Status is not deleted' });
    });
   }

  showDialog(id:number) {
    this.sSID=id;
    this.visible = true;
  }
}
