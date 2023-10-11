import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-update-international-shipping',
  templateUrl: './update-international-shipping.component.html',
  styleUrls: ['./update-international-shipping.component.scss']
})
export class UpdateInternationalShippingComponent {
  items: MenuItem[] | undefined;

  constructor() { }
  name!:string;
  ngOnInit() {
      this.items = [{ label: 'International Shipment',routerLink:'/international-tile'},{ label: 'International Shipment By Road',routerLink:'/international-shipment-by-road'},{ label: 'Update International Shipment By Road'}];
  }
}
