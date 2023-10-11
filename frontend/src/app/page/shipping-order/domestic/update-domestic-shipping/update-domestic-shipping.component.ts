import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-update-domestic-shipping',
  templateUrl: './update-domestic-shipping.component.html',
  styleUrls: ['./update-domestic-shipping.component.scss']
})
export class UpdateDomesticShippingComponent {
  items: MenuItem[] | undefined;

  constructor() { }
  name!:string;
  
  ngOnInit(): void {
    this.items = [{ label: 'Domestic Shipment',routerLink:'/domestic-shipping'},{ label: 'Update Domestic Shipment'}];
  }
}
