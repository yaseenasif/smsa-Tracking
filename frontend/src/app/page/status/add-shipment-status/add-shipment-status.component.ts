import { Component } from '@angular/core';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-add-shipment-status',
  templateUrl: './add-shipment-status.component.html',
  styleUrls: ['./add-shipment-status.component.scss']
})
export class AddShipmentStatusComponent {

  items: MenuItem[] | undefined;

  constructor() { }
  name!:string;
  
  ngOnInit(): void {
    this.items = [{ label: 'Shipment Status List',routerLink:'/shipment-status'},{ label: 'Add Shipment Status'}];
  }
}
