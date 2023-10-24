import { Component } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { ShipmentStatus } from 'src/app/model/ShipmentStatus';
import { ShipmentStatusService } from '../service/shipment-status.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-shipment-status',
  templateUrl: './add-shipment-status.component.html',
  styleUrls: ['./add-shipment-status.component.scss'],
  providers:[MessageService]
})
export class AddShipmentStatusComponent {
  items: MenuItem[] | undefined;
  shipmentStatus:ShipmentStatus={
    id: null,
    name: null,
  };

  constructor(private shipmentStatusService:ShipmentStatusService,
              private messageService: MessageService,
              private router: Router) { }
 
  ngOnInit(): void {
    this.items = [{ label: 'Shipment Status list',routerLink:'/shipment-status'},{ label: 'Add Shipment Status'}];
  }
  
  onSubmit() {
    this.shipmentStatusService.addShipmentStatus(this.shipmentStatus).subscribe(res=>{
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Shipment Status is added' });
      setTimeout(() => {
        this.router.navigate(['/shipment-status']);
      },800);
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Shipment Status is not added' });
    })  
  }
}
