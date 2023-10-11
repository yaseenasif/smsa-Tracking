import { Component } from '@angular/core';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-add-international-shipment-by-road',
  templateUrl: './add-international-shipment-by-road.component.html',
  styleUrls: ['./add-international-shipment-by-road.component.scss']
})
export class AddInternationalShipmentByRoadComponent {
  items: MenuItem[] | undefined;

  constructor() { }
  name!:string;
  ngOnInit() {
      this.items = [{ label: 'International Shipment',routerLink:'/international-tile'},{ label: 'International Shipment By Air',routerLink:'/international-shipment-by-air'},{ label: 'Add International Shipment By Air'}];
  }
}
