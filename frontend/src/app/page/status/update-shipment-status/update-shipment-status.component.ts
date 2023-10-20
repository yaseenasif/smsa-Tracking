import { Component } from '@angular/core';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-update-shipment-status',
  templateUrl: './update-shipment-status.component.html',
  styleUrls: ['./update-shipment-status.component.scss']
})
export class UpdateShipmentStatusComponent {

  items: MenuItem[] | undefined;

  constructor() { }
  name!:string;
  
  ngOnInit(): void {
    this.items = [{ label: 'Shipment Status List',routerLink:'/shipment-status'},{ label: 'Edit Shipment Status'}];
  }
}
