import { Component } from '@angular/core';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-update-international-shipment-by-air',
  templateUrl: './update-international-shipment-by-air.component.html',
  styleUrls: ['./update-international-shipment-by-air.component.scss']
})
export class UpdateInternationalShipmentByAirComponent {
  items: MenuItem[] | undefined;

  constructor() { }
  name!:string;
  ngOnInit() {
      this.items = [{ label: 'International Shipment',routerLink:'/international-tile'},{ label: 'International Shipment By Air',routerLink:'/international-shipment-by-air'},{ label: 'Update International Shipment By Air'}];
  }
}
