import { Component } from '@angular/core';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-shipment-status-list',
  templateUrl: './shipment-status-list.component.html',
  styleUrls: ['./shipment-status-list.component.scss']
})
export class ShipmentStatusListComponent {

  constructor() { }
  products:any=[{name:"Demo"},
  {name:"Demo"},
  {name:"Demo"},
  {name:"Demo"},
  {name:"Demo"},
  {name:"Demo"},
  {name:"Demo"},
  {name:"Demo"},
  {name:"Demo"},
  {name:"Demo"},];
  items: MenuItem[] | undefined;

 

  ngOnInit() {
      this.items = [{ label: 'Shipment Status List'}];
  }
}
