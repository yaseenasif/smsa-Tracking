import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-add-international-shipping',
  templateUrl: './add-international-shipping.component.html',
  styleUrls: ['./add-international-shipping.component.scss']
})
export class AddInternationalShippingComponent {
  items: MenuItem[] | undefined;

  constructor() { }
  name!:string;
  ngOnInit() {
      this.items = [{ label: 'International Shipment',routerLink:'/international-tile'},{ label: 'International Shipment By Road',routerLink:'/international-shipment-by-road'},{ label: 'Add International Shipment By Road'}];
  }
}
